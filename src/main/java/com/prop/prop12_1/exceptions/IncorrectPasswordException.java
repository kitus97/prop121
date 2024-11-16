package com.prop.prop12_1.exceptions;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException(String message) {
        super("Error: " + message);
    }
}
