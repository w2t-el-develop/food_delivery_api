package project.fooddelivery.api.user.mapper;

import org.springframework.stereotype.Component;
import project.fooddelivery.api.user.dto.AddUserRequestDto;
import project.fooddelivery.api.user.dto.UpdateUserRequestDto;
import project.fooddelivery.api.user.dto.UserResponseDto;
import project.fooddelivery.api.user.entity.User;

@Component("userMapper")
public class UserMapper {
    public User requestDtoToUser(AddUserRequestDto userRequestDto){
        return User.builder()
                .firstName(userRequestDto.firstName())
                .lastName(userRequestDto.lastName())
                .phoneNumber(userRequestDto.phoneNumber())
                .userPassword(userRequestDto.userPassword())
                .build();
    }

    public User requestDtoToUser(UpdateUserRequestDto userRequestDto){
        return User.builder()
                .userId(userRequestDto.userId())
                .firstName(userRequestDto.firstName())
                .lastName(userRequestDto.lastName())
                .phoneNumber(userRequestDto.phoneNumber())
                .userPassword(userRequestDto.userPassword())
                .build();
    }

    public UserResponseDto UserToResponseDto(User user){
        return new UserResponseDto(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber()
        );
    }
}
