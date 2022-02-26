package com.omf.service;

import com.omf.model.Order;
import com.omf.rest.OrderRequest;
import com.omf.rest.model.OrderCancelRequest;
import com.omf.rest.model.OrderCancelResponse;
import com.omf.rest.model.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);

    public OrderResponse updateOrder(OrderRequest orderRequest);

    public OrderCancelResponse cancelOrder(OrderCancelRequest orderCancelRequest);

    public List<Order> viewOrders(String customerName);
}
