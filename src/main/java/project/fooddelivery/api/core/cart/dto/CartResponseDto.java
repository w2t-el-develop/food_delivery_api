package project.fooddelivery.api.core.cart.dto;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record CartResponseDto(UUID cartId, List<CartItemResponseDto> cartItems) {
}
