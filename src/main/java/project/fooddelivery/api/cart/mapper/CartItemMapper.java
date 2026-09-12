package project.fooddelivery.api.cart.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import project.fooddelivery.api.cart.dto.CartItemResponseDto;
import project.fooddelivery.api.cart.entity.CartItem;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartItemMapper {
    CartItemResponseDto toResponseDto(CartItem cartItem);
}
