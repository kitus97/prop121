package com.prop.prop12_1.model;

import java.util.HashSet;
import java.util.Set;

public class Product {
    private String name;
    private Set<Characteristics> characteristics;

    public Product(String name) {
        this.name = name;
        characteristics = new HashSet<Characteristics>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Characteristics> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(Set<Characteristics> characteristics) {
        this.characteristics = characteristics;
    }

    public void addCharacteristic(Characteristics characteristic) {
        characteristics.add(characteristic);
    }

    public void removeCharacteristic(Characteristics characteristic) {
        characteristics.remove(characteristic);
    }
}

