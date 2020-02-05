package com.epam.esm.controller;

import com.epam.esm.dto.JwtAuthenticationResponse;
import com.epam.esm.dto.user.UserRequestDto;
import com.epam.esm.security.JwtTokenProvider;
import com.epam.esm.service.CustomUserDetailsService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthenticationController {
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(UserService userService, CustomUserDetailsService customUserDetailsService,
                                    AuthenticationManager authenticationManager,
                                    JwtTokenProvider jwtTokenProvider,
                                    PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signIn")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid UserRequestDto userRequestDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userRequestDto.getUsername(), userRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);
        return new ResponseEntity<>(new JwtAuthenticationResponse(jwt), HttpStatus.OK);
    }

    @PostMapping("/signUp")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody @Valid UserRequestDto userRequestDto) {
        userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        userService.create(userRequestDto);
    }
}
