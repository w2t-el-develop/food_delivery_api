package project.fooddelivery.api.cart.application.port;

import project.fooddelivery.api.cart.application.model.StoredCartEvent;

import java.util.List;

public interface CartProjection {
    void apply(List<StoredCartEvent> events);
}
