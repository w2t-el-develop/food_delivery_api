package project.fooddelivery.api.menu.service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.exceptionhandling.ResourceNotFoundException;
import project.fooddelivery.api.menu.dto.menu.ViewMenuResponseDto;
import project.fooddelivery.api.menu.dto.menuItem.MenuItemResponseDto;
import project.fooddelivery.api.menu.entity.Menu;
import project.fooddelivery.api.menu.exception.MenuExistenceException;
import project.fooddelivery.api.menu.repository.MenuRepository;
import project.fooddelivery.api.restaurant.service.RestaurantService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final RestaurantService restaurantService;
    private final MenuItemHelper menuItemHelper;


    public Menu addMenu(Menu menu){
        restaurantService.isRestaurantFound(menu.getRestaurantId()); // check restaurant is exist ?
        isMenuExistence(menu.getMenuCode()); // check, is exist menu the same new menu ?
        return menuRepository.save(menu);
    }

    @Transactional
    public Menu update(Menu newMenu){
        isMenuFound(newMenu.getMenuId());
        return  menuRepository.save(newMenu);


//        Integer numberOfUpdated = menuRepository.updateMenuById(newMenu.getMenuId()  ,newMenu.getMenuCategory(), newMenu.getMenuCode());
//        if (numberOfUpdated == 0) throw new ResourceNotFoundException("the  menu is not found");
//        return newMenu;
    }

    @Transactional
    public void deleteMenuByMenuId(UUID menuId){
        isMenuFound(menuId);
        menuRepository.deleteById(menuId);
        menuItemHelper.deleteAllByMenuId(menuId);
    }

    public ViewMenuResponseDto viewMenuByMenuId(UUID menuId){
        Menu menu = getMenuByMenuId(menuId);
        List<MenuItemResponseDto> getMenuItemsByMenuId = menuItemHelper.findAllMenuItemsByMenuId(menuId);
        return new ViewMenuResponseDto(menu.getMenuId(), menu.getMenuCategory(), menu.getMenuCode(), getMenuItemsByMenuId);
    }

    public void isMenuFound(UUID menuId) {
        boolean exists = menuRepository.existsMenuByMenuId(menuId);
        if (!exists) throw new ResourceNotFoundException("the menu is not found");
    }

    private void isMenuExistence(@NotNull(message = "the menu code is required") String menuCode) {
        boolean exists = menuRepository.existsMenuByMenuCode(menuCode);
        if (exists) throw new MenuExistenceException("the menu is already exist");
    }

    private Menu getMenuByMenuId(UUID menuId) {
        return menuRepository.findById(menuId).orElseThrow(()-> new ResourceNotFoundException("menu item is not found"));
    }
}