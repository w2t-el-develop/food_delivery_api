package project.fooddelivery.api.feature.cart;

import java.util.UUID;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(UUID customerId) {
        super("Cart not found for customer: " + customerId);
    }
}
