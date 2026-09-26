package project.fooddelivery.api.customer.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.fooddelivery.api.customer.dto.RegistrationRequestDto;
import project.fooddelivery.api.customer.dto.RegistrationResponseDto;
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
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserTypeService userTypeService;

   
    @Transactional
    public RegistrationResponseDto registerCustomer(RegistrationRequestDto request) {
        if (request.phone() == null || request.password() == null || 
        request.confirmPassword() == null|| request.fullName() == null) {
            throw new InvalidUserInputException("Missing required fields");
        }
        if (!request.password().equals(request.confirmPassword())) {
            throw new InvalidUserInputException("Password and confirm password do not match");
        }
        if (userRepository.findByPhoneNumber(request.phone()).isPresent()) {
            throw new InvalidUserInputException("User with this phone number already exists");
        }
        var user = new User();
        user.setPhoneNumber(request.phone());
        user.setUserPassword(passwordEncoder.encode(request.password()));
        user.setFullName(request.fullName());
        UserType userType = userTypeService.existsByUserTypeName("CUSTOMER")
            .orElseGet(() -> {
                UserType newUserType = new UserType();
                newUserType.setUserTypeName("CUSTOMER");
                return userTypeService.saveUserType(newUserType);
            });
        user.setUserType(userType);
        user = userRepository.save(user);

        Customer customer = new Customer();
        customer.setUser(user);
        customer = customerRepository.save(customer);

        String token = jwtService.generateToken(user.getPhoneNumber(), user.getUserId(), customer.getCustomerId(),
            user.getUserType().getUserTypeName());
        return new RegistrationResponseDto(token);
    }
    @Transactional(readOnly = true)
    public RegistrationResponseDto loginCustomer(LoginRequestDto loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.phone(), loginRequest.password()));

        User user = userRepository.findByPhoneNumber(loginRequest.phone())
                .orElseThrow(() -> new InvalidUserInputException("The input is not correct"));
        Customer customer = customerRepository.findByUser_UserId(user.getUserId())
                .orElseThrow(() -> new InvalidUserInputException("Customer account not found"));

        String token = jwtService.generateToken(user.getPhoneNumber(), user.getUserId(), customer.getCustomerId(),
                user.getUserType().getUserTypeName());
        return new RegistrationResponseDto(token);
    }
}
