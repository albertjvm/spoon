package com.albertjvm.dto;

import com.albertjvm.model.ShopifyWebhook;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
public class ShopifyWebhookSubcriptionDto {
    @Getter @Setter
    private ShopifyWebhook webhook;
}
