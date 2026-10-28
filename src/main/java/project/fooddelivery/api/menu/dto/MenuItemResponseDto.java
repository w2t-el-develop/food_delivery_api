package project.fooddelivery.api.menu.dto;



import java.math.BigDecimal;
import java.util.UUID;


public record MenuItemResponseDto(
        UUID menuItemId,
        String menuItemName,
        BigDecimal menuItemPrice,
        boolean isMenuItemAvailable
) {
}
