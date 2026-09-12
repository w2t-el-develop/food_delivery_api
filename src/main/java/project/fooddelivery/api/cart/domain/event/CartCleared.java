package project.fooddelivery.api.cart.domain.event;



import java.util.UUID;

public record CartCleared(UUID cartId) implements CartEvent {
    public static CartCleared buildCartClearedEvent(UUID cartId) {
        return new CartCleared(cartId);
    }
}
