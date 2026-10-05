package project.fooddelivery.api.customer.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegistrationRequestDto(
        @NotNull(message = "fullName is required") String fullName,
        @NotNull(message = "phone is required") @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number format") String phone,
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$", message = "Password must contain uppercase, lowercase, number and special character") String password,
        @NotNull(message = "confirmPassword is required") String confirmPassword) {
   
    

}
