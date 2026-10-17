package project.fooddelivery.api.restaurant.exception;

public class RestaurantAvailabilityException extends RuntimeException{
    public RestaurantAvailabilityException(String message){
        super(message);
    }
}
