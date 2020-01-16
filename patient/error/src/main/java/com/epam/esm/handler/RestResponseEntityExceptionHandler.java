package com.epam.esm.handler;

import com.epam.esm.dto.ErrorDto;
import com.epam.esm.exception.AnyIllnessExistWithSameNameException;
import com.epam.esm.exception.AnyPatientExistWithSameIdentificationNumberException;
import com.epam.esm.exception.IllnessAlreadyExistException;
import com.epam.esm.exception.IllnessNotExistException;
import com.epam.esm.exception.ParseDateException;
import com.epam.esm.exception.PatientAlreadyExistException;
import com.epam.esm.exception.PatientNotExistException;
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

    @ExceptionHandler({IllnessAlreadyExistException.class, PatientAlreadyExistException.class,
            AnyIllnessExistWithSameNameException.class, AnyPatientExistWithSameIdentificationNumberException.class,
            IllnessNotExistException.class, PatientNotExistException.class})
    public ResponseEntity<Object> handleExistException(RuntimeException e, WebRequest request) {
        ErrorDto error = new ErrorDto();
        HttpStatus status = HttpStatus.CONFLICT;
        error.setCode(status.value());
        error.setStatus(status);
        error.setMessage(e.getMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), status);
    }

    @ExceptionHandler(ParseDateException.class)
    public ResponseEntity<Object> handleParseException(RuntimeException e, WebRequest request) {
        ErrorDto error = new ErrorDto();
        HttpStatus status = HttpStatus.OK;
        error.setCode(status.value());
        error.setStatus(status);
        error.setMessage(e.getMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), status);
    }
}
