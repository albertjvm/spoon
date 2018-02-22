package com.albertjvm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
public class ShopifyAccessToken {

    @Getter @Setter
    private String accessToken;

    @Getter @Setter
    private String scope;

    @Getter @Setter
    private Integer expiresIn;

    @Getter @Setter
    private String shopName;
}
