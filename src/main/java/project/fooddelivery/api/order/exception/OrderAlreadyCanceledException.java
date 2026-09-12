package project.fooddelivery.api.order.exception;

public class OrderAlreadyCanceledException extends RuntimeException {
    public OrderAlreadyCanceledException() {
    }
    public OrderAlreadyCanceledException(String message) {
        super(message);
    }
}
