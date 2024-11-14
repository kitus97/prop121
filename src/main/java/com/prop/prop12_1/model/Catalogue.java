package com.prop.prop12_1.model;

import java.util.HashSet;
import java.util.Set;

public class Catalogue {
    private String name;
    Set<Product> products;

    public Catalogue(String name) {
        this.name = name;
        products = new HashSet<Product>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }
}
