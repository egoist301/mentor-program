package com.epam.esm.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRequestDto {
    @NotBlank(message = "username can't be null or empty")
    @Size(min = 4, max = 30, message = "username can be 4 to 30 characters long")
    @JsonProperty("username")
    private String username;

    @NotBlank(message = "password can't be null or empty")
    @Size(min = 4, max = 30, message = "password can be 4 to 30 characters long")
    @JsonProperty("password")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
