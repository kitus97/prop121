package com.prop.prop12_1.model;

import java.util.HashSet;
import java.util.Set;

public class Characteristics {

    private int id;
    private String name;
    private Set<Product> associatedProducts = new HashSet<Product>();

    public Characteristics(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Characteristics() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getAssociatedProducts() {
        return associatedProducts;
    }

    public void setAssociatedProducts(Set<Product> associatedProducts) {
        this.associatedProducts = associatedProducts;
    }

    public void addAssociatedProduct(Product product) {
        associatedProducts.add(product);
    }

    public void removeAssociatedProduct(Product product) {
        associatedProducts.remove(product);
    }
}
