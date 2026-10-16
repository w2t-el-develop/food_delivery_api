package project.fooddelivery.api.menu.exception;

public class MenuExistenceException extends RuntimeException {
    public MenuExistenceException(String message) {
        super(message);
    }
}
