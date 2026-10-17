package project.fooddelivery.api.menu.exception;

public class MenuItemExistenceException extends RuntimeException {
    public MenuItemExistenceException(String message) {
        super(message);
    }
}
