package project.fooddelivery.api.feature.cart;

import java.math.BigDecimal;
import java.util.List;

public record CartWithItemsResponseDTO(
    String id,
    String customerId,
    List <CartItemResponseDTO> items
    ,BigDecimal totalPrice
) {
}