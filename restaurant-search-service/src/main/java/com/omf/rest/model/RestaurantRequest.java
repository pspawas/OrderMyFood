package com.omf.rest.model;

import lombok.Data;

@Data
public class RestaurantRequest {
    private String transactionId;
    private String name;
    private String location;
    int distance;
    String cuisine;
    int budget;
}
