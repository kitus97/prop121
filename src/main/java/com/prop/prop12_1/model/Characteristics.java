package com.prop.prop12_1.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marc.rams.estrada
 */

public class Characteristics {

    private int id;
    private String name;
    private List<Product> associatedProducts = new ArrayList<>();

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

    public List<Product> getAssociatedProducts() {
        return associatedProducts;
    }

    public void setAssociatedProducts(List<Product> associatedProducts) {
        this.associatedProducts = associatedProducts;
    }

    public void addAssociatedProduct(Product product) {
        associatedProducts.add(product);
    }

    public void removeAssociatedProduct(Product product) {
        associatedProducts.remove(product);
    }

    @Override
    public String toString() {
        return "Characteristics{" +
                "name='" + name + '\'' +
                '}';
    }
}
