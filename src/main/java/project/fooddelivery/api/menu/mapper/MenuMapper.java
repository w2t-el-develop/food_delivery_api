package project.fooddelivery.api.menu.mapper;

import org.springframework.stereotype.Component;
import project.fooddelivery.api.menu.dto.menu.AddMenuRequestDto;
import project.fooddelivery.api.menu.dto.menu.MenuResponseDto;
import project.fooddelivery.api.menu.dto.menu.UpdateMenuRequestDto;
import project.fooddelivery.api.menu.entity.Menu;

@Component("MenuMapper")
public class MenuMapper {

    public Menu toEntity(AddMenuRequestDto addMenuRequestDto){
        return Menu.builder()
                .restaurantId(addMenuRequestDto.restaurantID())
                .menuCategory(addMenuRequestDto.menuCategory())
                .menuCode(addMenuRequestDto.menuCode())
                .build();
    }

    public Menu toEntity(UpdateMenuRequestDto updateMenuRequestDto){
        return Menu.builder()
                .menuId(updateMenuRequestDto.menuId())
                .restaurantId(updateMenuRequestDto.restaurantID())
                .menuCategory(updateMenuRequestDto.menuCategory())
                .menuCode(updateMenuRequestDto.menuCode())
                .build();
    }

    public MenuResponseDto toResponseDto(Menu menu){
        return new MenuResponseDto(
                menu.getMenuId(),
                menu.getRestaurantId(),
                menu.getMenuCategory(),
                menu.getMenuCode()
        );
    }
}
