package project.fooddelivery.api.menu.dto;


import java.math.BigDecimal;

public record MenuItemUpdateRequestDto(
        String menuItemName,
        BigDecimal menuItemPrice,
        Boolean isMenuItemAvailable
){
}
