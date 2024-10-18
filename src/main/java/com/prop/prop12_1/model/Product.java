package com.prop.prop12_1.model;

import java.util.HashSet;
import java.util.Set;

public class Product {
    private String name;
    private Set<Object> characterisics;

    public Product(String name) {
        this.name = name;
        characterisics = new HashSet<Object>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Object> getCharacterisics() {
        return characterisics;
    }

    public void setCharacterisics(Set<Object> characterisics) {
        this.characterisics = characterisics;
    }

    public void addCharacterisics(Object characteristic) {
        characterisics.add(characteristic);
    }

    public void removeCharacterisics(Object characteristic) {
        characterisics.remove(characteristic);
    }
}

