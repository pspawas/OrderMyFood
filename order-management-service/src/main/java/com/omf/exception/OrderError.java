package com.omf.exception;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderError {

    private String transactionId;
    private long dateTime;
    private String errorCode;
    private String details;

    public static OrderError build(final String errorCode, final String details){
        OrderError o = new OrderError();
        o.setDetails(details);
        o.setErrorCode(errorCode);
        o.setTransactionId(UUID.randomUUID().toString());
        o.setDateTime(System.currentTimeMillis());
        return o;
    }
}
