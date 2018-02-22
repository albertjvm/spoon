package com.albertjvm.service.shopify;

import com.albertjvm.data.ShopifyAccessTokenMapper;
import com.albertjvm.dto.OauthTokenRequestDto;
import com.albertjvm.dto.ShopifyOauthTokenResponseDto;
import com.albertjvm.model.ShopifyAccessToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Service;

@Service
public class ShopifyOauthService {

    private final static String CLIENT_ID = "aa22486dd71295321f9376463cf5da0f";
    private final static String CLIENT_SECRET = "e5bb4b31f4d755c435a35655464a5934";

    private ShopifyAccessTokenMapper shopifyAccessTokenMapper;

    public ShopifyOauthService(ShopifyAccessTokenMapper shopifyAccessTokenMapper) {
        this.shopifyAccessTokenMapper = shopifyAccessTokenMapper;
    }

    public ShopifyAccessToken getToken(String authCode, String shop) {
        ShopifyAccessToken result = null;
        OauthTokenRequestDto requestDto = new OauthTokenRequestDto();
        requestDto.setClientId(CLIENT_ID);
        requestDto.setClientSecret(CLIENT_SECRET);
        requestDto.setCode(authCode);

        HttpResponse<ShopifyOauthTokenResponseDto> response;

        try {
            response = Unirest.post("https://" + shop + ".myshopify.com/admin/oauth/access_token")
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(requestDto)
                    .asObject(ShopifyOauthTokenResponseDto.class);
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }

        if (response != null) {
            ShopifyOauthTokenResponseDto responseDto = response.getBody();
            result = createAndSaveToken(responseDto, shop);
        }

        return result;
    }

    public ShopifyAccessToken getTokenForShop(String shop) {
        return shopifyAccessTokenMapper.getTokenByShopName(shop);
    }

    private ShopifyAccessToken createAndSaveToken(ShopifyOauthTokenResponseDto responseDto, String shopName) {
        ShopifyAccessToken accessToken = new ShopifyAccessToken(
                responseDto.getAccessToken(),
                responseDto.getScope(),
                responseDto.getExpiresIn(),
                shopName
        );

        if (shopifyAccessTokenMapper.getTokenByShopName(shopName) != null) {
            shopifyAccessTokenMapper.updateShopifyToken(accessToken);
        } else {
            shopifyAccessTokenMapper.insertShopifyToken(accessToken);
        }

        return accessToken;
    }
}
