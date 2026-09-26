package com.example.food_Delivery_api_v3.address.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record AddToAddressRequestDto(
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
