package project.fooddelivery.api.core.menu.exception;

public class MenuItemExistenceException extends RuntimeException {
    public MenuItemExistenceException(String message) {
        super(message);
    }
}
