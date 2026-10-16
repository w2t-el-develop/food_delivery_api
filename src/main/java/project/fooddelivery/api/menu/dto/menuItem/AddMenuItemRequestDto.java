package project.fooddelivery.api.menu.dto.menuItem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record AddMenuItemRequestDto(
        @NotNull(message = "the is of mune is required")
        UUID menuId,
        @NotNull(message = "the name of menu item is required")
        String menuItemName,
        @NotNull(message = "the image url of menu item is required")
        String menuItemImageUrl,
        @NotNull(message = "the price of the menu item is required")
        Double menuItemPrice,
        @NotNull(message = "available status is required")
        boolean isAvailable,
        @Size(max = 10)
        @NotNull(message = "the code is required")
        String menuItemCode
) {
}
