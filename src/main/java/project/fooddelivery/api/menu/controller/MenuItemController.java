package project.fooddelivery.api.menu.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.fooddelivery.api.menu.dto.menuItem.AddMenuItemRequestDto;
import project.fooddelivery.api.menu.dto.menuItem.MenuItemResponseDto;
import project.fooddelivery.api.menu.dto.menuItem.UpdateMenuItemRequestDto;
import project.fooddelivery.api.menu.entity.MenuItem;
import project.fooddelivery.api.menu.mapper.MenuItemMapper;
import project.fooddelivery.api.menu.service.MenuItemService;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/menu-items")
public class MenuItemController {
    private final MenuItemService menuItemService;
    private final MenuItemMapper menuItemMapper;


    @PostMapping
    public ResponseEntity<MenuItemResponseDto> addMenuItem(@Valid @RequestBody AddMenuItemRequestDto addMenuItemRequestDto){
        MenuItem menuItem = menuItemService.addMenuItem(menuItemMapper.toEntity(addMenuItemRequestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(menuItemMapper.toResponseDto(menuItem));
    }

    @PutMapping
    public ResponseEntity<MenuItemResponseDto> updateMenuItem(@Valid @RequestBody UpdateMenuItemRequestDto updateMenuItemRequestDto){
        MenuItem menuItem = menuItemService.updateMenuItem(menuItemMapper.toEntity(updateMenuItemRequestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(menuItemMapper.toResponseDto(menuItem));
    }

    @DeleteMapping("/{menu-item-id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable("menu-item-id") UUID menuItemId){
        menuItemService.deletedMenuItem(menuItemId);
        return ResponseEntity.noContent().build();
    }
}
