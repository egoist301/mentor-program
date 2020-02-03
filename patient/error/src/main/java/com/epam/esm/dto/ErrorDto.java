package com.epam.esm.dto;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class ErrorDto implements Serializable {
    private int code;
    private HttpStatus status;
    private String message;

    public ErrorDto() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
