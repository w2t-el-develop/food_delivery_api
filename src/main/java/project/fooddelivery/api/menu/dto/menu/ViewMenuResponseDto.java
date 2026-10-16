package project.fooddelivery.api.menu.dto.menu;

import project.fooddelivery.api.menu.dto.menuItem.MenuItemResponseDto;

import java.util.List;
import java.util.UUID;

public record ViewMenuResponseDto(
        UUID menuId,
        String menuCategory,
        String menuCode,
        List<MenuItemResponseDto> menuItemsResponseDto
) {
}
