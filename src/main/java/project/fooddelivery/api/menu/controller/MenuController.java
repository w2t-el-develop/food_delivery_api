package project.fooddelivery.api.menu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fooddelivery.api.menu.dto.MenuItemRequestDto;
import project.fooddelivery.api.menu.dto.MenuItemResponseDto;
import project.fooddelivery.api.menu.entity.MenuItem;
import project.fooddelivery.api.menu.mapper.MenuItemMapper;
import project.fooddelivery.api.menu.service.MenuItemService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("menus")
public class MenuController {

    private final MenuItemService menuItemService;
    private final MenuItemMapper menuItemMapper;

    @PostMapping("{menu-id}/menu-items")
    public ResponseEntity<MenuItemResponseDto> addMenuItem(@PathVariable("menu-id") UUID menuId,
                                                           @Valid @RequestBody MenuItemRequestDto menuItemRequestDto){
        MenuItem addedMenuItem =  menuItemService.addMenuItem(menuItemRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(menuItemMapper.toResponseDto(addedMenuItem));
    }
}
