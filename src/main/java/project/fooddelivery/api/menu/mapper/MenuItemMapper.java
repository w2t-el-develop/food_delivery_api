package project.fooddelivery.api.menu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import project.fooddelivery.api.menu.dto.MenuItemResponseDto;
import project.fooddelivery.api.menu.entity.MenuItem;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MenuItemMapper {

    MenuItemResponseDto toResponseDto(MenuItem menuItem);
}
