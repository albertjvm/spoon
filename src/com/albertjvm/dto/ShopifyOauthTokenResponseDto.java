package com.albertjvm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class ShopifyOauthTokenResponseDto {
    @JsonProperty("access_token") @Getter @Setter
    private String accessToken;

    @JsonProperty @Getter @Setter
    private String scope;

    @JsonProperty("expires_in") @Getter @Setter
    private Integer expiresIn;
}
