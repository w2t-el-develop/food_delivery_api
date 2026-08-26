package project.fooddelivery.api.feature.cart;

import java.time.OffsetDateTime;

      public record ErrorResponse(
            OffsetDateTime timestamp,
            int status,
            String error,
            String message
    ) {
    }

