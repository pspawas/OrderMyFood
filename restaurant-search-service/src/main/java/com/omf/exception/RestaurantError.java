package com.omf.exception;

import lombok.Data;

import java.util.UUID;

@Data
public class RestaurantError {
    private String transactionId;
    private long dateTime;
    private String errorCode;
    private String details;

    public static RestaurantError build(String errorCode) {
        RestaurantError o = new RestaurantError();
        o.setDetails(ErrorConstant.REST_ERRORS.get(errorCode));
        o.setErrorCode(errorCode);
        o.setTransactionId(UUID.randomUUID().toString());
        o.setDateTime(System.currentTimeMillis());
        return o;
    }
}
