package project.fooddelivery.api.cart.application.model;

import java.util.UUID;

public record CartCommandResult(
        UUID cartId,
        UUID affectedCartItemId,
        Long version
) {
}
