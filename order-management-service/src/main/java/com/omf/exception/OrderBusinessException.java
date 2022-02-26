package com.omf.exception;

import java.util.List;

public class OrderBusinessException  extends RuntimeException {
    private transient List<OrderError> orderErrorList;

    public OrderBusinessException(final List<OrderError> orderErrors) {
        this.orderErrorList = orderErrors;
    }

    public OrderBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderBusinessException(String errorCode) {
        this.orderErrorList = ErrorConstant.getError(errorCode);
    }

    public OrderBusinessException(Throwable cause) {
        super(cause);
    }

    public OrderBusinessException() {
        super();
    }

    public List<OrderError> getOrderErrorList() {
        return this.orderErrorList;
    }

    public void setOrderErrorList(final List<OrderError> orderErrorList) {
        this.orderErrorList = orderErrorList;
    }

    public int hashCode() {
        return 1;
    }

    public String toString() {
        return "OrderBusinessException(orderErrors = " + this.getOrderErrorList();
    }
}