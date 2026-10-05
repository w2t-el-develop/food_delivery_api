package project.fooddelivery.api.customer.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record LoginRequestDto(
        @NotNull(message = "phone is required")
        @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number format")
        String phone,
        @NotNull(message = "Password is required")
        String password
) {


}
