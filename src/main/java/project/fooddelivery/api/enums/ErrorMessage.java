package project.fooddelivery.api.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    // Auth
    USER_TYPE_NOT_FOUND("User type not found"),
    USER_ALREADY_REGISTERED("User already registered"),
    INVALID_REFRESH_TOKEN("Invalid refresh token"),

    // Customer
    CUSTOMER_NOT_FOUND("Customer not found"),

    // Cart
    CART_NOT_FOUND("Cart not found"),
    CART_ITEM_NOT_FOUND("Cart item not found"),
    CART_ITEMS_NOT_FOUND("Cart items not found"),

    // Menu
    MENU_ITEM_NOT_FOUND("Menu item not found"),
    MENU_ITEM_NOT_AVAILABLE("Menu item is not available"),

    // Restaurant
    RESTAURANT_NOT_FOUND("Restaurant not found"),
    RESTAURANT_CLOSED("Restaurant is closed at the current time"),

    // Order
    ORDER_CANNOT_BE_PROCESSED("Order can not be processed at this moment"),
    ORDER_CANNOT_BE_DELIVERED("Order can not be delivered at this moment"),
    ORDER_ALREADY_PROCESSING("Order is already processing"),
    ORDER_ALREADY_CANCELLED("Order is already cancelled");

    private final String message;
}
