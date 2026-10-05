package project.fooddelivery.api.customer.service;


import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.fooddelivery.api.customer.entity.UserType;
import project.fooddelivery.api.customer.repository.UserTypeRepository;
import java.util.Optional;
@Service
@RequiredArgsConstructor 
public class UserTypeService {

 private final UserTypeRepository userTypeRepository;

 public Optional<UserType> existsByUserTypeName(String userTypeName) {
    return Optional.ofNullable(userTypeRepository.findByUserTypeName(userTypeName));
 }
 public UserType saveUserType(UserType userType) {
    return userTypeRepository.save(userType);
 }

    
}
