package project.fooddelivery.api.menu.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.exceptionhandling.ResourceNotFoundException;
import project.fooddelivery.api.menu.entity.MenuItem;
import project.fooddelivery.api.menu.exception.MenuItemExistenceException;
import project.fooddelivery.api.menu.repository.MenuItemRepository;

import java.util.UUID;

@Service
@AllArgsConstructor
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final MenuService menuService;

    public MenuItem addMenuItem(MenuItem menuItem){
        menuService.isMenuFound(menuItem.getMenuId());
        isMenuItemExistence(menuItem.getMenuItemCode());
        return menuItemRepository.save(menuItem);
    }

    @Transactional
    public MenuItem updateMenuItem(MenuItem newMenuItem){
        isMenuItemFound(newMenuItem.getMenuItemId());
        Integer numberOfUpdatedMenuItem = menuItemRepository.updateMenuItem(newMenuItem.getMenuItemId(), newMenuItem.getMenuItemName(), newMenuItem.getMenuItemPrice(), newMenuItem.isAvailable(), newMenuItem.getMenuItemImageUrl(), newMenuItem.getMenuItemCode());
        if (numberOfUpdatedMenuItem == 0) throw new ResourceNotFoundException("the menu item is not found");
        return newMenuItem;
    }

    @Transactional
    public void deletedMenuItem(UUID menuId){

        Integer numberOfDeleted = menuItemRepository.deleteMenuItem(menuId);
        if (numberOfDeleted == 0) throw new ResourceNotFoundException("the menu item is not found");
    }

    private void isMenuItemExistence(String menuItemCode){
        Boolean result = menuItemRepository.existsMenuItemByMenuItemCode(menuItemCode);
        if (result) throw new MenuItemExistenceException("the menu item is already exist");
    }

    private void isMenuItemFound(UUID menuItemID){
        Boolean result = menuItemRepository.existsMenuItemByMenuItemId(menuItemID);
        if (!result) throw new ResourceNotFoundException("the menu item is not found");
    }
}
