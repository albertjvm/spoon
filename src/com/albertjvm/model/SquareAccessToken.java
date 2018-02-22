package com.albertjvm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor @AllArgsConstructor
public class SquareAccessToken {
    @Getter @Setter
    private String accessToken;
    @Getter @Setter
    private String merchantId;
    @Getter @Setter
    private String tokenType;
    @Getter @Setter
    private Date expiresAt;
}
