package project.fooddelivery.api.restaurant.dto;

import project.fooddelivery.api.user.dto.UserResponseDto;

import java.util.UUID;

public record RestaurantResponseDto(
        UUID restaurantId,
        String restaurantCode,
        String restaurantName,
        String restaurantDescription,
        UserResponseDto userResponseDto
) {
}
