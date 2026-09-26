package project.fooddelivery.api.customer.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import project.fooddelivery.api.customer.repository.UserRepository;

@Service 
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    
}
