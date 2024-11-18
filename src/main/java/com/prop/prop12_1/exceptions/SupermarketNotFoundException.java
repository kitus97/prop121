package com.prop.prop12_1.exceptions;

public class SupermarketNotFoundException extends RuntimeException {
    public SupermarketNotFoundException(String message) {
        super("Error: " + message);
    }
}
