package com.omf.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@RequiredArgsConstructor(onConstructor = @__(@PersistenceConstructor))
@Document
public class Payment {
    @Id
    String id;

    private long timestamp;
    private int amount;
    private String orderId;
    private CreditCardInfo creditCardInfo;
}
