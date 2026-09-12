package project.fooddelivery.api.cart.domain.event;

import java.util.UUID;

public record CartItemAdded(
        UUID cartItemId,
        UUID menuItemId,
        String menuItemName,
        Double menuItemPrice,
        Integer quantity
) implements CartEvent {
    public static CartItemAdded buildCartItemAddedEvent(UUID cartItemId, UUID menuItemId,
                                                        String menuItemName, Double menuItemPrice, Integer quantity) {
        return new CartItemAdded(cartItemId, menuItemId, menuItemName, menuItemPrice, quantity);
    }
}