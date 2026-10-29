package project.fooddelivery.api.restaurant.service;

import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.exceptionhandling.ResourceNotFoundException;
import project.fooddelivery.api.restaurant.dto.AddRestaurantRequestDto;
import project.fooddelivery.api.restaurant.dto.UpdateRestaurantRequestDto;
import project.fooddelivery.api.restaurant.entity.Restaurant;
import project.fooddelivery.api.restaurant.exception.RestaurantExistenceException;
import project.fooddelivery.api.restaurant.mapper.RestaurantMapper;
import project.fooddelivery.api.restaurant.repositroy.RestaurantRepository;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public Restaurant addRestaurant(AddRestaurantRequestDto addRestaurantRequestDto) {
        Restaurant restaurant = restaurantMapper.ToEntity(addRestaurantRequestDto);
        isRestaurantExist(restaurant.getRestaurantCode());
        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurant(UpdateRestaurantRequestDto updateRestaurantRequestDto) {
        isRestaurantFound(updateRestaurantRequestDto.restaurantId());
        return restaurantRepository.save(restaurantMapper.ToEntity(updateRestaurantRequestDto));
    }

    public void deleteRestaurantByID(UUID restaurantId) {
        isRestaurantFound(restaurantId);
        restaurantRepository.deleteById(restaurantId);
    }


    public Restaurant getRestaurantById(UUID restaurantId){
        return restaurantRepository.findById(restaurantId).orElseThrow(()-> new ResourceNotFoundException("the restaurant is not found"));
    }

    private void isRestaurantExist(String restaurantCode) {
        boolean isRestaurantExistence =restaurantRepository.existsRestaurantByRestaurantCode(restaurantCode);
        if (isRestaurantExistence) throw new RestaurantExistenceException("the restaurant is already exist");
    }

    public void isRestaurantFound(UUID restaurantId) {
        boolean isRestaurantExistence =restaurantRepository.existsRestaurantByRestaurantId(restaurantId);
        if (!isRestaurantExistence) throw new ResourceNotFoundException("the restaurant is not found .");
    }
}
