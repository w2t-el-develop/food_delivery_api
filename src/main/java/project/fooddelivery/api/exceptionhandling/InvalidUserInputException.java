package project.fooddelivery.api.exceptionhandling;

public class InvalidUserInputException extends RuntimeException {
    public InvalidUserInputException(String message){
        super(message);
    }
    
}
