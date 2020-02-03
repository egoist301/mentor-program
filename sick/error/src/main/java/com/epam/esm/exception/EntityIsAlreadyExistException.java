package com.epam.esm.exception;

public class EntityIsAlreadyExistException extends RuntimeException {
    public EntityIsAlreadyExistException() {
    }

    public EntityIsAlreadyExistException(String message) {
        super(message);
    }

    public EntityIsAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityIsAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
