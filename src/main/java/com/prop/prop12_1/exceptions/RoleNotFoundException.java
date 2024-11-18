package com.prop.prop12_1.exceptions;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String message) {
        super("Error: " + message);
    }
}
