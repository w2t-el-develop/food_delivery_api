package project.fooddelivery.api.core.cart.dto;

import java.util.List;
import java.util.UUID;

public record CartResponseDto(UUID cartId, List<CartItemResponseDto> cartItems) {
}
