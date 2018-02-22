package com.albertjvm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyProduct {

    @JsonProperty @Getter @Setter
    private long id;

    @JsonProperty @Getter @Setter
    private String title;

    @JsonProperty @Getter @Setter
    private ShopifyVariant [] variants;
}
