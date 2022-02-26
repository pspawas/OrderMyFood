package com.omf.service;

import com.omf.model.Restaurant;
import com.omf.model.RestaurantResponse;
import com.omf.rest.model.RestaurantRequest;

import java.util.List;

public interface RestaurantService {

    RestaurantResponse createRestaurant(RestaurantRequest restaurant);
}
