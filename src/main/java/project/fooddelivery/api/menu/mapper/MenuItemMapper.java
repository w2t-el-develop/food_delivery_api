package project.fooddelivery.api.menu.mapper;

import org.springframework.stereotype.Component;
import project.fooddelivery.api.menu.dto.menuItem.AddMenuItemRequestDto;
import project.fooddelivery.api.menu.dto.menuItem.MenuItemResponseDto;
import project.fooddelivery.api.menu.dto.menuItem.UpdateMenuItemRequestDto;
import project.fooddelivery.api.menu.entity.MenuItem;

@Component("MenuItemMapper")
public class MenuItemMapper {

    public MenuItem toEntity(AddMenuItemRequestDto addMenuItemRequestDto){
        return MenuItem.builder()
                .menuId(addMenuItemRequestDto.menuId())
                .menuItemName(addMenuItemRequestDto.menuItemName())
                .menuItemImageUrl(addMenuItemRequestDto.menuItemImageUrl())
                .menuItemPrice(addMenuItemRequestDto.menuItemPrice())
                .menuItemCode(addMenuItemRequestDto.menuItemCode())
                .isAvailable(addMenuItemRequestDto.isAvailable())
                .build();
    }

    public MenuItem toEntity(UpdateMenuItemRequestDto updateMenuItemResponseDto){
        return MenuItem.builder()
                .menuItemId(updateMenuItemResponseDto.menuItemId())
                .menuId(updateMenuItemResponseDto.menuId())
                .menuItemName(updateMenuItemResponseDto.menuItemName())
                .menuItemImageUrl(updateMenuItemResponseDto.menuItemImageUrl())
                .menuItemPrice(updateMenuItemResponseDto.menuItemPrice())
                .menuItemCode(updateMenuItemResponseDto.menuItemCode())
                .isAvailable(updateMenuItemResponseDto.isAvailable())
                .build();
    }

    public MenuItemResponseDto toResponseDto(MenuItem menuItem){
        return new MenuItemResponseDto(
                menuItem.getMenuItemId(),
                menuItem.getMenuId(),
                menuItem.getMenuItemName(),
                menuItem.getMenuItemImageUrl(),
                menuItem.getMenuItemPrice(),
                menuItem.isAvailable(),
                menuItem.getMenuItemCode()
        );
    }
}
