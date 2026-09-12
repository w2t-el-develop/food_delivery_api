package project.fooddelivery.api.cart.domain.event;

import java.util.UUID;

public record CartCreated(
        UUID cartId,
        UUID customerId
) implements CartEvent {
    public static CartCreated buildCartCreatedEvent(UUID cartId, UUID customerId) {
        return new  CartCreated(cartId, customerId);
    }
}