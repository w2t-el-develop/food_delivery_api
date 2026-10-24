package project.fooddelivery.api.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.fooddelivery.api.restaurant.service.RestaurantService;

@RequiredArgsConstructor
@RestController
@RequestMapping("restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;


}
