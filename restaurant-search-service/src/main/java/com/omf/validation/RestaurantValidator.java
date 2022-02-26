package com.omf.validation;

import com.omf.exception.ErrorConstant;
import com.omf.exception.RestaurantBusinessException;
import com.omf.exception.RestaurantError;
import com.omf.rest.model.RestaurantRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class RestaurantValidator {

    private List<RestaurantError> restaurantErrors = new ArrayList<>();

    public void validateRestaurant(RestaurantRequest o) throws RestaurantBusinessException {
        if (StringUtils.isBlank(o.getName())) restaurantErrors.add(RestaurantError.build(ErrorConstant.REST100));
        if (StringUtils.isBlank(o.getLocation())) restaurantErrors.add(RestaurantError.build(ErrorConstant.REST101));
        if (o.getDistance() <= 0) restaurantErrors.add(RestaurantError.build(ErrorConstant.REST102));
        if (StringUtils.isBlank(o.getCuisine())) restaurantErrors.add(RestaurantError.build(ErrorConstant.REST103));
        if (o.getBudget() <= 0) restaurantErrors.add(RestaurantError.build(ErrorConstant.REST104));
        if (CollectionUtils.isNotEmpty(restaurantErrors)) throw new RestaurantBusinessException(restaurantErrors);
    }
}