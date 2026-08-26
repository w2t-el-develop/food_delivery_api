package project.fooddelivery.api.Cart.dto;

import project.fooddelivery.api.Cart.model.Cart;

import java.util.UUID;

public record CartItemRequestDto(UUID cartItemId,UUID menuItemId,int cartItemQuantity, double cartItemPrice) {
}
