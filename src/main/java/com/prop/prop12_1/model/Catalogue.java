package com.prop.prop12_1.model;

import java.util.*;

import com.prop.prop12_1.controller.CtrlProd;
import com.prop.prop12_1.exceptions.ProductAlreadyAddedException;
import org.apache.commons.lang3.tuple.Pair;

public class Catalogue {
    private static Map<String, Product> allProducts = new CtrlProd().getProducts();
    private String name;
    private Map<String, Product> products;

    public Catalogue(String name) {
        this.name = name;
        products = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getProductNames() {
        return new ArrayList<>(products.keySet());
    }

    public List<Pair<Integer, Set<String>>> getProductsArray() {
        List<Pair<Integer, Set<String>>> ret= new ArrayList<Pair<Integer, Set<String>>>();
        for(Product p : products.values()) {
            ret.add(Pair.of(p.getId(), p.getRestrictions()));
        }
        return ret;
    }

    public void setProducts(Map<String, Product> products) {
        this.products = products;
    }

    public Map<String, Product> getProducts() {
        return products;
    }

    public void addProduct(String product) {
        if(!allProducts.containsKey(product)) throw new NoSuchElementException("The product " + product + " does not exist.");
        else if(products.containsKey(product)) throw new ProductAlreadyAddedException("The product " + product + " already exists in the catalogue.");
        else products.put(product, allProducts.get(product));
    }

    public void removeProduct(String product) {
        if(products.remove(product) == null) throw new NoSuchElementException("The product " + product + " does not exist in the catalogue.");
    }

    @Override
    public String toString() {
        return "Catalogue{" +
                "name='" + name + '\'' +
                ", products=" + products +
                '}';
    }
}
