package project.fooddelivery.api.cart.domain.event;

import java.util.UUID;

public record CartItemRemoved(
        UUID cartId,
        UUID cartItemId
) implements CartEvent {
    public static CartItemRemoved buildCarItemRemovedEvent(UUID cartId, UUID cartItemId) {
        return new CartItemRemoved(cartId, cartItemId);
    }
}
