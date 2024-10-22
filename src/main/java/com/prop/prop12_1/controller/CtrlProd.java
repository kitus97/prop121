package com.prop.prop12_1.controller;

import com.prop.prop12_1.model.Characteristics;
import com.prop.prop12_1.model.Product;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.*;
import java.util.stream.Collectors;


public class CtrlProd {

    private Set<Product> products;
    private Set<Characteristics> characteristics;
    private ArrayList<ArrayList<Double>> similarityTable;
    Map<String,Integer> mapProductsName;
    Map<Integer,String> mapProductsId;

    public CtrlProd() {
        this.products = new HashSet<>();
        this.characteristics = new HashSet<>();
        this.similarityTable = new ArrayList<>();
        this.mapProductsName = new HashMap<>();
        this.mapProductsId = new HashMap<>();
    }

    public void addCharacteristic(String characteristicName) {
        if (findCharac(characteristicName) == null) {
            Characteristics c = new Characteristics(characteristics.size(), characteristicName);
            this.characteristics.add(c);
        }
    }

    public void removeCharacteristic(String characteristicName) {
        Characteristics c = findCharac(characteristicName);
        if (findCharac(characteristicName) != null) {
            this.characteristics.remove(c);
        } else {
            //exception
        }
    }

    public Map<String,Double> checkProductSimilarities(String productName) {
        int id = mapProductsName.get(productName);
        Map <String, Double> similarities = new HashMap<>();

        int size = similarityTable.get(id).size();
        for (int i = 0; i < size; i++) {
            similarities.put(mapProductsId.get(i), similarityTable.get(id).get(i));
        }

        return similarities;
    }

    public void modifySimilarity(String nameProduct1, String nameProduct2, Double newValue) {
        int id1 = mapProductsName.get(nameProduct1);
        int id2 = mapProductsName.get(nameProduct2);

        this.similarityTable.get(id1).set(id2, newValue);
    }

    public Double checkProductsSimilarity(String productName1, String productName2) {
        int id1 = mapProductsName.get(productName1);
        int id2 = mapProductsName.get(productName2);

        return similarityTable.get(id1).get(id2);
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

    public ArrayList<String> listCharacteristics() {
        return characteristics.stream().map(Characteristics::getName)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private  boolean productExists(String prod) {
        // Utilizamos un stream para buscar de manera eficiente
        return products.stream()
                .anyMatch(product -> product.getName().equals(prod));
    }

    private  boolean characteristicExists(String charac) {
        return characteristics.stream()
                .anyMatch(characteristic -> characteristic.getName().equals(charac));
    }

    private Product findProd(String prod) {
        return products.stream().
                filter(product -> product.getName().equals(prod)).findFirst().orElse(null);
    }

    private Characteristics findCharac(String charac) {
        return characteristics.stream().
                filter(characteristic -> characteristic.getName().equals(charac)).findFirst().orElse(null);
    }

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

}
