package project.fooddelivery.api.core.cart.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

public record RemoveCartItemRequestDto(
        @NotEmpty(message = "At least one cart item must be provided")
        Set<@NotNull UUID> cartItemIds
) {
}
