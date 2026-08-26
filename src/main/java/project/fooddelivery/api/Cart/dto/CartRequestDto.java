package project.fooddelivery.api.Cart.dto;

import java.util.UUID;

public record CartRequestDto(UUID cartId, UUID customerId) {
}
