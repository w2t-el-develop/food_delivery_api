package project.fooddelivery.api.order.exception;

public class OrderAlreadyProcessingException extends RuntimeException {
    public OrderAlreadyProcessingException(String message) {
        super(message);
    }
}
