package com.omf.exception;

import java.util.List;

public class RestaurantBusinessException extends RuntimeException {

    private static final long serialVersionUID = -276793195697383092L;
    private transient List<RestaurantError> restaurantErrorList;

    public RestaurantBusinessException(List<RestaurantError> restaurantErrors) {
        restaurantErrorList = restaurantErrors;
    }

    public RestaurantBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestaurantBusinessException(String errorCode) {
        restaurantErrorList = ErrorConstant.getError(errorCode);
    }

    public RestaurantBusinessException(Throwable cause) {
        super(cause);
    }

    public RestaurantBusinessException() {
        super();
    }

    public List<RestaurantError> getRestaurantErrorList() {
        return restaurantErrorList;
    }

    public void setRestaurantErrorList(List<RestaurantError> restaurantErrorList) {
        this.restaurantErrorList = restaurantErrorList;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "RestaurantBusinessException(restaurantErrors = " + getRestaurantErrorList();
    }
}
