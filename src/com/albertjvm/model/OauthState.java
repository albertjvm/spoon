package com.albertjvm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
public class OauthState {

    @Getter @Setter
    private String id;
    @Getter @Setter
    private int userId;
}
