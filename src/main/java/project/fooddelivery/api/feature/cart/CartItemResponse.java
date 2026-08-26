package project.fooddelivery.api.feature.cart;

import java.math.BigDecimal;
import java.util.UUID;

public record CartItemResponse(
    UUID id,
    UUID menuItemId,
    Integer quantity,
    BigDecimal price
) {
    
}
