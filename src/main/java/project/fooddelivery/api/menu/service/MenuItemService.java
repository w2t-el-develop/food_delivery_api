package project.fooddelivery.api.menu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.enums.ErrorMessage;
import project.fooddelivery.api.exception.ResourceNotFoundException;
import project.fooddelivery.api.menu.dto.MenuItemRequestDto;
import project.fooddelivery.api.menu.entity.Menu;
import project.fooddelivery.api.menu.entity.MenuItem;
import project.fooddelivery.api.menu.exception.MenuItemAvailabilityException;
import project.fooddelivery.api.menu.repository.MenuItemRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final MenuService menuService;

    public MenuItem addMenuItem(UUID menuId, MenuItemRequestDto menuItemRequestDto) {
        Menu menu = menuService.getMenuById(menuId);
        MenuItem newMenuItem = MenuItem.buildMenuItem(
                menuItemRequestDto.menuItemName(),
                BigDecimal.valueOf(menuItemRequestDto.menuItemPrice()),
                menuItemRequestDto.isMenuItemAvailable());
        menu.addItem(newMenuItem);
        return menuItemRepository.save(newMenuItem);
    }

    public void isMenuItemAvailable(UUID menuItemId) {
        if (menuItemRepository.isMenuItemAvailable(menuItemId)) {
            throw new MenuItemAvailabilityException(ErrorMessage.MENU_ITEM_NOT_AVAILABLE.getMessage());
        }
    }

    public MenuItem getMenuItemByIdIfMenuItemIsAvailable(UUID menuItemId) {
        return menuItemRepository.getMenuItemByIdIfMenuItemIsAvailable(menuItemId)
                .orElseThrow(() -> new MenuItemAvailabilityException(ErrorMessage.MENU_ITEM_NOT_AVAILABLE.getMessage()));
    }

    public MenuItem getMenuItemByMenuItemId(UUID menuItemId) {
        return menuItemRepository.getMenuItemByMenuItemId(menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.MENU_ITEM_NOT_FOUND.getMessage()));
    }
}
