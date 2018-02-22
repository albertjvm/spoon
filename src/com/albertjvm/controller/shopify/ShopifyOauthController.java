package com.albertjvm.controller.shopify;

import com.albertjvm.data.OauthStateMapper;
import com.albertjvm.data.UserMapper;
import com.albertjvm.model.OauthState;
import com.albertjvm.model.ShopifyAccessToken;
import com.albertjvm.model.User;
import com.albertjvm.service.shopify.ShopifyOauthService;
import com.albertjvm.service.shopify.ShopifyWebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.ws.rs.QueryParam;
import java.util.UUID;

@Controller
@RequestMapping(value = "shopify/oauth")
public class ShopifyOauthController {

    private final ShopifyOauthService shopifyOauthService;
    private final ShopifyWebhookService shopifyWebhookService;
    private final OauthStateMapper oauthStateMapper;
    private final UserMapper userMapper;

    private final String SHOPIFY_AUTHORIZE_URL = "https://{shopName}.myshopify.com/admin/oauth/authorize?client_id=aa22486dd71295321f9376463cf5da0f&scope=read_products,write_products&redirect_uri=http://localhost:18080/service/shopify/oauth/callback";

    @Autowired
    public ShopifyOauthController(ShopifyOauthService shopifyOauthService, ShopifyWebhookService shopifyWebhookService, OauthStateMapper oauthStateMapper, UserMapper userMapper) {
        this.shopifyOauthService = shopifyOauthService;
        this.shopifyWebhookService = shopifyWebhookService;
        this.oauthStateMapper = oauthStateMapper;
        this.userMapper = userMapper;
    }

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ResponseEntity getAndSaveToken(@QueryParam("authCode") String authCode, @QueryParam("shop") String shop) {
        ShopifyAccessToken accessToken = shopifyOauthService.getToken(authCode, shop);

        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public RedirectView handleCallback(@QueryParam("code") String code, @QueryParam("shop") String shop, @QueryParam("state") String state) {
        String shopName = shop.substring(0, shop.indexOf(".myshopify.com"));
        ShopifyAccessToken accessToken = shopifyOauthService.getToken(code, shopName);
        shopifyWebhookService.subscribeToWebhooks(shopName);

        OauthState oauthState = oauthStateMapper.getOauthState(state);
        User user = userMapper.getUser(oauthState.getUserId());
        user.setShopName(accessToken.getShopName());
        userMapper.updateUser(user);
        oauthStateMapper.deleteOauthState(state);

        return new RedirectView("/");
    }

    @RequestMapping(value = "/authorize", method = RequestMethod.GET)
    public ModelAndView authorize(@QueryParam("userId") int userId, @QueryParam("shopName") String shopName) {
        String nonce = UUID.randomUUID().toString();

        oauthStateMapper.createOauthState(new OauthState(nonce, userId));

        return new ModelAndView("redirect:" + SHOPIFY_AUTHORIZE_URL.replace("{shopName}", shopName) + "&state=" + nonce);
    }
}
