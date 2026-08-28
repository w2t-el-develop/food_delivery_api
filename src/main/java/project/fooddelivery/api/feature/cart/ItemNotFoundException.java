package project.fooddelivery.api.feature.cart;

import java.util.UUID;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String message) {
        super(message);
    }
}
