package com.albertjvm.controller.shopify;

import com.albertjvm.model.ShopifyProduct;
import com.albertjvm.model.ShopifyVariant;
import com.albertjvm.service.shopify.ShopifyProductService;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "/shopify/shops/{shopName}/products")
public class ShopifyProductController {

    private final ShopifyProductService shopifyProductService;

    @Autowired
    public ShopifyProductController(ShopifyProductService shopifyProductService) {
        this.shopifyProductService = shopifyProductService;
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public ResponseEntity importVariantsFromShopify(@PathVariable("shopName") String shopName) {
        ResponseEntity response = null;

        try {
            List<ShopifyVariant> variants = shopifyProductService.fetchAndSaveAllVariants(shopName);
            response = new ResponseEntity<>(variants, HttpStatus.OK);
        } catch (UnirestException e) {
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }

        return response;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity queryProductsBySku(@PathVariable("shopName") String shopName) {
        ResponseEntity response = null;

        try {
            List<ShopifyProduct> variants = shopifyProductService.getAllProducts(shopName);
            response = new ResponseEntity<>(variants, HttpStatus.OK);
        } catch (UnirestException e) {
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }

        return response;
    }
}
