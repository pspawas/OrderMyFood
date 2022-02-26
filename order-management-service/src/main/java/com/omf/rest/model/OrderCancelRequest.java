package com.omf.rest.model;

import com.omf.model.ItemDetail;
import lombok.Data;

@Data
public class OrderCancelRequest {

    private String[] orderIds;
    private String customerName;
}
