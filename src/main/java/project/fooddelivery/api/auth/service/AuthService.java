package project.fooddelivery.api.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.Customer.service.CustomerService;
import project.fooddelivery.api.auth.config.JwtProperties;
import project.fooddelivery.api.auth.dto.LoginRequestDto;
import project.fooddelivery.api.auth.dto.RegisterRequestDto;
import project.fooddelivery.api.auth.dto.TokenResponseDto;
import project.fooddelivery.api.auth.exception.InvalidRefreshTokenException;
import project.fooddelivery.api.auth.exception.UserAlreadyExistsException;
import project.fooddelivery.api.user.entity.User;
import project.fooddelivery.api.user.entity.UserType;
import project.fooddelivery.api.user.repository.UserRepository;
import project.fooddelivery.api.user.repository.UserTypeRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String TOKEN_TYPE = "Bearer";

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final JwtProperties jwtProperties;

    @Transactional
    public TokenResponseDto registerCustomer(RegisterRequestDto registerRequestDto) {
        if (userRepository.existsByPhoneNumber(registerRequestDto.phoneNumber())) {
            throw new UserAlreadyExistsException("Phone number is already registered");
        }
        UserType customerType = userTypeRepository.findByUserTypeName(UserType.CUSTOMER)
                .orElseThrow(() -> new IllegalStateException("CUSTOMER user type is not configured"));

        User user = userRepository.save(User.builder()
                .firstName(registerRequestDto.firstName())
                .lastName(registerRequestDto.lastName())
                .phoneNumber(registerRequestDto.phoneNumber())
                .userPassword(passwordEncoder.encode(registerRequestDto.password()))
                .userType(customerType)
                .build());
        customerService.createCustomer(user.getUserId());

        return issueTokens(user);
    }

    @Transactional
    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(
                loginRequestDto.phoneNumber(), loginRequestDto.password()));

        User user = userRepository.findByPhoneNumber(loginRequestDto.phoneNumber()).orElseThrow();
        return issueTokens(user);
    }

    // keep the revocation done on refresh token reuse even though an exception is thrown
    @Transactional(dontRollbackOn = InvalidRefreshTokenException.class)
    public TokenResponseDto refresh(String refreshToken) {
        UUID userId = refreshTokenService.consumeRefreshToken(refreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidRefreshTokenException("Invalid refresh token"));
        return issueTokens(user);
    }

    @Transactional
    public void logout(String refreshToken) {
        refreshTokenService.revokeRefreshToken(refreshToken);
    }

    private TokenResponseDto issueTokens(User user) {
        return TokenResponseDto.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(refreshTokenService.createRefreshToken(user.getUserId()))
                .tokenType(TOKEN_TYPE)
                .expiresIn(jwtProperties.getAccessTokenTtl().toSeconds())
                .build();
    }
}
