package com.prop.prop12_1.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

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

    public List<Pair<Integer, Set<String>>> getProductsArray() {
        List<Pair<Integer, Set<String>>> ret= new ArrayList<Pair<Integer, Set<String>>>();
        for(Product p : products) {
            ret.add(Pair.of(p.getId(), p.getRestrictions()));
        }
        return ret;
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
