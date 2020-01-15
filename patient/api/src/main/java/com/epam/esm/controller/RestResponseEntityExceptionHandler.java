package com.epam.esm.controller;

import com.epam.esm.controller.dto.ErrorDto;
import com.epam.esm.service.IllnessNameExistException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        ErrorDto error = new ErrorDto();
        error.setCode(status.value());
        error.setStatus(status);
        error.setMessage("Argument is not valid.");
        return new ResponseEntity<>(error, headers, status);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleException(RuntimeException e, WebRequest request) {
        ErrorDto error = new ErrorDto();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        error.setCode(status.value());
        error.setStatus(status);
        error.setMessage("Server error. Try later please.");
        return new ResponseEntity<>(error, new HttpHeaders(), status);
    }
    @ExceptionHandler(IllnessNameExistException.class)
    public ResponseEntity<Object> handleIllnessExistException(RuntimeException e, WebRequest request) {
        ErrorDto error = new ErrorDto();
        HttpStatus status = HttpStatus.CONFLICT;
        error.setCode(status.value());
        error.setStatus(status);
        error.setMessage(e.getMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), status);
    }
}
