package com.omf.rest;

import com.omf.exception.RestaurantBusinessException;
import com.omf.exception.RestaurantError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static List<RestaurantError> restaurantErrors = new ArrayList<>();

    @ExceptionHandler(RestaurantBusinessException.class)
    public ResponseEntity<Object> handleRestaurantBusinessException(RestaurantBusinessException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getRestaurantErrorList(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
