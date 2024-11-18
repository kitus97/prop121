package com.prop.prop12_1.exceptions;

public class SupermarketAlreadyAddedException extends RuntimeException {
    public SupermarketAlreadyAddedException(String message) {
        super("Error: " + message);
    }
}
