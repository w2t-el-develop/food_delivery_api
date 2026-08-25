package project.fooddelivery.api.feature.cart;

import java.util.UUID;

public record AddToCartRequest(
        String customerId,
        CartItemRequest cartItem
) {
    public record CartItemRequest(
            UUID menu_item_id,
            int cart_item_quantity
    ) {}
}