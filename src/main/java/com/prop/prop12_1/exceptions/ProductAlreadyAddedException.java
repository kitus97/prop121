package com.prop.prop12_1.exceptions;

public class ProductAlreadyAddedException extends RuntimeException {
    public ProductAlreadyAddedException(String message) {
        super("Error: " + message);
    }
}
