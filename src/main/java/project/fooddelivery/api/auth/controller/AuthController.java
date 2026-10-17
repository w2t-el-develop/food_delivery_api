package project.fooddelivery.api.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.fooddelivery.api.auth.dto.LoginRequestDto;
import project.fooddelivery.api.auth.dto.RefreshTokenRequestDto;
import project.fooddelivery.api.auth.dto.RegisterRequestDto;
import project.fooddelivery.api.auth.dto.TokenResponseDto;
import project.fooddelivery.api.auth.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<TokenResponseDto> register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .cacheControl(CacheControl.noStore())
                .body(authService.registerCustomer(registerRequestDto));
    }

    @PostMapping("login")
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noStore())
                .body(authService.login(loginRequestDto));
    }

    @PostMapping("refresh")
    public ResponseEntity<TokenResponseDto> refresh(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noStore())
                .body(authService.refresh(refreshTokenRequestDto.refreshToken()));
    }

    @PostMapping("logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        authService.logout(refreshTokenRequestDto.refreshToken());
        return ResponseEntity.noContent().build();
    }
}
