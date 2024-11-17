package com.prop.prop12_1.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super("Error: " + message);
    }
}
