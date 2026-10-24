package project.fooddelivery.api.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank(message = "Phone number is required")
        String phoneNumber,
        @NotBlank(message = "Password is required")
        String password) {
}
