package com.prop.prop12_1.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super("Error: " + message);
    }
}
