package com.prop.prop12_1.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Product {
    private Integer id;
    private String name;
    private Set<Characteristics> characteristics;
    private Set<Characteristics> restrictions;

    public Product(Integer id, String name) {
        this.id = id;
        this.name = name;
        characteristics = new HashSet<>();
        restrictions = new HashSet<>();
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

    public Set<String> getRestrictions() {
        return restrictions.stream()
                .map(Characteristics::getName)
                .collect(Collectors.toSet());
    }

    public void setRestrictions(Set<Characteristics> restrictions) {
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

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", characteristics=" + characteristics +
                ", restrictions=" + restrictions +
                '}';
    }
}

