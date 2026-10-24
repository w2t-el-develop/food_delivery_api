package project.fooddelivery.api.menu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.enums.ErrorMessage;
import project.fooddelivery.api.exceptionhandling.ResourceNotFoundException;
import project.fooddelivery.api.menu.entity.MenuItem;
import project.fooddelivery.api.menu.exception.MenuItemAvailabilityException;
import project.fooddelivery.api.menu.repository.MenuItemRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public void isMenuItemAvailable(UUID menuItemId) {
        if(menuItemRepository.isMenuItemAvailable(menuItemId)){
            throw new MenuItemAvailabilityException(ErrorMessage.MENU_ITEM_NOT_AVAILABLE.getMessage());
        }
    }

    public MenuItem getMenuItemIfAvailable(UUID menuItemId) {
        return menuItemRepository.getMenuItemByIdIfMenuItemIsAvailable(menuItemId)
                .orElseThrow(()-> new MenuItemAvailabilityException(ErrorMessage.MENU_ITEM_NOT_AVAILABLE.getMessage()));
    }

    public MenuItem getMenuItemByMenuItemId(UUID menuItemId) {
        return menuItemRepository.getMenuItemByMenuItemId(menuItemId)
                .orElseThrow(()-> new ResourceNotFoundException(ErrorMessage.MENU_ITEM_NOT_FOUND.getMessage()));
    }
}
