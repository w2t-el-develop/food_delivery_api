package project.fooddelivery.api.order.dto;

import java.util.UUID;

public record OrderResponseDto(
        UUID orderId,
        UUID customerId,
        UUID restaurantI
) {
}
