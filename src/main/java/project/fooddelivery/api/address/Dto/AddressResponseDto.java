package com.example.food_Delivery_api_v3.address.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record AddressResponseDto(
        @NotNull(message = "address must be not null")
        UUID addressId,

        @NotNull(message = "customer must be not null")
        UUID customerId,

        @NotNull(message = "phone number is required")
        String PhoneNumber,
        @NotNull(message = "city is required")
        String city,
        @NotNull(message = "street is required")
        String street,
        @NotNull(message = "building number is required")
        @PositiveOrZero(message = "must be not negative")
        Integer buildingNumber,
        @NotNull(message = "apartment number is required")
        @PositiveOrZero(message = "must be not negative")
        Integer apartmentNumber
) {
}
