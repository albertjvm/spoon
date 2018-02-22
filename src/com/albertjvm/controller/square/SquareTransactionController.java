package com.albertjvm.controller.square;

import com.albertjvm.model.ItemQuantity;
import com.albertjvm.service.square.SquareTransactionService;
import com.squareup.connect.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "/square/merchants/{merchantId}/locations/{locationId}/transactions")
public class SquareTransactionController {

    private final SquareTransactionService squareTransactionService;

    @Autowired
    public SquareTransactionController(SquareTransactionService squareTransactionService) {
        this.squareTransactionService = squareTransactionService;
    }

    @RequestMapping(value = "/{transactionId}", method = RequestMethod.GET)
    public ResponseEntity getTransaction (
            @PathVariable("transactionId") String transactionId,
            @PathVariable("locationId") String locationId,
            @PathVariable("merchantId") String merchantId) {

        ResponseEntity responseEntity;

        if (locationId == null || merchantId == null) {
            responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            try {
                List<ItemQuantity> itemQuantities = squareTransactionService.getTransaction(merchantId, locationId, transactionId);
                responseEntity = new ResponseEntity<>(itemQuantities, HttpStatus.OK);
            } catch (ApiException e) {
                responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }
        }

        return responseEntity;
    }
}
