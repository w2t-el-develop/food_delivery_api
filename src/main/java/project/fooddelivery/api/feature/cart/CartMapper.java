package project.fooddelivery.api.feature.cart;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface CartMapper {

    // Maps Cart entity properties and its nested collections to CartWithItemsResponse record
    @Mapping(source = "cartId", target = "id")
    @Mapping(source = "cartItems", target = "items")
    @Mapping(source = "customerId", target = "customerId")
    @Mapping(target = "totalPrice", expression = "java(calculateTotalPrice(cart))")
    CartWithItemsResponseDTO toCartWithItemsResponse(Cart cart);

    // Explicitly tells MapStruct how to handle individual cart items inside the list mapping
    @Mapping(source = "cartItemId", target = "id")
    @Mapping(source = "menuItemId", target = "menuItemId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "cartItemPrice", target = "price")
    CartItemResponseDTO toCartItemResponse(CartItem cartItem);

    default BigDecimal calculateTotalPrice(Cart cart) {
        return cart.getCartItems().stream()
                .map(item -> item.getCartItemPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
