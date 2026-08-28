package project.fooddelivery.api.feature.cart;

import java.math.BigDecimal;

public record CartTotalPriceResponseDTO(
    BigDecimal totalPrice
) {
}
