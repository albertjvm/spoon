package com.albertjvm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyVariant {

    @JsonProperty @Getter @Setter
    private long id;

    @JsonProperty @Getter @Setter
    private String sku;

    @JsonProperty("inventory_quantity_adjustment") @Getter @Setter
    private int inventoryQuantityAdjustment;
}
