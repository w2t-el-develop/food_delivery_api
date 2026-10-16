package project.fooddelivery.api.menu.dto.menu;

import java.util.UUID;

public record MenuResponseDto(
        UUID menuId,
        UUID restaurantID,
        String menuCategory,
        String menuCode
) {
}
