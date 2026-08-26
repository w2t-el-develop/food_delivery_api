package project.fooddelivery.api.feature.cart;

import java.math.BigDecimal;
import java.util.UUID;

public record CartItemResponse(
        UUID cartItemId,
        UUID menuItemId,
        int quantity,
        BigDecimal price
) {}