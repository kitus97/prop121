package com.prop.prop12_1.exceptions;

public class CatalogAlreadyAdded extends RuntimeException {
    public CatalogAlreadyAdded(String message) {
        super("Error: " + message);
    }
}
