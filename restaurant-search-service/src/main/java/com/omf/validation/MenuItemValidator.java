package com.omf.validation;

import com.omf.exception.ErrorConstant;
import com.omf.exception.RestaurantBusinessException;
import com.omf.exception.RestaurantError;
import com.omf.rest.model.MenuItemRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MenuItemValidator {

    private List<RestaurantError> restaurantErrors = new ArrayList<>();

    public void validateMenuItem(MenuItemRequest o) throws RestaurantBusinessException {
        if (StringUtils.isBlank(o.getName())) restaurantErrors.add(RestaurantError.build(ErrorConstant.MENU100));
        if (StringUtils.isBlank(o.getDescription())) restaurantErrors.add(RestaurantError.build(ErrorConstant.MENU101));
        if (o.getPrice() <= 0) restaurantErrors.add(RestaurantError.build(ErrorConstant.MENU102));
        if (CollectionUtils.isNotEmpty(restaurantErrors)) throw new RestaurantBusinessException(restaurantErrors);
    }
}
