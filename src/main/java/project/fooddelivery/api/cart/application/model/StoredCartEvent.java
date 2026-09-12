package project.fooddelivery.api.cart.application.model;

import project.fooddelivery.api.cart.domain.event.CartEvent;

import java.time.Instant;
import java.util.UUID;

public record StoredCartEvent(
        UUID eventId,
        UUID aggregateId,
        long aggregateVersion,
        String eventType,
        int eventSchemaVersion,
        CartEvent payload,
        Instant occurredAt
) {
}
