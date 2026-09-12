package project.fooddelivery.api.cart.dto.response;

import java.util.UUID;

public record CartItemResponseDto(
        UUID cartItemId,
        UUID menuItemId,
        Integer cartItemQuantity,
        Double cartItemPrice) {

}
