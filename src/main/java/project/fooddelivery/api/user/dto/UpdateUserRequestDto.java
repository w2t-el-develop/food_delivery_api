package project.fooddelivery.api.user.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateUserRequestDto(
        @NotNull(message = "the id of user is required")
        UUID userId,
        @NotNull(message = "first name is required")
        String firstName,
        @NotNull(message = "last name is required")
        String lastName,
        @NotNull(message = "phone number is required")
        String phoneNumber,
        @NotNull(message = "user password is required")
        String userPassword
) {
}
