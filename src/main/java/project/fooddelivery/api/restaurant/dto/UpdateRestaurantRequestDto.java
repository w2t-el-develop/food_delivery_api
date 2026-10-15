package project.fooddelivery.api.restaurant.dto;

import jakarta.validation.constraints.NotNull;
import project.fooddelivery.api.user.dto.UpdateUserRequestDto;

import java.util.UUID;

public record UpdateRestaurantRequestDto(
        @NotNull(message = "the id of restaurant is required")
        UUID restaurantId,
        @NotNull(message = "the restaurant's code is required")
        String restaurantCode,
        @NotNull(message = "the restaurant's name is required")
        String restaurantName,
        String restaurantDescription,
        @NotNull UpdateUserRequestDto userRequestDto
) {
}
