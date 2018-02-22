package com.albertjvm.service.shopify;

import com.albertjvm.dto.ShopifyWebhookSubcriptionDto;
import com.albertjvm.model.ShopifyWebhook;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopifyWebhookService extends ShopifyService {

    private final String [] TOPICS = { "products/create", "products/update" };

    @Autowired
    public ShopifyWebhookService(ShopifyOauthService shopifyOauthService) {
        super(shopifyOauthService);
    }

    public void subscribeToWebhooks(String shop) {
        for (String topic : TOPICS) {
            subscribeToWebhook(shop, topic);
        }
    }

    private void subscribeToWebhook(String shop, String topic) {
        ShopifyWebhookSubcriptionDto subcriptionDto = new ShopifyWebhookSubcriptionDto(
                new ShopifyWebhook(topic, "http://shopifytest.requestcatcher.com/test", "json")
        );

        try {
            Unirest.post(baseUrl(shop) + "/webhooks.json")
                    .header("X-Shopify-Access-Token", shopifyAccessToken(shop).getAccessToken())
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(subcriptionDto)
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}
