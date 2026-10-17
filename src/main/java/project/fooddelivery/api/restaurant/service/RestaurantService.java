package project.fooddelivery.api.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.exceptionhandling.ResourceNotFoundException;
import project.fooddelivery.api.restaurant.entity.Restaurant;
import project.fooddelivery.api.restaurant.repository.RestaurantRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;


    public Optional<Restaurant> getRestaurantByRestaurantId(UUID restaurantId){
        return restaurantRepository.getRestaurantByRestaurantId(restaurantId);
    }

    public boolean isRestaurantOpen(UUID restaurantId){
        return getRestaurantByRestaurantId(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("restaurant not found"))
                .isOpen();
    }
}
