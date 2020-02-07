package com.epam.esm.facade;

import com.epam.esm.converter.UserDtoConverter;
import com.epam.esm.dto.user.UserRequestDto;
import com.epam.esm.dto.user.UserResponseDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityIsAlreadyExistException;
import com.epam.esm.exception.EntityIsNotExistException;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
        if (userService.isUserExist(id)) {
            return UserDtoConverter.convertToDto(userService.findById(id));
        } else {
            throw new EntityIsNotExistException("гыук is not exist");
        }
    }

    public List<UserResponseDto> getAll() {
        return userService.findAll().stream().map(UserDtoConverter::convertToDto).collect(Collectors.toList());
    }

    public UserResponseDto create(UserRequestDto userRequestDto) {
        User user = UserDtoConverter.convertToEntity(userRequestDto);
        if (userService.isUserExist(user.getUsername())) {
            throw new EntityIsAlreadyExistException("user already exist");
        } else {
            userService.create(user);
            return UserDtoConverter.convertToDto(userService.findByUsername(user.getUsername()));
        }
    }

    public UserResponseDto update(Long id, UserRequestDto userRequestDto) {
        User user = UserDtoConverter.convertToEntity(userRequestDto);
        user.setId(id);
        if (!userService.isUserExist(user.getId())) {
            throw new EntityIsNotExistException("illness is not exist");
        } else if (userService.isAnyUserExistWithUsername(user.getId(), user.getUsername())) {
            throw new EntityIsAlreadyExistException(
                    "user with this username:" + user.getUsername() + " already exist");
        } else {
            userService.update(user);
            return UserDtoConverter.convertToDto(userService.findByUsername(user.getUsername()));
        }
    }

    public void delete(Long id) {
        if (userService.isUserExist(id)) {
            userService.delete(id);
        } else {
            throw new EntityIsNotExistException("user is not exist");
        }
    }
}
