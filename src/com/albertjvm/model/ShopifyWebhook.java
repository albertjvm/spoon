package com.albertjvm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
public class ShopifyWebhook {
    @Getter @Setter
    private String topic;

    @Getter @Setter
    private String address;

    @Getter @Setter
    private String format;
}
