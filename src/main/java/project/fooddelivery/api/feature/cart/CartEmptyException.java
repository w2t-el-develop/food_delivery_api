package project.fooddelivery.api.feature.cart;

import java.util.UUID;

public class CartEmptyException extends RuntimeException {

    
    public CartEmptyException(UUID cartId, UUID customerId) {
        super("Cart with ID " + cartId + " for customer " + customerId + " is empty.");
    }
}
