package project.fooddelivery.api.Cart.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;
import project.fooddelivery.api.Cart.dto.CartItemRequestDto;
import project.fooddelivery.api.Cart.model.CartItem;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartItemMapper {
//    static CartItemRequestDto toCartItemRequestDto(CartItem cartItem) {
//        return null;
//    }

     CartItemRequestDto toCartItemRequestDto(CartItem cartItem);

    CartItem tocartItem(CartItemRequestDto cartItemRequestDto);


}
