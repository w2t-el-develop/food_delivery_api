package project.fooddelivery.api.customer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import project.fooddelivery.api.customer.dto.RegistrationRequestDto;
import project.fooddelivery.api.customer.dto.TokenResponseDto;
import project.fooddelivery.api.customer.service.AuthService;
import org.springframework.http.ResponseEntity;
import project.fooddelivery.api.customer.dto.LoginRequestDto;

@RestController 
@RequiredArgsConstructor 
@RequestMapping ("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register-customer")
    public  ResponseEntity<TokenResponseDto> registerCustomer(@Valid @RequestBody RegistrationRequestDto request) {
        return ResponseEntity.ok(authService.registerCustomer(request));
    }
    @PostMapping("/Login-customer")
    public  ResponseEntity<TokenResponseDto> LoginCustomer(@Valid @RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(authService.loginCustomer(request));
    }
    @GetMapping ("/test")
    public String test() {
        return "Hello from AuthController";
    }
}
