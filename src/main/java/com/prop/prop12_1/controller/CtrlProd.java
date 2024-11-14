package com.prop.prop12_1.controller;

import com.prop.prop12_1.exceptions.CharacteristicNotFoundException;
import com.prop.prop12_1.exceptions.CharacteristicAlreadyAddedToProductException;
import com.prop.prop12_1.exceptions.RestrictionNotFoundInProductException;
import com.prop.prop12_1.exceptions.RestrictionAlreadyAddedToProductException;
import com.prop.prop12_1.exceptions.CharacteristicNotFoundInProductException;
import com.prop.prop12_1.exceptions.CharacteristicAlreadyAddedException;
import com.prop.prop12_1.exceptions.ProductAlreadyAddedException;
import com.prop.prop12_1.exceptions.ProductNotFoundException;
import com.prop.prop12_1.model.Characteristics;
import com.prop.prop12_1.model.Product;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class CtrlProd {

    private static Map<String, Product> products;
    private static Map<String, Characteristics> characteristics;
    private static List<List<Double>> similarityTable;
    private static Map<Integer,String> mapProductsId;

    public CtrlProd() {
        products = new HashMap<>();
        characteristics = new HashMap<>();
        similarityTable = new ArrayList<>();
        mapProductsId = new HashMap<>();
    }

    public void addCharacteristic(String characteristicName) {
        if (findCharacteristic(characteristicName) == null) {
            Characteristics characteristic = new Characteristics(characteristics.size(), characteristicName);
            characteristics.put(characteristicName, characteristic);
        } else {
            throw new CharacteristicAlreadyAddedException("Characteristic with name '" + characteristicName + "' was already added");
        }
    }

    public void removeCharacteristic(String characteristicName) {
        Characteristics deletedCharacteristic = characteristics.remove(characteristicName);
        if (deletedCharacteristic == null) {
            throw new CharacteristicNotFoundException("Characteristic with name '" + characteristicName + "' was not found");
        }
    }

    public Map<String,Double> checkProductSimilarities(String productName) {
        Product product = products.get(productName);

        if (product == null) {
            throw new ProductNotFoundException("Product with name '" + productName + "' was not found");
        }

        Map <String, Double> similarities = new HashMap<>();

        IntStream.range(0, similarityTable.get(product.getId()).size())
                .forEach(i -> similarities.put(mapProductsId.get(i), similarityTable.get(product.getId()).get(i)));


        return similarities;
    }

    public void modifySimilarity(String productName1, String productName2, Double newValue) {

        Product product1 = products.get(productName1);
        Product product2 = products.get(productName2);
        
        if (product1 == null && product2 == null) {
            throw new ProductNotFoundException("Products with name '" + productName1 + "' and '"
                                                                        + productName2 + "' were not found");
        } else if (product1 == null) {
            throw new ProductNotFoundException("Product with name '" + productName1 + "' was not found");
        } else if (product2 == null) {
            throw new ProductNotFoundException("Product with name'" + productName2 + " was not found");
        }

        similarityTable.get(product1.getId()).set(product2.getId(), newValue);
        similarityTable.get(product2.getId()).set(product1.getId(), newValue);
    }

    public Double checkProductsSimilarity(String productName1, String productName2) {

        Product product1 = products.get(productName1);
        Product product2 = products.get(productName2);

        if (product1 == null && product2 == null) {
            throw new ProductNotFoundException("Products with name '" + productName1 + "' and '"
                    + productName2 + "' were not found");
        } else if (product1 == null) {
            throw new ProductNotFoundException("Product with name '" + productName1 + "' was not found");
        } else if (product2 == null) {
            throw new ProductNotFoundException("Product with name'" + productName2 + " was not found");
        }

        return similarityTable.get(product1.getId()).get(product2.getId());
    }

    public void addProduct(String productName) {

        if (findProduct(productName) == null) {
            Product newProduct = new Product(products.size(), productName);
            products.put(productName, newProduct);
            int size = products.size();
            mapProductsId.put(size-1,productName);
        }
        else {
            throw new ProductAlreadyAddedException("Product with name '" + productName + "' was already added");
        }

    }

    //Pre: We assume that a new product has been added and CtrlProd function is called right after
    public Boolean setSimilarities(Double[] similarities) {

        if (similarities == null) {
            int idx1 = products.size()-1;
            List<Characteristics> characteristics1 = products.get(mapProductsId.get(idx1)).getCharacteristics();
            List<Double> newRow = new ArrayList<>();
            for (int i =0 ; i < idx1; i++) {
                List<Characteristics> characteristics2 = products.get(mapProductsId.get(i)).getCharacteristics();
                double similarity = calculateSimilarity(characteristics1,characteristics2);
                newRow.add(similarity);
                similarityTable.get(i).add(similarity);
            }
            newRow.add(1.0);
            similarityTable.add(newRow);
        }
        else {
            List<Double> newLine = new ArrayList<>(Arrays.asList(similarities));
            newLine.add(0.0);
            similarityTable.add(newLine);
            int j = 0;
            for(List<Double> line : similarityTable) {
                if (line.size() < products.size() && j < similarities.length) {
                    line.add(similarities[j]);
                }
                j++;
            }
        }
        return true;

    }

    private double calculateSimilarity(List<Characteristics> characteristics1, List<Characteristics> characteristics2) {
        List<Object> intersection = new ArrayList<>(characteristics1);
        intersection.retainAll(characteristics2);
        List<Object> union = new ArrayList<>(characteristics1);
        union.addAll(characteristics2);

        return union.isEmpty() ?  0 : (double) intersection.size() / union.size();
    }

    public List<List<Double>> generateSimilarityTable() {
        int productsSize = products.size();
        List<List<Double>> generatedSimilarities = new ArrayList<>();

        for (int i = 0; i < productsSize; i++) {
            List<Double> row = new ArrayList<>(productsSize);
            for (int j = 0; j < productsSize; j++) {
                row.add(0.0);
            }
            generatedSimilarities.add(row);
        }

        List<Product> productsList = new ArrayList<>(products.values());

        for (int i = 0; i < productsSize; i++) {
            Product product1 = productsList.get(i);
            List<Characteristics> characteristics1 = product1.getCharacteristics();
            for (int j = i; j < productsSize; j++) {
                Product product2 = productsList.get(j);
                List<Characteristics> characteristics2 = product2.getCharacteristics();

                double similarity = calculateSimilarity(characteristics1,characteristics2);
                generatedSimilarities.get(product1.getId()).set(product2.getId(), similarity);
                generatedSimilarities.get(product2.getId()).set(product1.getId(), similarity);
            }
        }

        return generatedSimilarities;
    }

    public void addRestrictionProduct(String restrictionName, String productName) {
        Characteristics c = findCharacteristic(restrictionName);
        if ((c != null)) {
            Product p = findProduct(productName);
            if (p != null) {
                if(p.getRestrictions().contains(c)) {
                    throw new RestrictionAlreadyAddedToProductException("Restriction with name '" + restrictionName + "' was already added to product");
                }
                else {
                    p.addRestriction(c);
                    c.addAssociatedProduct(p);
                }

            }
            else {
                throw new ProductNotFoundException("Product with name '" + productName + "' was not found");
            }
        }
        else {
            throw new CharacteristicNotFoundException("Restriction with name '" + restrictionName + "' was not found");
        }
    }

    public void removeRestrictionProduct(String restrictionName, String productName) {
        Characteristics c = findCharacteristic(restrictionName);
        if (c != null) {
            Product p = findProduct(productName);
            if (p != null) {
                if (p.getRestrictions().contains(c)) {
                    p.removeCharacteristic(c);
                    c.removeAssociatedProduct(p);
                }
                else {
                    throw new RestrictionNotFoundInProductException("Restriction with name '" + restrictionName + "' was not found in product");
                }

            }
            else {
                throw new ProductNotFoundException("Product with name '" + productName + "' was not found");
            }
        }
        else {
            throw new CharacteristicNotFoundException("Restriction with name '" + restrictionName + "' was not found");
        }
    }

    public void addCharacteristicProduct(String characteristicName, String productName) {
        Characteristics c = findCharacteristic(characteristicName);
        if ((c != null)) {
            Product p = findProduct(productName);
            if (p != null) {
                if (p.getCharacteristics().contains(c)) {
                    throw new CharacteristicAlreadyAddedToProductException("Characteristic with name '" + characteristicName + "' was already added");
                }
                else {
                    p.addCharacteristic(c);
                    c.addAssociatedProduct(p);
                }

            }
            else {
                throw new ProductNotFoundException("Product with name '" + productName + "' was not found");
            }
        }
        else {
            throw new CharacteristicNotFoundException("Characteristic with name '" + characteristicName + "' was not found");
        }
    }

    public void removeCharacteristicProduct(String characteristicName, String productName) {
        Characteristics c = findCharacteristic(characteristicName);
        if (c != null) {
            Product p = findProduct(productName);
            if (p != null) {
                if (p.getCharacteristics().contains(c)) {
                    p.removeCharacteristic(c);
                    c.removeAssociatedProduct(p);
                }
                else {
                    throw new CharacteristicNotFoundInProductException("Characteristic with name '" + characteristicName + "' was not found in product");
                }

            }
            else {
                throw new ProductNotFoundException("Product with name '" + productName + "' was not found");
            }
        }
        else {
            throw new CharacteristicNotFoundException("Characteristic with name '" + characteristicName + "' was not found");
        }
    }

    private Boolean productExists(String productName) {
        return products.containsKey(productName);
    }

    private Boolean characteristicExists(String characteristicName) {
        return characteristics.containsKey(characteristicName);
    }

    public Product findProduct(String productName) {
        return products.get(productName);
    }

    private Characteristics findCharacteristic(String characteristicName) {
        return characteristics.get(characteristicName);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products.values());
    }

    public List<String> listCharacteristics() {
        return characteristics.values().stream()
                                       .map(Characteristics::toString)
                                       .collect(Collectors.toList());
    }

    public List<String> listProducts() {
        return products.values().stream()
                                .map(Product::getName)
                                .collect(Collectors.toList());
    }

    public void setProducts(Map<String, Product> products) {
        CtrlProd.products = products;
    }

    public Map<String, Characteristics> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(Map<String, Characteristics> characteristics) {
        CtrlProd.characteristics = characteristics;
    }

    public Product findProduct(String productName) {
        return products.get(productName);
    }

    public List<List<Double>> getSimilarityTable() {
        return similarityTable;
    }

    public List<String> getRestrictionsProducts(String productName) {
        List<String> nameRestrictions =  new ArrayList<>();
        Product p = findProduct(productName);
        if (p != null) {
            return p.getRestrictions().stream()
                    .map(Characteristics::getName)
                    .collect(Collectors.toList());
        }
        else {
            throw new ProductNotFoundException("Product with name '" + productName + "' was not found");
        }
    }

    public List<String> getCharacteristicsProducts(String productName) {
        List<String> nameCharacteristics =  new ArrayList<>();
        Product p = findProduct(productName);
        if (p != null) {
            return p.getCharacteristics().stream()
                    .map(Characteristics::getName)
                    .collect(Collectors.toList());
        }
        else {
            throw new ProductNotFoundException("Product with name '" + productName + "' was not found");
        }
    }

    public void setSimilarityTable(List<List<Double>> arraySimilarityTable) {
        similarityTable = arraySimilarityTable;
    }

    public Map<Integer, String> getMapProductsId() {
        return mapProductsId;
    }

    public void setMapProductsId(Map<Integer, String> mapProductsId) {
        CtrlProd.mapProductsId = mapProductsId;
    }

    public static boolean findCharacteristicByName(List<Characteristics> characteristics, String characteristicName) {
        return characteristics.stream()
                .anyMatch(characteristic -> characteristic.getName().equalsIgnoreCase(characteristicName));
    }
}
