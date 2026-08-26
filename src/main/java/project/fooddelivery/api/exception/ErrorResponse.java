package project.fooddelivery.api.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(String status, String message, LocalDateTime Timestamp) {
}
