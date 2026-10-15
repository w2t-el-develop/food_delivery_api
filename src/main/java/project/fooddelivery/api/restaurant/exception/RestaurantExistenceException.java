package project.fooddelivery.api.restaurant.exception;

public class RestaurantExistenceException extends RuntimeException {
    public RestaurantExistenceException(String message) {
        super(message);
    }
}
