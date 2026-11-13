package project.fooddelivery.api.menu.service;



import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.fooddelivery.api.menu.entity.Menu;
import project.fooddelivery.api.menu.repository.MenuItemRepository;
import project.fooddelivery.api.menu.repository.MenuRepository;

import java.util.UUID;



@ExtendWith(MockitoExtension.class)
public class MenuItemServiceTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuItemService menuItemService;

    @Test
    public void addMenuItem_WhenMenuExists_ShouldSaveAndReturnMenuItem(){
        UUID menuId = UUID.randomUUID();

        Menu menu = Menu.createMenu("Test menu", "Test Menu Description");


    }
}








