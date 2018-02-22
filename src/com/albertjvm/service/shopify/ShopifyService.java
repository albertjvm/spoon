package com.albertjvm.service.shopify;

import com.albertjvm.model.ShopifyAccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("shopifyService")
public abstract class ShopifyService {
    private final ShopifyOauthService shopifyOauthService;

    @Autowired
    public ShopifyService(ShopifyOauthService shopifyOauthService) {
        this.shopifyOauthService = shopifyOauthService;
    }

    protected ShopifyAccessToken shopifyAccessToken(String shop) {
        return shopifyOauthService.getTokenForShop(shop);
    }

    protected String baseUrl(String shop) {
        return "https://" + shop + ".myshopify.com/admin";
    }
}
