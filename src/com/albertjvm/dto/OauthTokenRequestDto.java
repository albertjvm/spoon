package com.albertjvm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class OauthTokenRequestDto {
    @JsonProperty("client_id") @Getter @Setter
    private String clientId;

    @JsonProperty("client_secret") @Getter @Setter
    private String clientSecret;

    @JsonProperty @Getter @Setter
    private String code;

    @JsonProperty("redirect_uri") @Getter @Setter
    private String redirectUri;
}
