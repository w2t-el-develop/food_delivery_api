package project.fooddelivery.api.menu.dto.menu;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record AddMenuRequestDto(
        @NotNull(message = "the restaurant id is required")
        UUID restaurantID,
        String menuCategory,
        @Size(max = 10)
        @NotNull(message = "the code of the menu is required")
        String menuCode
) {
}
