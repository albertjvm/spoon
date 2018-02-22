package com.albertjvm.service.shopify;

import com.albertjvm.data.ShopifyVarientMapper;
import com.albertjvm.dto.ShopifyProductQueryResponseDto;
import com.albertjvm.model.ItemQuantity;
import com.albertjvm.model.ShopifyProduct;
import com.albertjvm.model.ShopifyVariant;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopifyProductService extends ShopifyService {

    private final ShopifyVarientMapper shopifyVarientMapper;

    @Autowired
    public ShopifyProductService(ShopifyOauthService shopifyOauthService, ShopifyVarientMapper shopifyVarientMapper) {
        super(shopifyOauthService);
        this.shopifyVarientMapper = shopifyVarientMapper;
    }

    public ShopifyVariant getVariantById(String shop, int variantId) throws UnirestException {
        HttpResponse<ShopifyVariant> response = Unirest.get(baseUrl(shop) + "/variants/" + variantId + ".json")
                .header("X-Shopify-Access-Token", shopifyAccessToken(shop).getAccessToken())
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .asObject(ShopifyVariant.class);

        return response.getBody();
    }

    public List<ShopifyProduct> getAllProducts(String shop) throws UnirestException {
        HttpResponse<ShopifyProductQueryResponseDto> response = Unirest.get(baseUrl(shop) + "/products")
                .header("X-Shopify-Access-Token", shopifyAccessToken(shop).getAccessToken())
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .asObject(ShopifyProductQueryResponseDto.class);

        return response.getBody().getProducts();
    }

    public List<ShopifyVariant> updateProductInventory(String shop, List<ItemQuantity> itemQuantities) throws UnirestException {
        List<ShopifyVariant> results = new ArrayList<>();

        for (ItemQuantity itemQuantity : itemQuantities) {
            results.add(updateProductInventory(shop, itemQuantity));
        }

        return results;
    }

    public ShopifyVariant updateProductInventory(String shop, ItemQuantity itemQuantity) throws UnirestException {
        ShopifyVariant variant = shopifyVarientMapper.getVariantBySku(itemQuantity.getSku());
        variant.setInventoryQuantityAdjustment(itemQuantity.getQuantity());

        HttpResponse<ShopifyVariant> response = Unirest.put(baseUrl(shop) + "/variants/" + variant.getId())
                .header("X-Shopify-Access-Token", shopifyAccessToken(shop).getAccessToken())
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(variant)
                .asObject(ShopifyVariant.class);

        return response.getBody();
    }

    public List<ShopifyVariant> saveAllVariants(ShopifyProduct product) {
        List<ShopifyVariant> variants = new ArrayList<>();

        for (ShopifyVariant variant : product.getVariants()) {
            saveVariant(variant);
            variants.add(variant);
        }

        return variants;
    }

    public List<ShopifyVariant> fetchAndSaveAllVariants(String shop) throws UnirestException {
        List<ShopifyProduct> products = getAllProducts(shop);
        List<ShopifyVariant> allVariants = new ArrayList<>();

        for (ShopifyProduct product : products) {
            allVariants.addAll(saveAllVariants(product));
        }

        return allVariants;
    }

    public void saveVariant(ShopifyVariant variant) {
        if (shopifyVarientMapper.getVariantBySku(variant.getSku()) != null) {
            shopifyVarientMapper.updateVariant(variant);
        } else {
            shopifyVarientMapper.insertVariant(variant);
        }
    }
}
