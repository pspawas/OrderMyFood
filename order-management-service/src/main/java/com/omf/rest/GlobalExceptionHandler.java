package com.omf.rest;

import com.omf.exception.OrderBusinessException;
import com.omf.exception.OrderError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static List<OrderError> restaurantErrors = new ArrayList<>();

    @ExceptionHandler(OrderBusinessException.class)
    public ResponseEntity<Object> handleOrderBusinessException(OrderBusinessException ex, WebRequest request) {
        System.out.println(ex.getMessage());
        return new ResponseEntity<>(ex.getOrderErrorList(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
