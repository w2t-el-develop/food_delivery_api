package project.fooddelivery.api.customer.service;

import java.util.Optional;

import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import project.fooddelivery.api.customer.entity.User;
import project.fooddelivery.api.customer.repository.UserRepository;

@Service 
@RequiredArgsConstructor 
public class UserService {
    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByPhoneNumber(
            String phone) {
        return userRepository.findByPhoneNumber(phone);
    }

}
