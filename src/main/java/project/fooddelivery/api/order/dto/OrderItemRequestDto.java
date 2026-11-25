package project.fooddelivery.api.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record OrderItemRequestDto(
        @NotNull(message = "Cart item ID is required")
        UUID cartItemId,

        @NotNull(message = "Menu item Id is required")
        UUID menuItemId,

        @NotNull(message = "Order item quantity is required")
        @Positive(message = "Order item quantity must be greater than zero")
        Integer orderItemQuantity
) {
}
