package com.epam.esm.controller;

import com.epam.esm.dto.JwtAuthenticationResponse;
import com.epam.esm.dto.user.AuthorizeDto;
import com.epam.esm.security.TokenProvider;
import com.epam.esm.security.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin
@RestController
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private TokenProvider jwtTokenProvider;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    TokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid AuthorizeDto authorizeDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authorizeDto.getUsername(), authorizeDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", jwt);
        return new ResponseEntity<>(new JwtAuthenticationResponse(jwt), httpHeaders, HttpStatus.OK);
    }
}
