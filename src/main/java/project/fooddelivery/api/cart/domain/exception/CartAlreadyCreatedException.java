package project.fooddelivery.api.cart.domain.exception;

public class CartAlreadyCreatedException extends RuntimeException {
    public CartAlreadyCreatedException(String message) {
        super(message);
    }
}
