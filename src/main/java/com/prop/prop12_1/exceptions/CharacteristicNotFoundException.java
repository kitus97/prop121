package com.prop.prop12_1.exceptions;

public class CharacteristicNotFoundException extends RuntimeException {
    public CharacteristicNotFoundException(String message) {
        super("Error: " + message);
    }
}
