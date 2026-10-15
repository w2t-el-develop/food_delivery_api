package project.fooddelivery.api.restaurant.service;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.exceptionhandling.ResourceNotFoundException;
import project.fooddelivery.api.restaurant.entity.Restaurant;
import project.fooddelivery.api.restaurant.exception.RestaurantExistenceException;
import project.fooddelivery.api.restaurant.repositroy.RestaurantRepository;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public Restaurant addRestaurant(Restaurant restaurant) {
        isRestaurantExistence(restaurant.getRestaurantCode());
        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurant(Restaurant restaurant) {
        isRestaurantFound(restaurant.getRestaurantId());
        return restaurantRepository.save(restaurant);
    }

    public void deleteRestaurantByID(UUID restaurantId) {
        isRestaurantFound(restaurantId);
        restaurantRepository.deleteById(restaurantId);
    }


    public Restaurant getRestaurantById(UUID restaurantId){
        return restaurantRepository.findById(restaurantId).orElseThrow(()-> new ResourceNotFoundException("the restaurant is not found"));
    }

    private void isRestaurantExistence(String restaurantCode) {
        boolean isRestaurantExistence =restaurantRepository.existsRestaurantByRestaurantCode(restaurantCode);
        if (isRestaurantExistence) throw new RestaurantExistenceException("the restaurant is already exist");
    }

    public void isRestaurantFound(UUID restaurantId) {
        boolean isRestaurantExistence =restaurantRepository.existsRestaurantByRestaurantId(restaurantId);
        if (!isRestaurantExistence) throw new ResourceNotFoundException("the restaurant is not found .");
    }
}
