package com.omf.service.impl;

import com.mongodb.client.result.UpdateResult;
import com.omf.constant.OrderMessage;
import com.omf.exception.ErrorConstant;
import com.omf.exception.OrderBusinessException;
import com.omf.model.Order;
import com.omf.repository.OrderRepository;
import com.omf.rest.OrderRequest;
import com.omf.rest.model.OrderCancelRequest;
import com.omf.rest.model.OrderCancelResponse;
import com.omf.rest.model.OrderResponse;
import com.omf.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {

        List<Order> orders = orderRepository.findByCustomerNameAndItemDetailName(orderRequest.getCustomerName(), orderRequest.getItemDetail().getName());
        OrderResponse orderResponse = null;
        if (orders != null && orders.size() == 0) {
            orderRequest.setTransactionId(UUID.randomUUID().toString());
            orderRequest.setTotalPrice(orderRequest.getItemDetail().getPrice() * orderRequest.getItemDetail().getQuantity());
            orderRequest.setOrderTime(System.currentTimeMillis());
            orderRequest.setDeliveryTime(System.currentTimeMillis() + 300000);
            orderRequest.setPaymentId(orderRequest.getCustomerCellNo() + "_ORG");

            orderResponse = new OrderResponse();
            Order order = new Order();
            BeanUtils.copyProperties(orderRequest, order);
            order.setStatus("NEW");
            orderRepository.save(order);
            orderResponse.setRestaurantId(order.getRestaurantId());
            orderResponse.setOrderId(order.getOrderId());
            orderResponse.setTransactionId(UUID.randomUUID().toString());
            orderResponse.setStatus(OrderMessage.CREATED);
        } else throw new OrderBusinessException(ErrorConstant.ORDER90);
        return orderResponse;
    }

    @Override
    public OrderResponse updateOrder(OrderRequest orderRequest) {

        OrderResponse orderResponse = null;
        List<Order> ordersForRestaurantId = orderRepository.findByRestaurantId(orderRequest.getRestaurantId());

        if (ordersForRestaurantId != null && ordersForRestaurantId.size() == 0)
            throw new OrderBusinessException(ErrorConstant.ORDER91);

        List<Order> ordersForCustomerNameAndItem = orderRepository.findByCustomerNameAndItemDetailName(orderRequest.getCustomerName(), orderRequest.getItemDetail().getName());

        if (ordersForCustomerNameAndItem != null && ordersForCustomerNameAndItem.size() == 0)
            throw new OrderBusinessException(ErrorConstant.ITEM91);

        if (ordersForCustomerNameAndItem != null && ordersForCustomerNameAndItem.size() == 1) {
            orderRequest.setTransactionId(UUID.randomUUID().toString());
            orderRequest.setTotalPrice(orderRequest.getItemDetail().getPrice() * orderRequest.getItemDetail().getQuantity());
            orderRequest.setPaymentId(orderRequest.getCustomerCellNo() + "_REVISED");

            Query query = new Query();
            query.addCriteria(Criteria.where("customerName").is(orderRequest.getCustomerName()));
            query.addCriteria(Criteria.where("itemDetail.name").is(orderRequest.getItemDetail().getName()));
            Update update = new Update();
            update.set("itemDetail", orderRequest.getItemDetail());
            update.set("totalPrice", orderRequest.getTotalPrice());
            update.set("paymentId", orderRequest.getCustomerCellNo() + "_REVISED");
            update.set("status", "UPDATED");
            UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Order.class);

            if (updateResult.getModifiedCount() == 1) {
                orderResponse = new OrderResponse();
                orderResponse.setTransactionId(UUID.randomUUID().toString());
                orderResponse.setRestaurantId(orderRequest.getRestaurantId());
                orderResponse.setStatus(OrderMessage.MODIFIED);
                orderResponse.setOrderId(ordersForCustomerNameAndItem.get(0).getOrderId());
            } else throw new OrderBusinessException(ErrorConstant.ORDER105);
        } else throw new OrderBusinessException(ErrorConstant.ORDER90);
        return orderResponse;
    }

    @Override
    public OrderCancelResponse cancelOrder(OrderCancelRequest orderCancelRequest) {

        Arrays.stream(orderCancelRequest.getOrderIds()).forEach(
                orderId -> {
                    Order order = orderRepository.getByOrderIdAndCustomerName(orderId, orderCancelRequest.getCustomerName());
                    if (order == null) throw new OrderBusinessException(ErrorConstant.ORDER92);
                }
        );
        Query query = new Query();
        query.addCriteria(Criteria.where("orderId").in(orderCancelRequest.getOrderIds()));
        query.addCriteria(Criteria.where("customerName").is(orderCancelRequest.getCustomerName()));
        Update update = new Update();
        update.set("status", "CANCELED");
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, Order.class);
        OrderCancelResponse orderCancelResponse = null;
        if (updateResult.getModifiedCount() != 0 && updateResult.getModifiedCount() == orderCancelRequest.getOrderIds().length) {
            orderCancelResponse = new OrderCancelResponse();
            orderCancelResponse.setTransactionId(UUID.randomUUID().toString());
            orderCancelResponse.setStatus(OrderMessage.CANCELED);
        } else if (updateResult.getModifiedCount() != 0 && updateResult.getModifiedCount() < orderCancelRequest.getOrderIds().length)
            throw new OrderBusinessException(ErrorConstant.ORDER107);
        else
            throw new OrderBusinessException(ErrorConstant.ORDER106);
        return orderCancelResponse;
    }

    @Override
    public List<Order> viewOrders(String customerName) {
        List<Order> orders = orderRepository.findByCustomerName(customerName);
        if (orders != null && orders.size() == 0) throw new OrderBusinessException(ErrorConstant.CUSTOMER91);
        return orders;
    }
}
