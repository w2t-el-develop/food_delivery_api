package project.fooddelivery.api.cart.application.port;

import project.fooddelivery.api.cart.domain.event.CartEvent;
import project.fooddelivery.api.cart.application.model.StoredCartEvent;

import java.util.List;
import java.util.UUID;

public interface CartEventStore {
    List<StoredCartEvent> load(UUID aggregateId);

    List<StoredCartEvent> append(
            UUID aggregateId,
            Long expectedVersion,
            List<CartEvent> cartEvents
    );
}
