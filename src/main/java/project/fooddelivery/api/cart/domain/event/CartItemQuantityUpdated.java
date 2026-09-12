package project.fooddelivery.api.cart.domain.event;

import java.util.UUID;

public record CartItemQuantityUpdated(
        UUID cartItemId,
        int newQuantity
)implements CartEvent {
    public static CartItemQuantityUpdated buildCartItemQuantityChangedEvent(UUID cartItemId, Integer newQuantity) {
        return new CartItemQuantityUpdated(cartItemId, newQuantity);
    }
}
