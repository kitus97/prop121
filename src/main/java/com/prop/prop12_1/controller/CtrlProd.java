package com.prop.prop12_1.controller;

import com.prop.prop12_1.model.Characteristics;
import com.prop.prop12_1.model.Product;

import java.util.*;
import java.util.stream.Collectors;


public class CtrlProd {

    private Map<String, Product> products;
    private Map<String, Characteristics> characteristics;
    private ArrayList<ArrayList<Double>> similarityTable;
    Map<String,Integer> mapProductsName;
    Map<Integer,String> mapProductsId;

    public CtrlProd() {
        this.products = new HashMap<>();
        this.characteristics = new HashMap<>();
        this.similarityTable = new ArrayList<>();
        this.mapProductsName = new HashMap<>();
        this.mapProductsId = new HashMap<>();
    }

    public void addCharacteristic(String characteristicName) {
        if (findCharacteristic(characteristicName) == null) {
            Characteristics characteristic = new Characteristics(this.characteristics.size(), characteristicName);
            this.characteristics.put(characteristicName, characteristic);
        }
    }

    public void removeCharacteristic(String characteristicName) {
        Characteristics deletedCharacteristic = this.characteristics.remove(characteristicName);
        if (deletedCharacteristic == null) {
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
            Product newProduct = new Product(productName);
            products.put(productName, newProduct);
            int size = products.size();
            mapProductsName.put(productName,size-1);
            mapProductsId.put(size-1,productName);

            return true;
        }
        else {
            return false;
        }

    }
    //Pre: We assume that a new product has been added and this function is called right after
    public Boolean setSimilarities(Double[] similarities) {

        if (similarities.length == 0) {
            ArrayList<Double> newLine = new ArrayList<>(Collections.nCopies(products.size(), 0.0));
            similarityTable.add(newLine);
            for(ArrayList<Double> line : similarityTable) {
                if (line.size() < products.size()) {
                    line.add(0.0);
                }
            }
        }
        else {
            ArrayList<Double> newLine = new ArrayList<>(Arrays.asList(similarities));
            newLine.add(0.0);
            similarityTable.add(newLine);
            int j = 0;
            for(ArrayList<Double> line : similarityTable) {
                if (line.size() < products.size() && j < similarities.length) {
                        line.add(similarities[j]);
                }
                j++;
            }
        }
        return true;

    }

    public Boolean addCharacteristicProduct(String characteristicName, String productName) {
        Characteristics c = findCharacteristic(characteristicName);
        if ((c != null)) {
            Product p = findProduct(productName);
            if (p != null) {
                p.addCharacteristic(c);
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

    public Boolean removeCharacteristicProduct(String characteristicName, String productName) {
        Characteristics c = findCharacteristic(characteristicName);
        if (c != null) {
            Product p = findProduct(productName);
            if (p != null) {
                p.removeCharacteristic(c);
                c.removeAssociatedProduct(p);
                return true;
            }
            else return null;
        }
        else return false;
    }

    public ArrayList<String> listCharacteristics() {
        return characteristics.values().stream().map(Characteristics::getName)
                                .collect(Collectors.toCollection(ArrayList::new));
    }

    private Boolean productExists(String productName) {
        return products.containsKey(productName);
    }

    private Boolean characteristicExists(String characteristicName) {
        return characteristics.containsKey(characteristicName);
    }

    private Product findProduct(String productName) {
        return products.get(productName);
    }

    private Characteristics findCharacteristic(String characteristicName) {
        return characteristics.get(characteristicName);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products.values());
    }

    public ArrayList<String> listProducts() {
        return products.values().stream().map(Product::getName)
                            .collect(Collectors.toCollection(ArrayList::new));
    }

    public void setProducts(Map<String, Product> products) {
        this.products = products;
    }

    public Map<String, Characteristics> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(Map<String, Characteristics> characteristics) {
        this.characteristics = characteristics;
    }

}
