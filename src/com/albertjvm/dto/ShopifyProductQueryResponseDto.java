package com.albertjvm.dto;

import com.albertjvm.model.ShopifyProduct;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor
public class ShopifyProductQueryResponseDto {

    @JsonProperty @Getter @Setter
    private List<ShopifyProduct> products;
}
