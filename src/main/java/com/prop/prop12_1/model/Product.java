package com.prop.prop12_1.model;

import java.util.HashSet;
import java.util.Set;

public class Product {
    private String name;
    private Set<Characteristics> characterisics;

    public Product(String name) {
        this.name = name;
        characterisics = new HashSet<Characteristics>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Characteristics> getCharacterisics() {
        return characterisics;
    }

    public void setCharacterisics(Set<Characteristics> characterisics) {
        this.characterisics = characterisics;
    }

    public void addCharacterisics(Characteristics characteristic) {
        characterisics.add(characteristic);
    }

    public void removeCharacterisics(Characteristics characteristic) {
        characterisics.remove(characteristic);
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", characterisics=" + characterisics +
                '}';
    }
}

