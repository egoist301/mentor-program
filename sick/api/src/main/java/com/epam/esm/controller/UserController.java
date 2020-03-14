package com.epam.esm.controller;

import com.epam.esm.constant.AppConstants;
import com.epam.esm.dto.doctor.DoctorResponseDto;
import com.epam.esm.dto.user.UserRequestDto;
import com.epam.esm.dto.user.UserResponseDto;
import com.epam.esm.facade.UserFacade;
import com.epam.esm.security.exception.BadRequestException;
import com.epam.esm.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserFacade userFacade;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserFacade userFacade,
                          PasswordEncoder passwordEncoder) {
        this.userFacade = userFacade;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> get(@PathVariable Long id) {
        Validator.validateId(id);
        return new ResponseEntity<>(userFacade.get(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        Validator.validatePageNumberAndSize(page, size);
        return new ResponseEntity<>(userFacade.getAll(page, size), HttpStatus.OK);
    }

    @PostMapping("/signUp")
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserRequestDto userRequestDto) {
        if (!userRequestDto.getPassword().equals(userRequestDto.getConfirmedPassword())) {
            throw new BadRequestException("passwords are not equal");
        }
        userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        UserResponseDto userResponseDto = userFacade.create(userRequestDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(userResponseDto.getId()).toUri());
        return new ResponseEntity<>(userResponseDto, httpHeaders, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id,
                                                  @RequestBody @Valid UserRequestDto userRequestDto) {
        Validator.validateId(id);
        userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

        return new ResponseEntity<>(userFacade.update(id, userRequestDto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        Validator.validateId(id);
        userFacade.delete(id);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/orders")
    public ResponseEntity<List<DoctorResponseDto>> getAllDoctorsForCurrentUser(
            @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        Validator.validatePageNumberAndSize(page, size);
        return new ResponseEntity<>(userFacade.getAllDoctorsForCurrentUser(page, size), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        return new ResponseEntity<>(userFacade.getCurrentUserDto(), HttpStatus.OK);
    }
}
