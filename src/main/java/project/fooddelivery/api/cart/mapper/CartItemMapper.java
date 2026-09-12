package project.fooddelivery.api.cart.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import project.fooddelivery.api.cart.dto.response.CartItemResponseDto;
import project.fooddelivery.api.cart.infrastructure.projection.entity.CartItem;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartItemMapper {
    CartItemResponseDto toResponseDto(CartItem cartItem);
}
