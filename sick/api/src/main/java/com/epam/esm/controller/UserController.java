package com.epam.esm.controller;

import com.epam.esm.dto.user.UserRequestDto;
import com.epam.esm.dto.user.UserResponseDto;
import com.epam.esm.service.UserService;
import com.epam.esm.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> get(@PathVariable Long id) {
        Validator.validateId(id);
        return new ResponseEntity<>(userService.get(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserRequestDto userRequestDto) {
        userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        userService.create(userRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id,
                                                  @RequestBody @Valid UserRequestDto userRequestDto) {
        Validator.validateId(id);
        userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        userService.update(id, userRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        Validator.validateId(id);
        userService.delete(id);
    }
}
