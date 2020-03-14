package com.epam.esm.facade;

import com.epam.esm.converter.DoctorDtoConverter;
import com.epam.esm.converter.UserDtoConverter;
import com.epam.esm.dto.doctor.DoctorResponseDto;
import com.epam.esm.dto.user.UserRequestDto;
import com.epam.esm.dto.user.UserResponseDto;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.security.UserPrincipal;
import com.epam.esm.service.DoctorService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserFacade {
    private UserService userService;
    private DoctorService doctorService;

    @Autowired
    public UserFacade(UserService userService, DoctorService doctorService) {
        this.userService = userService;
        this.doctorService = doctorService;
    }

    public UserResponseDto get(Long id) {
        if (getCurrentUser().getRole().equals(Role.ROLE_ADMIN)) {
            return UserDtoConverter.convertToDto(userService.findById(id));
        }
        if (getCurrentUser().getId().equals(id)) {
            return UserDtoConverter.convertToDto(userService.findById(id));
        } else {
            throw new AccessDeniedException("this is not your id");
        }
    }

    public List<UserResponseDto> getAll(int page, int size) {
        User user = getCurrentUser();
        if (user.getRole().equals(Role.ROLE_ADMIN)) {
            return userService.findAll(page, size).stream().map(UserDtoConverter::convertToDto)
                    .collect(Collectors.toList());
        }
        return Collections.singletonList(UserDtoConverter.convertToDto(userService.findById(user.getId())));
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

    public UserResponseDto getCurrentUserDto() {
        return UserDtoConverter.convertToDto(UserDtoConverter
                .convertToUser((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
    }

    User getCurrentUser() {
        return UserDtoConverter
                .convertToUser((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public List<DoctorResponseDto> getAllDoctorsForCurrentUser(int page, int size) {
        return doctorService.findAllForCurrentUser(getCurrentUser().getId(), page, size).stream().map(DoctorDtoConverter::convertToDto).collect(Collectors.toList());
    }
}
