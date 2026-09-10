package project.fooddelivery.api.core.cart.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import project.fooddelivery.api.core.cart.dto.CartItemResponseDto;
import project.fooddelivery.api.core.cart.entity.CartItem;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartItemMapper {
    CartItemResponseDto toResponseDto(CartItem cartItem);
}
