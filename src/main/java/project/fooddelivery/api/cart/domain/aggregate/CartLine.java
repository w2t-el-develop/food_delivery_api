package project.fooddelivery.api.cart.domain.aggregate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record CartLine(
        @NotNull(message = "Cart item was selected")
        UUID cartItemId,
        @NotNull(message = "Menu item was selected")
        UUID menuItemId,
        @NotNull()
        @NotBlank()
        String menuItemName,
        @NotNull(message = "menu Item price is required")
        @Positive(message = "menuItem must be at least one")
        Double menuItemPrice,
        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be at least one")
        Integer menuItemQuantity
) {

    public static CartLine buildCartLine(UUID cartItemId, UUID menuItemId, String menuItemName, Double unitPrice, Integer quantity) {
        return  new CartLine(cartItemId, menuItemId, menuItemName, unitPrice, quantity);
    }

    public CartLine updateQuantity(int newQuantity) {
        return buildCartLine(cartItemId, menuItemId, menuItemName, menuItemPrice, newQuantity);
    }

    public Double totalPrice() {
        return menuItemPrice * menuItemQuantity;
    }
}
