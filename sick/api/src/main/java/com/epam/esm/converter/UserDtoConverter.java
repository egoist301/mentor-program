package com.epam.esm.converter;

import com.epam.esm.dto.user.UserRequestDto;
import com.epam.esm.dto.user.UserResponseDto;
import com.epam.esm.entity.User;
import com.epam.esm.security.UserPrincipal;

import java.util.stream.Collectors;

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
        if (user.getOrders() != null) {
            userResponseDto.setOrderResponseDtos(user.getOrders().stream().map(OrderDtoConverter::convertToDto).collect(
                    Collectors.toSet()));
        }
        return userResponseDto;
    }

    public static User convertToUser(UserPrincipal userPrincipal) {
        User user = new User();
        user.setId(userPrincipal.getId());
        user.setUsername(userPrincipal.getUsername());
        user.setPassword(userPrincipal.getPassword());
        user.setRole(userPrincipal.getRole());
        return user;
    }
}
