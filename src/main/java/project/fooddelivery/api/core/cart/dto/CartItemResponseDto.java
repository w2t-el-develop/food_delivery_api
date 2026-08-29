package project.fooddelivery.api.core.cart.dto;

import java.util.UUID;

public record CartItemResponseDto(
        UUID cartItemId,
        UUID menuItemId,
        Integer cartItemQuantity,
        Double cartItemPrice) {
}
