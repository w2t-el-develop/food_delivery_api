package project.fooddelivery.api.order.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import project.fooddelivery.api.cart.dto.CartItemResponseDto;

import java.util.List;
import java.util.UUID;

public record OrderRequestDto(
        @NotNull(message = "Cart ID is required")
        UUID cartId,

        @NotNull(message = "Customer ID is required")
        UUID customerId,

        @NotNull(message = "Address ID is required")
        UUID addressId,

        @NotNull(message = "Restaurant ID is required")
        UUID restaurantId,

        @NotNull(message = "Payment Method is required")
        UUID preferredPaymentSettingId,

        @NotEmpty(message = "Order must have at least on item")
        @NotNull(message = "Order must have at least on item")
        List<OrderItemRequestDto> orderItems
) {
}
