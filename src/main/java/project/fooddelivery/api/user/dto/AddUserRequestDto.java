package project.fooddelivery.api.user.dto;

import jakarta.validation.constraints.NotNull;

public record AddUserRequestDto(
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
