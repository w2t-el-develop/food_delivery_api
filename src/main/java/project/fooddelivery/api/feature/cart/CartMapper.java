package project.fooddelivery.api.feature.cart;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(source = "cartId", target = "id")
    CartIdResponse toCartIdResponse(Cart cart);
    
    // Maps Cart entity properties and its nested collections to CartWithItemsResponse record
    @Mapping(source = "cartId", target = "id")
    @Mapping(source = "cartItems", target = "items")
    @Mapping(source = "customerId", target = "customerId")
    CartWithItemsResponse toCartWithItemsResponse(Cart cart);

    // Explicitly tells MapStruct how to handle individual cart items inside the list mapping
    @Mapping(source = "cartItemId", target = "id")
    @Mapping(source = "menuItemId", target = "menuItemId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "cartItemPrice", target = "price")
    CartItemResponse toCartItemResponse(CartItem cartItem);

}
