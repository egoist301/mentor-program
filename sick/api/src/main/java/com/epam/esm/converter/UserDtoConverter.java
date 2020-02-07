package com.epam.esm.converter;

import com.epam.esm.dto.user.UserRequestDto;
import com.epam.esm.dto.user.UserResponseDto;
import com.epam.esm.entity.User;

public class UserDtoConverter {
    public static User convertToEntity(UserRequestDto userRequestDto) {
        User user = new User();
        user.setUsername(userRequestDto.getUsername());
        user.setPassword(userRequestDto.getPassword());
        return user;
    }

    public static UserResponseDto convertToDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setRole(user.getRole());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setCreateDate(user.getCreateDate());
        userResponseDto.setUpdateDate(user.getUpdateDate());
        return userResponseDto;
    }
}
