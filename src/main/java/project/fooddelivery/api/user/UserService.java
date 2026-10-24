package project.fooddelivery.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.auth.exception.UserAlreadyExistsException;
import project.fooddelivery.api.enums.ErrorMessage;
import project.fooddelivery.api.user.entity.User;
import project.fooddelivery.api.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean isUserExistsByPhonNumber(String phoneNumber) {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new UserAlreadyExistsException(ErrorMessage.USER_ALREADY_REGISTERED.getMessage());
        }
        return false;
    }


    public User saveUserAndReturnIt(User user){
        return userRepository.save(user);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

}
