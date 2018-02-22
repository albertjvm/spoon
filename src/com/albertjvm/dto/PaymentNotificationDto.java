package com.albertjvm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
public class PaymentNotificationDto {
    @JsonProperty("entity_id")
    @Getter @Setter
    private String entityId;

    @JsonProperty("event_type")
    @Getter @Setter
    private String eventType;

    @JsonProperty("location_id")
    @Getter @Setter
    private String locationId;

    @JsonProperty("merchant_id")
    @Getter @Setter
    private String merchantId;
}
