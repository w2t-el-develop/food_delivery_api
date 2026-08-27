package project.fooddelivery.api.feature.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record AddToCartRequest(
        @NotNull(message = "Menu item ID is required")
        UUID menuItemId,

        @Min(value = 1, message = "Quantity must be at least 1")
        int quantity
) {}
