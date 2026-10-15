package project.fooddelivery.api.restaurant.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import project.fooddelivery.api.restaurant.dto.AddRestaurantRequestDto;
import project.fooddelivery.api.restaurant.dto.RestaurantResponseDto;
import project.fooddelivery.api.restaurant.dto.UpdateRestaurantRequestDto;
import project.fooddelivery.api.restaurant.entity.Restaurant;
import project.fooddelivery.api.user.dto.UpdateUserRequestDto;
import project.fooddelivery.api.user.mapper.UserMapper;

@Component("RestaurantMapper")
@AllArgsConstructor
public class RestaurantMapper {

    private final UserMapper userMapper;

    public Restaurant ToEntity(AddRestaurantRequestDto restaurantRequestDto){
        return Restaurant.builder()
                .restaurantName(restaurantRequestDto.restaurantName())
                .restaurantCode(restaurantRequestDto.restaurantCode())
                .restaurantDescription(restaurantRequestDto.restaurantDescription())
                .user(userMapper.requestDtoToUser(restaurantRequestDto.addUserRequestDto()))
                .build();
    }

    public Restaurant ToEntity(UpdateRestaurantRequestDto restaurantRequestDto){
        return Restaurant.builder()
                .restaurantId(restaurantRequestDto.restaurantId())
                .restaurantName(restaurantRequestDto.restaurantName())
                .restaurantCode(restaurantRequestDto.restaurantCode())
                .restaurantDescription(restaurantRequestDto.restaurantDescription())
                .user(userMapper.requestDtoToUser(restaurantRequestDto.userRequestDto()))
                .build();
    }

    public RestaurantResponseDto ToRequestDto(Restaurant restaurant){
        return new RestaurantResponseDto(
                restaurant.getRestaurantId(),
                restaurant.getRestaurantCode(),
                restaurant.getRestaurantName(),
                restaurant.getRestaurantDescription(),
                userMapper.UserToResponseDto(restaurant.getUser())
        );
    }
}
