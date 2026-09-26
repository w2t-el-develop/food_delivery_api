package com.example.food_Delivery_api_v3.customer;

import java.util.UUID;

public record MockCustomer(
        UUID customerId,
        UUID userId
) {
}
