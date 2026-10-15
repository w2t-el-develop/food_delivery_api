package project.fooddelivery.api.restaurant.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import project.fooddelivery.api.user.dto.AddUserRequestDto;

public record AddRestaurantRequestDto(
        @Size(max = 10)
        @NotNull(message = "the restaurant's code is required")
        String restaurantCode,
        @NotNull(message = "the restaurant's name is required")
        String restaurantName,
        String restaurantDescription,
        @NotNull AddUserRequestDto addUserRequestDto
) {
}
