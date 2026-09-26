package project.fooddelivery.api.customer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.fooddelivery.api.customer.service.AuthService;

@RestController 
@RequiredArgsConstructor 
@RequestMapping ("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    
}
