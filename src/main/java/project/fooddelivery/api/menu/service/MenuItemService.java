package project.fooddelivery.api.menu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.exceptionhandling.ResourceNotFoundException;
import project.fooddelivery.api.menu.entity.MenuItem;
import project.fooddelivery.api.menu.exception.MenuItemAvailabilityException;
import project.fooddelivery.api.menu.repository.MenuItemRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public boolean isMenuItemAvailable(UUID menuItemId) {
        if(menuItemRepository.isMenuItemAvailable(menuItemId)){
            throw new MenuItemAvailabilityException("Menu item is not available");
        }
        return true;
    }

    public MenuItem getMenuItemIfAvailable(UUID menuItemId) {
        return menuItemRepository.getMenuItemIfAvailable(menuItemId)
                .orElseThrow(()-> new MenuItemAvailabilityException("Menu item is not available"));
    }

    public MenuItem getMenuItemByMenuItemId(UUID menuItemId) {
        return menuItemRepository.getMenuItemByMenuItemId(menuItemId)
                .orElseThrow(()-> new ResourceNotFoundException("MenuItem not found"));
    }
}
