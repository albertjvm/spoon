package com.albertjvm.controller.shopify;

import com.albertjvm.model.ShopifyProduct;
import com.albertjvm.service.shopify.ShopifyProductService;
import com.albertjvm.service.shopify.ShopifyWebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.ws.rs.QueryParam;

@Controller
@RequestMapping(value = "/shopify/webhook")
public class ShopifyWebhookController {

    private final ShopifyWebhookService shopifyWebhookService;
    private final ShopifyProductService shopifyProductService;

    @Autowired
    public ShopifyWebhookController(ShopifyWebhookService shopifyWebhookService, ShopifyProductService shopifyProductService) {
        this.shopifyWebhookService = shopifyWebhookService;
        this.shopifyProductService = shopifyProductService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity handleWebhook(@RequestBody ShopifyProduct product) {
        ResponseEntity responseEntity = new ResponseEntity<>(product, HttpStatus.OK);

        shopifyProductService.saveAllVariants(product);

        return responseEntity;
    }

    @RequestMapping(value = "/subscribe")
    public ResponseEntity subscribeToWebhooks(@QueryParam("shop") String shop) {
        shopifyWebhookService.subscribeToWebhooks(shop);

        return new ResponseEntity (HttpStatus.OK);
    }
}
