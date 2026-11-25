package project.fooddelivery.api.menu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.enums.ErrorMessage;
import project.fooddelivery.api.exception.ResourceNotFoundException;
import project.fooddelivery.api.menu.entity.Menu;
import project.fooddelivery.api.menu.repository.MenuRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public Menu getMenuById(UUID menuId) {
        return menuRepository.getMenuByMenuId(menuId)
                .orElseThrow(() -> new ResourceNotFoundException((ErrorMessage.MENU_NOT_FOUND).getMessage()));
    }

    public void isMenuExistsById(UUID menuId) {
        if (!menuRepository.isMenuExistsById(menuId)) {
            throw new ResourceNotFoundException((ErrorMessage.MENU_NOT_FOUND).getMessage());
        }
    }

}
