package com.prop.prop12_1.exceptions;

public class CharacteristicAlreadyAddedToProductException extends RuntimeException {
    public CharacteristicAlreadyAddedToProductException(String message) {
        super("Error: " + message);
    }
}
