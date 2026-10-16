package project.fooddelivery.api.order.dto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import project.fooddelivery.api.cart.dto.CartItemResponseDto;

import java.util.List;
import java.util.UUID;
public record OrderRequestDto(
        @NotNull(message = "Cart ID is required")
        UUID cartId,

        @NotNull(message = "customer ID is required")
        UUID customerId,

        @NotNull(message = "address ID is required")
        UUID addressId,

        @NotEmpty(message = "Order must have at least on item")
        List<CartItemResponseDto> cartItemResponseDtoList
) {
}
