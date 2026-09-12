package project.fooddelivery.api.order.exception;

public class IllegalOrderStateException extends RuntimeException{
    public IllegalOrderStateException(String message){
        super(message);
    }
}
