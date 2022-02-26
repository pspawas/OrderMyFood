package com.omf.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MenuItemResponse {
    private String transactionId;
    private String status;
    private String restaurantId;
}
