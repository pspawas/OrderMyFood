package com.omf.rest;

import com.omf.rest.model.MenuItemRequest;
import com.omf.rest.model.RestaurantRequest;
import com.omf.service.MenuItemService;
import com.omf.service.RestaurantService;
import com.omf.validation.MenuItemValidator;
import com.omf.validation.RestaurantValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class RestaurantRestApi {

    private final RestaurantService restaurantService;
    private final MenuItemService menuItemService;
    private final RestaurantValidator restaurantValidator;

    @Autowired
    public RestaurantRestApi(RestaurantService restaurantService, MenuItemService menuItemService, RestaurantValidator restaurantValidator) {
        this.restaurantService = restaurantService;
        this.menuItemService = menuItemService;
        this.restaurantValidator = restaurantValidator;
    }

    @RequestMapping(value = "/restaurants", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Object> createRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
        restaurantRequest.setTransactionId(UUID.randomUUID().toString());
        new RestaurantValidator().validateRestaurant(restaurantRequest);
        ResponseEntity responseEntity = new ResponseEntity<>(restaurantService.createRestaurant(restaurantRequest), HttpStatus.CREATED);
        return responseEntity;
    }

    @RequestMapping(value = "/restaurants/{restaurantId}/menuItems", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Object> createMenuItem(@RequestBody MenuItemRequest menuItemRequest, @PathVariable String restaurantId) {
        menuItemRequest.setTransactionId(UUID.randomUUID().toString());
        new MenuItemValidator().validateMenuItem(menuItemRequest);
        menuItemRequest.setRestaurantId(restaurantId);
        ResponseEntity responseEntity = new ResponseEntity<>(menuItemService.createMenuItem(menuItemRequest), HttpStatus.CREATED);
        return responseEntity;
    }

    @RequestMapping(value = "/menuItems/{restaurantId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getMenuItems(@PathVariable String restaurantId) {
        ResponseEntity responseEntity = new ResponseEntity<>(menuItemService.findAllMenusByRestaurantId(restaurantId), HttpStatus.FOUND);
        return responseEntity;
    }
}
