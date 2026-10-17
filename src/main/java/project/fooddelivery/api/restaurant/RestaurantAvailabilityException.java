package project.fooddelivery.api.restaurant;

public class RestaurantAvailabilityException extends RuntimeException{
    public RestaurantAvailabilityException(String message){
        super(message);
    }
}
