package project.fooddelivery.api.feature.cart;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartItemResponse toCartItemResponse(CartItem cartItem);
}
