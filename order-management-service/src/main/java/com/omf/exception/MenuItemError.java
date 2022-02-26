package com.omf.exception;

import lombok.Data;

import java.util.UUID;

@Data
public class MenuItemError {
    private String transactionId;
    private long dateTime;
    private String errorCode;
    private String details;

    public static MenuItemError buildFromCodeAndDetails(final String errorCode, final String details){
        MenuItemError o = new MenuItemError();
        o.setDetails(details);
        o.setErrorCode(errorCode);
        o.setTransactionId(UUID.randomUUID().toString());
        o.setDateTime(System.currentTimeMillis());
        return o;
    }
}
