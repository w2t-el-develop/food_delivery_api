package project.fooddelivery.api.feature.cart;

import java.util.List;

public record CartWithItemsResponse(
    String id,
    String customerId,
    List <CartItemResponse> items
) {
}