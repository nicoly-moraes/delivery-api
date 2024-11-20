package com.delivery.api.controllers;

import com.delivery.api.dtos.CreateRestaurantDto;
import com.delivery.api.services.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/restaurant")
@RestController
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody CreateRestaurantDto createRestaurantDto) {
        return ResponseEntity.ok(restaurantService.create(createRestaurantDto));
    }
}
