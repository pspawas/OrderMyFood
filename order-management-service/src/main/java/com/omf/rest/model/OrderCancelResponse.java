package com.omf.rest.model;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderCancelResponse {

    private String transactionId;
    private String status;
}
