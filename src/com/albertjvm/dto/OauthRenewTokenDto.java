package com.albertjvm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class OauthRenewTokenDto {
    @JsonProperty("access_token") @Getter @Setter
    private String accessToken;
}
