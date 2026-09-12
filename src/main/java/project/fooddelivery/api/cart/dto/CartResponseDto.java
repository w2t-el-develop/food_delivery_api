package project.fooddelivery.api.cart.dto;

import java.util.List;
import java.util.UUID;

public record CartResponseDto(UUID cartId, List<CartItemResponseDto> cartItems) {
    public static CartResponseDto buildCartResponseDto(UUID cartId, List<CartItemResponseDto> cartItems) {
        return new CartResponseDto(cartId, cartItems);
    }
}
