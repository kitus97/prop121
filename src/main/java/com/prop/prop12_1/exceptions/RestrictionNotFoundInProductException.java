package com.prop.prop12_1.exceptions;

public class RestrictionNotFoundInProductException extends RuntimeException {
    public RestrictionNotFoundInProductException(String message) {
        super("Error: " + message);
    }
}
