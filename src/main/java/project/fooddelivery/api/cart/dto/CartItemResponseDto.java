package project.fooddelivery.api.cart.dto;

import java.util.UUID;

public record CartItemResponseDto(
        UUID cartItemId,
        UUID menuItemId,
        Integer cartItemQuantity,
        Double cartItemPrice) {

}
