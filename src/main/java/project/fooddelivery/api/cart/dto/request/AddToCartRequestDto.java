package project.fooddelivery.api.cart.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record AddToCartRequestDto(
        @NotNull(message = "Menu item was selected")
        UUID menItemId,
        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be at least one")
        Integer quantity) {
}
