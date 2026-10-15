package project.fooddelivery.api.user.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserResponseDto(
        UUID userId,
        String firstName,
        String lastName,
        String phoneNumber
) {
}
