package project.fooddelivery.api.feature.cart;

import java.util.List;

public record CartWithItemsResponseDTO(
    String id,
    String customerId,
    List <CartItemResponseDTO> items
) {
}