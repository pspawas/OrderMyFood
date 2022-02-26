package com.omf.rest;

import com.omf.model.Order;
import com.omf.model.RestaurantResponse;
import com.omf.rest.model.OrderResponse;
import com.omf.rest.model.RestaurantRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

public class OrderRestApiTest extends AbstractOrderRestApiTest {

    private String getErrorCode(String message) {
        return message.split(",")[2].replace("\"", "").split(":")[1];
    }

    private ResponseEntity<RestaurantResponse> createTestRestaurantData() {
        String baseURL = "http://localhost:" + mongoDBPort + "/api/restaurants";
        RestaurantRequest restaurantRequest = createRestaurantRequest();
        HttpEntity<RestaurantRequest> entity = new HttpEntity<>(restaurantRequest, httpHeaders);
        return restTemplate.postForEntity(baseURL, entity, RestaurantResponse.class);
    }

    private ResponseEntity<OrderResponse> createTestOrderData() {
        ResponseEntity<RestaurantResponse> responseEntity = createTestRestaurantData();
        OrderRequest orderRequest = createOrderRequest();
        HttpEntity<OrderRequest> entity = new HttpEntity<>(orderRequest, httpHeaders);
        String baseURL = "http://localhost:" + mongoDBPort + "/api/restaurants/" + responseEntity.getBody().getRestaurantId() + "/orders";
        return restTemplate.postForEntity(baseURL, entity, OrderResponse.class);
    }

    @Test
    public void createOrderData_1() {
        ResponseEntity<RestaurantResponse> responseEntity = createTestRestaurantData();
        OrderRequest orderRequest = createOrderRequest();
        HttpEntity<OrderRequest> entity = new HttpEntity<>(orderRequest, httpHeaders);
        String baseURL = "http://localhost:" + mongoDBPort + "/api/restaurants/" + responseEntity.getBody().getRestaurantId() + "/orders";
        ResponseEntity<OrderResponse> orderResponse = restTemplate.postForEntity(baseURL, entity, OrderResponse.class);
        Assertions.assertEquals(201, orderResponse.getStatusCode().value());
    }

    @Test
    public void createOrderData_2() {
        ResponseEntity<RestaurantResponse> responseEntity = createTestRestaurantData();
        OrderRequest orderRequest = createOrderRequest();
        HttpEntity<OrderRequest> entity = new HttpEntity<>(orderRequest, httpHeaders);
        String baseURL = "http://localhost:" + mongoDBPort + "/api/restaurants//orders";
        try {
            restTemplate.postForEntity(baseURL, entity, OrderResponse.class);
        } catch (HttpClientErrorException e) {
            Assertions.assertEquals(404, e.getRawStatusCode());
        }
    }

    @Test
    public void createOrderData_3() {
        ResponseEntity<OrderResponse> responseEntity = createTestOrderData();
        OrderRequest orderRequest = createOrderRequest();
        HttpEntity<OrderRequest> entity = new HttpEntity<>(orderRequest, httpHeaders);
        String baseURL = "http://localhost:" + mongoDBPort + "/api/restaurants/" + responseEntity.getBody().getRestaurantId() + "/orders";
        try {
            restTemplate.postForEntity(baseURL, entity, OrderResponse.class);
        } catch (HttpServerErrorException e) {
            Assertions.assertEquals("ORDER90", getErrorCode(e.getMessage()));
        }
    }

    @Test
    public void createOrderData_4() {
        ResponseEntity<OrderResponse> responseEntity = createTestOrderData();
        OrderRequest orderRequest = createOrderRequest();
        orderRequest.getItemDetail().setQuantity(20);
        orderRequest.setRestaurantId(responseEntity.getBody().getRestaurantId());
        HttpEntity<OrderRequest> entity = new HttpEntity<>(orderRequest, httpHeaders);
        String baseURL = "http://localhost:" + mongoDBPort + "/api/restaurants/" + responseEntity.getBody().getRestaurantId() + "/orders";
        restTemplate.put(baseURL, entity, OrderResponse.class);

        String baseURLForViewOrders = "http://localhost:" + mongoDBPort + "/api/customers/" + orderRequest.getCustomerName();
        ParameterizedTypeReference<List<Order>> responseType = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<List<Order>> responseEntity1 = restTemplate.exchange(baseURLForViewOrders, HttpMethod.GET, null, responseType);
        Assertions.assertEquals(20, responseEntity1.getBody().get(0).getItemDetail().getQuantity());
    }
}
