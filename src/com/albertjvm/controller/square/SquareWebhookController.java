package com.albertjvm.controller.square;

import com.albertjvm.data.UserMapper;
import com.albertjvm.dto.PaymentNotificationDto;
import com.albertjvm.model.ItemQuantity;
import com.albertjvm.model.User;
import com.albertjvm.service.shopify.ShopifyProductService;
import com.albertjvm.service.square.SquareTransactionService;
import com.albertjvm.service.square.SquareWebhookService;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.squareup.connect.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.ws.rs.QueryParam;
import java.util.List;

@Controller
@RequestMapping(value = "/square/webhook")
public class SquareWebhookController {

    private final SquareWebhookService squareWebhookService;
    private final SquareTransactionService squareTransactionService;
    private final ShopifyProductService shopifyProductService;
    private final UserMapper userMapper;

    @Autowired
    public SquareWebhookController(SquareWebhookService squareWebhookService, SquareTransactionService squareTransactionService, UserMapper userMapper, ShopifyProductService shopifyProductService) {
        this.squareWebhookService = squareWebhookService;
        this.squareTransactionService = squareTransactionService;
        this.userMapper = userMapper;
        this.shopifyProductService = shopifyProductService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity handleWebhook(@RequestBody PaymentNotificationDto paymentNotificationDto) {
       ResponseEntity responseEntity;

        try {
            List<ItemQuantity> itemQuantities = squareTransactionService.getTransaction(
                    paymentNotificationDto.getMerchantId(),
                    paymentNotificationDto.getLocationId(),
                    paymentNotificationDto.getEntityId()
            );
            User user = userMapper.getUserByMerchantId(paymentNotificationDto.getMerchantId());
            shopifyProductService.updateProductInventory(user.getShopName(), itemQuantities);

            responseEntity = new ResponseEntity<>(paymentNotificationDto, HttpStatus.OK);
        } catch (ApiException | UnirestException e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @RequestMapping(value = "/subscribe")
    public ResponseEntity subscribeToWebhooks(@QueryParam("merchantId") String merchantId) {
        ResponseEntity responseEntity;

        try {
            squareWebhookService.subscribeToWebhooks(merchantId);
            responseEntity = new ResponseEntity (HttpStatus.OK);
        } catch (ApiException e) {
            responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }
}
