package com.omf.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RestaurantResponse {
    private String transactionId;
    private String status;
    private String restaurantId;
}
