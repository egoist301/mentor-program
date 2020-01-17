package com.epam.esm.exception;

public class IllnessAlreadyExistException extends RuntimeException {
    public IllnessAlreadyExistException() {
    }

    public IllnessAlreadyExistException(String message) {
        super(message);
    }

    public IllnessAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllnessAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
