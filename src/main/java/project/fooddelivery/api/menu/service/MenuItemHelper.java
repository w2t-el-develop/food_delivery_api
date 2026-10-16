package project.fooddelivery.api.menu.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.menu.dto.menuItem.MenuItemResponseDto;
import project.fooddelivery.api.menu.entity.MenuItem;
import project.fooddelivery.api.menu.mapper.MenuItemMapper;
import project.fooddelivery.api.menu.repository.MenuItemRepository;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MenuItemHelper {
    private final MenuItemMapper menuItemMapper;
    private final MenuItemRepository menuItemRepository;

    public List<MenuItemResponseDto> findAllMenuItemsByMenuId(UUID menuId) {
        List<MenuItem> menuItemList = menuItemRepository.findAllMenuItemsByMenuId(menuId);
        return menuItemList.stream().map(menuItemMapper::toResponseDto).toList();
    }

    public void deleteAllByMenuId(UUID menuId){
        menuItemRepository.deleteAllByMenuID(menuId);
    }
}
