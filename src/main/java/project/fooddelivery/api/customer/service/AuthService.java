package project.fooddelivery.api.customer.service;

import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.fooddelivery.api.customer.dto.RegistrationRequestDto;
import project.fooddelivery.api.customer.dto.TokenResponseDto;
import project.fooddelivery.api.customer.dto.LoginRequestDto;
import project.fooddelivery.api.customer.entity.Customer;
import project.fooddelivery.api.customer.entity.User;
import project.fooddelivery.api.customer.repository.CustomerRepository;
import project.fooddelivery.api.customer.repository.UserRepository;
import project.fooddelivery.api.exceptionhandling.InvalidUserInputException;
import project.fooddelivery.api.service.JwtService;
import project.fooddelivery.api.customer.entity.UserType;
import org.springframework.transaction.annotation.Transactional;

@Service 
@RequiredArgsConstructor
public class AuthService {

    private final CustomerService customerService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserTypeService userTypeService;

   
  @Transactional
public TokenResponseDto registerCustomer(RegistrationRequestDto request) {
    // 1. Lambda-based null check
    if (Stream.of(request.phone(), request.password(), request.confirmPassword(), request.fullName()).anyMatch(Objects::isNull)) {
        throw new InvalidUserInputException("Missing required fields");
    }

    if (!request.password().equals(request.confirmPassword())) {
        throw new InvalidUserInputException("Password and confirm password do not match");
    }

    userService.findByPhoneNumber(request.phone())
        .ifPresent(u -> {
            throw new InvalidUserInputException("User with this phone number already exists");
        });

    UserType userType = userTypeService.existsByUserTypeName("CUSTOMER")
        .orElseGet(() -> userTypeService.saveUserType(new UserType("CUSTOMER")));

    User user = userService.save(new User(request.phone(), passwordEncoder.encode(request.password()), request.fullName(), userType));
    Customer customer = customerService.save(new Customer(user));

    String token = jwtService.generateToken(
        user.getPhoneNumber(),
        user.getUserId(),
        customer.getCustomerId(),
        user.getUserType().getUserTypeName()
    );

    return new TokenResponseDto(token);   
}
    @Transactional(readOnly = true)
    public TokenResponseDto loginCustomer(LoginRequestDto loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.phone(), loginRequest.password()));

        User user = userService.findByPhoneNumber(loginRequest.phone())
                .orElseThrow(() -> new InvalidUserInputException("The input is not correct"));
        Customer customer = customerService.findByUserId(user.getUserId())
                .orElseThrow(() -> new InvalidUserInputException("Customer account not found"));

        String token = jwtService.generateToken(user.getPhoneNumber(), user.getUserId(), customer.getCustomerId(),
                user.getUserType().getUserTypeName());
        return new TokenResponseDto(token);
    }
}
