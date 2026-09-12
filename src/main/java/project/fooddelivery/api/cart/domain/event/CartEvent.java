package project.fooddelivery.api.cart.domain.event;

public sealed interface CartEvent
        permits CartCreated,
        CartCleared,
        CartItemAdded,
        CartItemRemoved,
        CartItemQuantityUpdated {
}
