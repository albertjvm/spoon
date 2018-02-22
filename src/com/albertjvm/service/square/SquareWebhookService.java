package com.albertjvm.service.square;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.squareup.connect.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SquareWebhookService {
    private final static String CONNECT_BASE_URL = "https://connect.squareup.com";

    private final SquareLocationService locationService;
    private final SquareOauthService oauthService;

    @Autowired
    public SquareWebhookService(SquareLocationService locationService, SquareOauthService oauthService) {
        this.locationService = locationService;
        this.oauthService = oauthService;
    }

    public void subscribeToWebhooks(String merchantId) throws ApiException {
        String accessToken = oauthService.getAndRefreshTokenForMerchant(merchantId);
        List<String> locationIds = locationService.getLocationIds(merchantId);

        for (String locationId : locationIds) {
            subscribeToLocationWebhooks(accessToken, locationId);
        }
    }

    private void subscribeToLocationWebhooks(String accessToken, String locationId) throws ApiException {
        try {
             Unirest.put(CONNECT_BASE_URL + "/v1/" + locationId + "/webhooks")
                     .header("Authorization", "Bearer " + accessToken)
                     .header("accept", "application/json")
                     .header("Content-Type", "application/json")
                     .body("[\"PAYMENT_UPDATED\"]")
                     .asString().getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}