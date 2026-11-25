package project.fooddelivery.api.menu.exception;

public class MenuItemNotAssignedToMenuException extends RuntimeException{

    public MenuItemNotAssignedToMenuException(String message){
        super(message);
    }
}
