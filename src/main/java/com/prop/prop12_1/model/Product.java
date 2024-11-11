package com.prop.prop12_1.model;

import java.util.HashSet;
import java.util.Set;

public class Product {
    private Integer id;
    private String name;
    private Set<Object> characteristics;

    public Product(String name) {
        this.name = name;
        characteristics = new HashSet<Object>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Object> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(Set<Object> characteristics) {
        this.characteristics = characteristics;
    }

    public void addCharacteristic(Object characteristic) {
        characteristics.add(characteristic);
    }

    public void removeCharacteristic(Object characteristic) {
        characteristics.remove(characteristic);
    }
}

