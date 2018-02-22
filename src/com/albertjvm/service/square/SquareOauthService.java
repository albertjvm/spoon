package com.albertjvm.service.square;

import com.albertjvm.data.SquareAccessTokenMapper;
import com.albertjvm.dto.OauthRenewTokenDto;
import com.albertjvm.dto.OauthTokenRequestDto;
import com.albertjvm.dto.SquareOauthTokenResponseDto;
import com.albertjvm.model.SquareAccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class SquareOauthService {

    private final static String CONNECT_BASE_URL = "https://connect.squareup.com";
    private final static String CLIENT_ID = "sq0idp-aLcZa0mXJmRDknr_zuCXCg";
    private final static String CLIENT_SECRET = "sq0csp-hfYTS8_ecDxN3GK-ti5f5pZP5On4K1ylAqGx6mup_OE";

    private SquareAccessTokenMapper squareAccessTokenMapper;

    public SquareOauthService(SquareAccessTokenMapper squareAccessTokenMapper) {
        this.squareAccessTokenMapper = squareAccessTokenMapper;

        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public SquareAccessToken getToken(String authCode) throws JSONException {
        SquareAccessToken result = null;
        OauthTokenRequestDto requestDto = new OauthTokenRequestDto();
        requestDto.setClientId(CLIENT_ID);
        requestDto.setClientSecret(CLIENT_SECRET);
        requestDto.setCode(authCode);

        HttpResponse<SquareOauthTokenResponseDto> response;
        try {
            response = Unirest.post(CONNECT_BASE_URL + "/oauth2/token")
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(requestDto)
                    .asObject(SquareOauthTokenResponseDto.class);

        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }

        if (response != null) {
            SquareOauthTokenResponseDto responseDto = response.getBody();
            result = createAndSaveToken(responseDto);
        }

        return result;
    }

    public String getAndRefreshTokenForMerchant(String merchantId) {

        SquareAccessToken squareAccessToken = squareAccessTokenMapper.getTokenByMerchantId(merchantId);

        if (squareAccessToken.getExpiresAt().before(new Date())) {
            squareAccessToken = renewToken(squareAccessToken.getAccessToken());
        }

        return squareAccessToken.getAccessToken();
    }

    public SquareAccessToken renewToken(String token) {
        SquareAccessToken result = null;
        OauthRenewTokenDto renewTokenDto = new OauthRenewTokenDto();
        renewTokenDto.setAccessToken(token);

        HttpResponse<SquareOauthTokenResponseDto> response;
        try {
            response = Unirest.post(CONNECT_BASE_URL + "/oauth2/clients/" + CLIENT_ID + "/access-token/renew")
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(renewTokenDto)
                    .asObject(SquareOauthTokenResponseDto.class);

        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }

        if (response != null) {
            SquareOauthTokenResponseDto responseDto = response.getBody();
            result = createAndSaveToken(responseDto);
        }
        return result;
    }

    private SquareAccessToken createAndSaveToken(SquareOauthTokenResponseDto responseDto) {
        SquareAccessToken accessToken = new SquareAccessToken(
                responseDto.getAccessToken(),
                responseDto.getMerchantId(),
                responseDto.getTokenType(),
                responseDto.getExpiresAt()
        );

        if (squareAccessTokenMapper.getTokenByMerchantId(responseDto.getMerchantId()) != null) {
            squareAccessTokenMapper.updateSquareToken(accessToken);
        } else {
            squareAccessTokenMapper.insertSquareToken(accessToken);
        }

        return accessToken;
    }
}
