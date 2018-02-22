package com.albertjvm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
public class ItemQuantity {
    @Getter @Setter
    private String sku;

    @Getter @Setter
    private int quantity;

    @Getter @Setter
    private String name;
}
