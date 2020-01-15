package com.epam.esm.service;

public class IllnessNameExistException extends RuntimeException {
    public IllnessNameExistException() {
    }

    public IllnessNameExistException(String message) {
        super(message);
    }

    public IllnessNameExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllnessNameExistException(Throwable cause) {
        super(cause);
    }
}
