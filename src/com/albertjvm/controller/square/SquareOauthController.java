package com.albertjvm.controller.square;

import com.albertjvm.data.OauthStateMapper;
import com.albertjvm.data.UserMapper;
import com.albertjvm.model.OauthState;
import com.albertjvm.model.SquareAccessToken;
import com.albertjvm.model.User;
import com.albertjvm.service.square.SquareOauthService;
import com.albertjvm.service.square.SquareWebhookService;
import com.mashape.unirest.http.Unirest;
import com.squareup.connect.ApiException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import java.util.UUID;

@Controller
@RequestMapping(value = "/square/oauth")
public class SquareOauthController {

    private final SquareOauthService squareOauthService;
    private final SquareWebhookService squareWebhookService;
    private final OauthStateMapper oauthStateMapper;
    private final UserMapper userMapper;

    private final String SQUARE_AUTHORIZE_URL = "https://connect.squareup.com/oauth2/authorize?client_id=sq0idp-aLcZa0mXJmRDknr_zuCXCg&scope=PAYMENTS_READ%20MERCHANT_PROFILE_READ";

    @Autowired
    public SquareOauthController(SquareOauthService squareOauthService, SquareWebhookService squareWebhookService, OauthStateMapper oauthStateMapper, UserMapper userMapper) {
        this.squareOauthService = squareOauthService;
        this.squareWebhookService = squareWebhookService;
        this.oauthStateMapper = oauthStateMapper;
        this.userMapper = userMapper;
    }

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ResponseEntity getAndSaveToken(@QueryParam("code") String authCode) throws JSONException {
        SquareAccessToken accessToken = squareOauthService.getToken(authCode);

        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public RedirectView handleCallback(@QueryParam("code") String code, @QueryParam("state") String state) throws JSONException, ApiException {
        SquareAccessToken accessToken = squareOauthService.getToken(code);
        squareWebhookService.subscribeToWebhooks(accessToken.getMerchantId());

        OauthState oauthState = oauthStateMapper.getOauthState(state);
        User user = userMapper.getUser(oauthState.getUserId());
        user.setMerchantId(accessToken.getMerchantId());
        userMapper.updateUser(user);
        oauthStateMapper.deleteOauthState(state);

        return new RedirectView("/");
    }

    @RequestMapping(value = "/authorize", method = RequestMethod.GET)
    public ModelAndView authorize(@QueryParam("userId") int userId) {
        String stateId = UUID.randomUUID().toString();

        oauthStateMapper.createOauthState(new OauthState(stateId, userId));

        return new ModelAndView("redirect:" + SQUARE_AUTHORIZE_URL + "&state=" + stateId);
    }
}
