package project.fooddelivery.api.menu.dto.menuItem;

import java.util.UUID;

public record MenuItemResponseDto(
        UUID menuItemId,
        UUID menuId,
        String menuItemName,
        String menuItemImageUrl,
        Double menuItemPrice,
        boolean isAvailable,
        String menuItemCode
) {
}
