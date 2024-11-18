package com.prop.prop12_1.exceptions;

public class sameProductException extends RuntimeException {
    public sameProductException(String message) {
        super("Error: " + message);
    }
}
