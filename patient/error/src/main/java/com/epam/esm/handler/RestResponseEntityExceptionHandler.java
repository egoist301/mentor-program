package com.epam.esm.handler;

import com.epam.esm.dto.ErrorDto;
import com.epam.esm.exception.AnyIllnessExistWithSameNameException;
import com.epam.esm.exception.AnyPatientExistWithSameIdentificationNumberException;
import com.epam.esm.exception.IllnessAlreadyExistException;
import com.epam.esm.exception.IllnessNotExistException;
import com.epam.esm.exception.IncorrectPathVariableException;
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
        return createResponse(new RuntimeException(ex), status);
    }

    @ExceptionHandler({IllnessAlreadyExistException.class, PatientAlreadyExistException.class,
            AnyIllnessExistWithSameNameException.class, AnyPatientExistWithSameIdentificationNumberException.class,
            IllnessNotExistException.class, PatientNotExistException.class})
    public ResponseEntity<Object> handleExistException(RuntimeException e, WebRequest request) {
        return createResponse(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ParseDateException.class)
    public ResponseEntity<Object> handleParseException(RuntimeException e, WebRequest request) {
        return createResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectPathVariableException.class)
    public ResponseEntity<Object> handlePathVariableException(RuntimeException e, WebRequest request) {
        return createResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleException(RuntimeException e, WebRequest request) {
        return createResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> createResponse(RuntimeException ex, HttpStatus status) {
        ErrorDto error = new ErrorDto();
        error.setStatus(status);
        error.setCode(status.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), status);
    }


}
