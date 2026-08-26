package project.fooddelivery.api.feature.cart;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public record AddToCartRequest(
        @NotNull(message = "Customer ID is required")
        UUID customerId,

        @Valid
        @NotNull(message = "Cart item details are required")
        CartItemRequest cartItem
) {
    public record CartItemRequest(
            @NotNull(message = "Menu item ID is required")
            UUID menuItemId,

            @Min(value = 1, message = "Quantity must be at least 1")
            int quantity,

            @NotNull(message = "Price is required")
            @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
            BigDecimal price
    ) {}
}