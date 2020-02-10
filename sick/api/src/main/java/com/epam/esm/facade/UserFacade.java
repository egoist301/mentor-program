package com.epam.esm.facade;

import com.epam.esm.converter.UserDtoConverter;
import com.epam.esm.dto.user.UserRequestDto;
import com.epam.esm.dto.user.UserResponseDto;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.security.UserPrincipal;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserFacade {
    private UserService userService;

    @Autowired
    public UserFacade(UserService userService) {
        this.userService = userService;
    }

    public UserResponseDto get(Long id) {
        return UserDtoConverter.convertToDto(userService.findById(id));
    }

    public List<UserResponseDto> getAll(int page, int size) {
        return userService.findAll(page, size).stream().map(UserDtoConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto create(UserRequestDto userRequestDto) {
        User user = UserDtoConverter.convertToEntity(userRequestDto);
        userService.create(user);
        return UserDtoConverter.convertToDto(userService.findByUsername(user.getUsername()));
    }

    public UserResponseDto update(Long id, UserRequestDto userRequestDto) {
        User user = UserDtoConverter.convertToEntity(userRequestDto);
        user.setId(id);
        userService.update(user);
        return UserDtoConverter.convertToDto(userService.findByUsername(user.getUsername()));
    }

    public void delete(Long id) {
        userService.delete(id);
    }

    User getCurrentUser() {
        return UserDtoConverter
                .convertToUser((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
