package project.fooddelivery.api.menu.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.fooddelivery.api.menu.dto.menu.AddMenuRequestDto;
import project.fooddelivery.api.menu.dto.menu.MenuResponseDto;
import project.fooddelivery.api.menu.dto.menu.UpdateMenuRequestDto;
import project.fooddelivery.api.menu.dto.menu.ViewMenuResponseDto;
import project.fooddelivery.api.menu.entity.Menu;
import project.fooddelivery.api.menu.mapper.MenuMapper;
import project.fooddelivery.api.menu.service.MenuService;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/menus")
public class MenuController {
    private final MenuService menuService;
    private final MenuMapper menuMapper;

    @PostMapping
    public ResponseEntity<MenuResponseDto> addMenu(@Valid @RequestBody AddMenuRequestDto addMenuRequestDto){
        Menu menu = menuService.addMenu(menuMapper.toEntity(addMenuRequestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(menuMapper.toResponseDto(menu));
    }

    @PatchMapping
    public ResponseEntity<MenuResponseDto> updateMenu(@Valid @RequestBody UpdateMenuRequestDto updateMenuRequestDto){
        Menu menu = menuService.update(menuMapper.toEntity(updateMenuRequestDto));
        return ResponseEntity.ok(menuMapper.toResponseDto(menu));
    }

    @DeleteMapping("/{menu-id}")
    public ResponseEntity<Void> deleteMenuByMenuId(@PathVariable("menu-id") UUID menuId){
        menuService.deleteMenuByMenuId(menuId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{menu-id}")
    public ResponseEntity<ViewMenuResponseDto> viewMenuResponseDtoResponseEntity(@PathVariable("menu-id") UUID menuId){
        return ResponseEntity.ok(menuService.viewMenuByMenuId(menuId));
    }
}
