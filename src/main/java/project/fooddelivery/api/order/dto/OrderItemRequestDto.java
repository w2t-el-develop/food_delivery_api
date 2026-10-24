package project.fooddelivery.api.order.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderItemRequestDto(
        @NotNull(message = "Cart item ID is required")
        UUID cartItemId,

        @NotNull(message = "Menu item Id is required")
        UUID menuItemId,

        @NotNull(message = "Order item quantity is required")
        Integer orderItemQuantity
) {
}
