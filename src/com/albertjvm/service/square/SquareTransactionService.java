package com.albertjvm.service.square;

import com.albertjvm.model.ItemQuantity;
import com.squareup.connect.ApiClient;
import com.squareup.connect.ApiException;
import com.squareup.connect.Configuration;
import com.squareup.connect.api.V1TransactionsApi;
import com.squareup.connect.auth.OAuth;
import com.squareup.connect.models.V1Payment;
import com.squareup.connect.models.V1PaymentItemization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SquareTransactionService {

    private final SquareOauthService squareOauthService;

    @Autowired
    public SquareTransactionService(SquareOauthService squareOauthService) {
        this.squareOauthService = squareOauthService;
    }

    public List<ItemQuantity> getTransaction(String merchantId, String locationId, String transactionId) throws ApiException {
        V1TransactionsApi transactionsApi = transactionsApi(squareOauthService.getAndRefreshTokenForMerchant(merchantId));
        V1Payment payment = transactionsApi.retrievePayment(locationId, transactionId);

        return payment.getItemizations().stream().map(this::buildItemQuantity).collect(Collectors.toList());
    }

    private ItemQuantity buildItemQuantity(V1PaymentItemization itemization) {
        ItemQuantity itemQuantity = new ItemQuantity();

        itemQuantity.setSku(itemization.getItemDetail().getSku());
        itemQuantity.setName(itemization.getName());
        itemQuantity.setQuantity(itemization.getQuantity().intValueExact());

        return itemQuantity;
    }

    private V1TransactionsApi transactionsApi (String accessToken) {
        ApiClient apiClient = Configuration.getDefaultApiClient();
        OAuth oAuth2 = (OAuth) apiClient.getAuthentication("oauth2");
        oAuth2.setAccessToken(accessToken);

        V1TransactionsApi transactionsApi = new V1TransactionsApi();
        transactionsApi.setApiClient(apiClient);

        return transactionsApi;
    }
}
