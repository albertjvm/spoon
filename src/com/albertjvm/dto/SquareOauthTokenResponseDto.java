package com.albertjvm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class SquareOauthTokenResponseDto {
    @JsonProperty("access_token") @Getter @Setter
    private String accessToken;

    @JsonProperty("token_type") @Getter @Setter
    private String tokenType;

    @JsonProperty("expires_at") @Getter @Setter
    private Date expiresAt;

    @JsonProperty("merchant_id") @Getter @Setter
    private String merchantId;
}
