package project.fooddelivery.api.feature.cart;

import java.util.UUID;

public class CartEmptyException extends RuntimeException {

    
    public CartEmptyException(String message) {
        super(message);
    }
}
