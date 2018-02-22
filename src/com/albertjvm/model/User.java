package com.albertjvm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@NoArgsConstructor @AllArgsConstructor
public class User {
    @Getter @Setter
    private int id;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String password;

    @Getter @Setter
    private String merchantId;

    @Getter @Setter
    private String shopName;

    @Getter @Setter
    private String firstName;

    @Getter @Setter
    private String lastName;
}
