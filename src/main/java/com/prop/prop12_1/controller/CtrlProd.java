package com.prop.prop12_1.controller;

import com.prop.prop12_1.model.Characteristics;
import com.prop.prop12_1.model.Product;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashSet;
import java.util.Set;


public class CtrlProd {
    Set<Product> products = new HashSet<>();
    Set<Characteristics> characteristics = new HashSet<>();
    public Set<Product> getProducts() {
        return products;
    }



    public void setProducts(Set<Product> products) {
        this.products = products;
    }
    public Set<Characteristics> getCharacteristics() {
        return characteristics;
    }
    public void setCharacteristics(Set<Characteristics> characteristics) {
        this.characteristics = characteristics;
    }

    public void addProduct(String prod) {
        if (findProd(prod) == null) {
            Product p = new Product(prod);
            products.add(p);
        }
        else {
            //expection
        }

    }

    public void addCharacteristictProduct(String charac, String prod) {
        Characteristics c = findCharac(charac);
        if ((c != null)) {
            Product p = findProd(prod);
            if (p != null) {
                p.addCharacterisics(c);
                c.addAssociatedProduct(p);

            }
            else {

            }
        }
        else {

        }
    }

    public void removeCharacteristictProduct(String charac, String prod) {
        Characteristics c = findCharac(charac);
        if (c != null) {
            Product p = findProd(prod);
            if (p != null) {
                p.removeCharacterisics(c);
                c.removeAssociatedProduct(p);
            }
        }
    }

    public CtrlProd(Set<Product> products) {

        this.products = products;
    }

    public  boolean productExists(String prod) {
        // Utilizamos un stream para buscar de manera eficiente
        return products.stream()
                .anyMatch(product -> product.getName().equals(prod));
    }

    public  boolean characteristicExists(String charac) {
        return characteristics.stream()
                .anyMatch(characteristic -> characteristic.getName().equals(charac));
    }

    public Product findProd(String prod) {
        return products.stream().
                filter(product -> product.getName().equals(prod)).findFirst().orElse(null);
    }

    public Characteristics findCharac(String charac) {
        return characteristics.stream().
                filter(characteristic -> characteristic.getName().equals(charac)).findFirst().orElse(null);
    }

}
