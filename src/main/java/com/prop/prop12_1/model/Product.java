package com.prop.prop12_1.model;

import java.util.*;

public class Product {
    private Integer id;
    private String name;
    private List<Characteristics> characteristics;
    private List<Characteristics> restrictions;

    public Product(Integer id, String name) {
        this.id = id;
        this.name = name;
        characteristics = new ArrayList<>();
        restrictions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Characteristics> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<Characteristics> characteristics) {
        this.characteristics = characteristics;
    }

    public void addCharacteristic(Characteristics characteristic) {
        characteristics.add(characteristic);
    }

    public void removeCharacteristic(Characteristics characteristic) {
        characteristics.remove(characteristic);
    }

    public List<Characteristics> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<Characteristics> restrictions) {
        this.restrictions = restrictions;
    }

    public void addRestriction(Characteristics characteristic) {
        restrictions.add(characteristic);
    }

    public void removeRestriction(Characteristics characteristic) {
        restrictions.remove(characteristic);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

