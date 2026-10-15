package project.fooddelivery.api.restaurant.controller;

import jakarta.persistence.PostRemove;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.fooddelivery.api.restaurant.dto.AddRestaurantRequestDto;
import project.fooddelivery.api.restaurant.dto.RestaurantResponseDto;
import project.fooddelivery.api.restaurant.dto.UpdateRestaurantRequestDto;
import project.fooddelivery.api.restaurant.entity.Restaurant;
import project.fooddelivery.api.restaurant.mapper.RestaurantMapper;
import project.fooddelivery.api.restaurant.service.RestaurantService;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;

    @PostMapping
    public ResponseEntity<RestaurantResponseDto> addRestaurant(@Valid @RequestBody AddRestaurantRequestDto addRestaurantRequestDto){
        Restaurant restaurant = restaurantService.addRestaurant(restaurantMapper.ToEntity(addRestaurantRequestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantMapper.ToRequestDto(restaurant));
    }

    @PutMapping
    public ResponseEntity<RestaurantResponseDto> updateRestaurant(
            @Valid @RequestBody UpdateRestaurantRequestDto updateRestaurantRequestDto
    ){
        Restaurant restaurant = restaurantService.updateRestaurant(restaurantMapper.ToEntity(updateRestaurantRequestDto));
        return ResponseEntity.ok(restaurantMapper.ToRequestDto(restaurant));
    }

    @DeleteMapping("/{restaurant-id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable("restaurant-id") UUID restaurantId){
        restaurantService.deleteRestaurantByID(restaurantId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{restaurant-id}")
    public ResponseEntity<RestaurantResponseDto> viewRestaurant(@PathVariable("restaurant-id") UUID restaurantId){
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        return ResponseEntity.ok(restaurantMapper.ToRequestDto(restaurant));
    }
}
