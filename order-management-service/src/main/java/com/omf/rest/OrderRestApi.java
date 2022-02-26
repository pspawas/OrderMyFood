package com.omf.rest;

import com.omf.model.Order;
import com.omf.rest.model.OrderCancelRequest;
import com.omf.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderRestApi {
    private OrderService orderService;

    @Autowired
    public OrderRestApi(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/restaurants/{restaurantId}/orders", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Object> createOder(@RequestBody OrderRequest orderRequest, @PathVariable String restaurantId) {
        orderRequest.setRestaurantId(restaurantId);
        // Validation will be created
        return new ResponseEntity<>(orderService.createOrder(orderRequest), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/restaurants/{restaurantId}/orders", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Object> updateOder(@RequestBody OrderRequest orderRequest, @PathVariable String restaurantId) {
        orderRequest.setRestaurantId(restaurantId);
        // Validation will be created
        return new ResponseEntity<>(orderService.updateOrder(orderRequest), HttpStatus.OK);
    }

    @RequestMapping(value = "/orders/cancel", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Object> cancelOder(@RequestBody OrderCancelRequest orderCancelRequest) {
        // Validation will be created
        return new ResponseEntity<>(orderService.cancelOrder(orderCancelRequest), HttpStatus.OK);
    }

    @RequestMapping(value = "/customers/{customerName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<List<Order>> getOrder(@PathVariable String customerName) {
        return new ResponseEntity<>(orderService.viewOrders(customerName), HttpStatus.OK);
    }
}
