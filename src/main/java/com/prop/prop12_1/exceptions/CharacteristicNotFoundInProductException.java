package com.prop.prop12_1.exceptions;

public class CharacteristicNotFoundInProductException extends RuntimeException {
    public CharacteristicNotFoundInProductException(String message) {
        super("Error: " + message);
    }
}
