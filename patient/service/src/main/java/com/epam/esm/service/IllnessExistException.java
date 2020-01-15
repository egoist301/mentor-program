package com.epam.esm.service;

public class IllnessExistException extends RuntimeException {
    public IllnessExistException() {
    }

    public IllnessExistException(String message) {
        super(message);
    }

    public IllnessExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllnessExistException(Throwable cause) {
        super(cause);
    }
}
