package project.fooddelivery.api.menu.dto.menu;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateMenuRequestDto(
        @NotNull(message = "the menu id is required")
        UUID menuId,
        @NotNull(message = "the id of restaurant is required")
        UUID restaurantID,
        String menuCategory,
        @Size(max = 10)
        @NotNull(message = "the menu code is required")
        String menuCode
) {
}
