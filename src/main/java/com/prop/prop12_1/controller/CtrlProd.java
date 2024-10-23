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
        if (findCharacteristic(characteristicName) == null) {
            Characteristics c = new Characteristics(characteristics.size(), characteristicName);
            this.characteristics.add(c);
        }
    }

    public void removeCharacteristic(String characteristicName) {
        Characteristics c = findCharacteristic(characteristicName);
        if (findCharacteristic(characteristicName) != null) {
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
        this.similarityTable.get(id2).set(id1, newValue);
    }

    public Double checkProductsSimilarity(String productName1, String productName2) {
        int id1 = mapProductsName.get(productName1);
        int id2 = mapProductsName.get(productName2);

        return similarityTable.get(id1).get(id2);
    }

    public Boolean addProduct(String productName) {

        if (findProduct(productName) == null) {

            Product p = new Product(productName);
            products.add(p);
            int tam = products.size();
            mapProductsName.put(productName,tam-1);
            mapProductsId.put(tam-1,productName);

            return true;

        }
        else {
            return false;
        }

    }
    //Pre: We assume that a new product has been added and this function is called right after
    public Boolean setSimilarities(double[] similarities) {

        if (similarities.length == 0) {
            ArrayList<Double> newLine = new ArrayList<>();
            for (int i = 0; i < products.size(); i++) {
                newLine.add(0.0);
            }
            similarityTable.add(newLine);
            for(ArrayList<Double> line : similarityTable) {
                while (line.size() < products.size()) {
                    line.add(0.0);
                }
            }
        }
        else {
            ArrayList<Double> newLine = new ArrayList<>();
            for (int i = 0; i < products.size(); i++) {
                if (i != similarities.length-1) {
                    newLine.add(similarities[i]);
                }
                else {
                    newLine.add(0.0);
                }
            }
            similarityTable.add(newLine);
            int j = 0;
            for(ArrayList<Double> line : similarityTable) {
                while (line.size() < products.size()) {
                    if (j != similarities.length-1) {
                        newLine.add(similarities[j]);
                    }
                    else {
                        line.add(0.0);
                    }
                    j++;
                }
            }
        }
        return true;


    }

    public Boolean addCharacteristictProduct(String characteristicName, String productName) {
        Characteristics c = findCharacteristic(characteristicName);
        if ((c != null)) {
            Product p = findProduct(productName);
            if (p != null) {
                p.addCharacterisics(c);
                c.addAssociatedProduct(p);
                return true;

            }
            else {
                return false;
            }
        }
        else {
            return null;
        }
    }

    public Boolean removeCharacteristictProduct(String characteristicName, String productName) {
        Characteristics c = findCharacteristic(characteristicName);
        if (c != null) {
            Product p = findProduct(productName);
            if (p != null) {
                p.removeCharacterisics(c);
                c.removeAssociatedProduct(p);
                return true;
            }
            else return null;
        }
        else return false;
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

    private boolean characteristicExists(String characteristicName) {
        return characteristics.stream()
                .anyMatch(characteristic -> characteristic.getName().equals(characteristicName));
    }

    private Product findProduct(String productName) {
        return products.stream().
                filter(product -> product.getName().equals(productName)).findFirst().orElse(null);
    }

    private Characteristics findCharacteristic(String characteristicName) {
        return characteristics.stream().
                filter(characteristic -> characteristic.getName().equals(characteristicName)).findFirst().orElse(null);
    }

    public Set<Product> getProducts() {
        return products;
    }

    public ArrayList<String> listProducts() {
        return products.stream().map(Product::getName).collect(Collectors.toCollection(ArrayList::new));
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
