package project.fooddelivery.api.menu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record MenuItemRequestDto(
        @NotNull(message = "Menu item name is required")
        @NotBlank(message = "Menu item name is required")
        String menuItemName,

        @NotNull(message = "menu item pride is required")
        @Positive(message = "menu item price must be greater than zero")
        Double menuItemPrice,

        boolean isMenuItemAvailable
) {

}
