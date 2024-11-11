package com.prop.prop12_1.controller;

import com.prop.prop12_1.exceptions.CharacteristicNotFoundException;
import com.prop.prop12_1.exceptions.CharacteristicAlreadyAddedException;
import com.prop.prop12_1.exceptions.ProductAlreadyAddedException;
import com.prop.prop12_1.exceptions.ProductNotFoundException;
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
        } else {
            throw new CharacteristicAlreadyAddedException("Characteristic with name '" + characteristicName + "' was already added");
        }
    }

    public void removeCharacteristic(String characteristicName) {
        Characteristics deletedCharacteristic = this.characteristics.remove(characteristicName);
        if (deletedCharacteristic == null) {
            throw new CharacteristicNotFoundException("Characteristic with name '" + characteristicName + "' was not found");
        }
    }

    public Map<String,Double> checkProductSimilarities(String productName) {
        int id = mapProductsName.getOrDefault(productName, -1);

        if (id == -1) {
            throw new ProductNotFoundException("Product with name '" + productName + "' was not found");
        }

        Map <String, Double> similarities = new HashMap<>();

        int size = similarityTable.get(id).size();
        for (int i = 0; i < size; i++) {
            similarities.put(mapProductsId.get(i), similarityTable.get(id).get(i));
        }

        return similarities;
    }

    public void modifySimilarity(String productName1, String productName2, Double newValue) {
        int id1 = mapProductsName.getOrDefault(productName1,-1);
        int id2 = mapProductsName.getOrDefault(productName2,-1);
        
        if (id1 == -1 && id2 == -1) {
            throw new ProductNotFoundException("Products with name '" + productName1 + "' and '"
                                                                        + productName2 + "' were not found");
        } else if (id1 == -1) {
            throw new ProductNotFoundException("Product with name '" + productName1 + "' was not found");
        } else if (id2 == -1) {
            throw new ProductNotFoundException("Product with name'" + productName2 + " was not found");
        }

        this.similarityTable.get(id1).set(id2, newValue);
        this.similarityTable.get(id2).set(id1, newValue);
    }

    public Double checkProductsSimilarity(String productName1, String productName2) {
        int id1 = mapProductsName.getOrDefault(productName1,-1);
        int id2 = mapProductsName.getOrDefault(productName2,-1);

        if (id1 == -1 && id2 == -1) {
            throw new ProductNotFoundException("Products with name '" + productName1 + "' and '"
                    + productName2 + "' were not found");
        } else if (id1 == -1) {
            throw new ProductNotFoundException("Product with name '" + productName1 + "' was not found");
        } else if (id2 == -1) {
            throw new ProductNotFoundException("Product with name'" + productName2 + " was not found");
        }

        return similarityTable.get(id1).get(id2);
    }

    public void addProduct(String productName) {

        if (findProduct(productName) == null) {
            Product newProduct = new Product(productName);
            products.put(productName, newProduct);
            int size = products.size();
            mapProductsName.put(productName,size-1);
            mapProductsId.put(size-1,productName);
        }
        else {
            throw new ProductAlreadyAddedException("Product with name '" + productName + "' was already added");
        }

    }

    //Pre: We assume that a new product has been added and this function is called right after
    public Boolean setSimilarities(Double[] similarities) {

        if (similarities == null) {
            int idx1 = products.size()-1;
            Set<Characteristics> characteristics1 = products.get(mapProductsId.get(idx1)).getCharacteristics();
            ArrayList<Double> newRow = new ArrayList<>();
            for (int i =0 ; i < idx1; i++) {
                Set<Characteristics> characteristics2 = products.get(mapProductsId.get(i)).getCharacteristics();
                double similarity = calculateSimilarity(characteristics1,characteristics2);
                newRow.add(similarity);
                similarityTable.get(i).add(similarity);


            }
            newRow.add(1.0);
            similarityTable.add(newRow);

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

    private double calculateSimilarity(Set<Characteristics> characteristics1, Set<Characteristics> characteristics2) {
        Set<Object> intersection = new HashSet<>(characteristics1);
        intersection.retainAll(characteristics2);
        Set<Object> union = new HashSet<>(characteristics1);
        union.addAll(characteristics2);

        return union.isEmpty() ?  0 : (double) intersection.size() / union.size();
    }

    public ArrayList<ArrayList<Double>> generateSimilarityTable() {
        int size = products.size();
        ArrayList<ArrayList<Double>> generatedSimilarities = new ArrayList<>(size);
        for (Map.Entry<String, Product> entry : products.entrySet()) {
            for (Map.Entry<String, Product> entry2 : products.entrySet()) {
                int idx1 = mapProductsName.get(entry.getKey());
                int idx2 = mapProductsName.get(entry2.getKey());

                Set<Characteristics> characteristics1 = entry.getValue().getCharacteristics();
                Set<Characteristics> characteristics2 = entry2.getValue().getCharacteristics();
                double similarity = calculateSimilarity(characteristics1,characteristics2);
                generatedSimilarities.get(idx1).add(idx2, similarity);
            }
        }
        return generatedSimilarities;

    }

    public void addRestrictionProduct(String restrictionName, String productName) {
        Characteristics c = findCharacteristic(restrictionName);
        if ((c != null)) {
            Product p = findProduct(productName);
            if (p != null) {
                p.addRestriction(c);
                c.addAssociatedProduct(p);
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
                p.removeCharacteristic(c);
                c.removeAssociatedProduct(p);
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
                p.addCharacteristic(c);
                c.addAssociatedProduct(p);
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
                p.removeCharacteristic(c);
                c.removeAssociatedProduct(p);
            }
            else {
                throw new ProductNotFoundException("Product with name '" + productName + "' was not found");
            }
        }
        else {
            throw new CharacteristicNotFoundException("Characteristic with name '" + characteristicName + "' was not found");
        }
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

    public ArrayList<ArrayList<Double>> getSimilarityTable() {
        return similarityTable;
    }

    public Set<String> getRestrictionsProducts(String productName) {
        Set<String> nameRestrictions =  new HashSet<>();
        Product p = findProduct(productName);
        if (p != null) {
            Set<Characteristics> characteristics1 = p.getRestrictions();
            for (Characteristics characteristics : characteristics1) {
                nameRestrictions.add(characteristics.getName());
            }
            return nameRestrictions;
        }
        else {
            throw new ProductNotFoundException("Product with name '" + productName + "' was not found");
        }
    }

    public Set<String> getCharacteristicsProducts(String productName) {
        Set<String> nameCharacteristics =  new HashSet<>();
        Product p = findProduct(productName);
        if (p != null) {
            Set<Characteristics> characteristics1 = p.getRestrictions();
            for (Characteristics characteristics : characteristics1) {
                nameCharacteristics.add(characteristics.getName());
            }
            return nameCharacteristics;
        }
        else {
            throw new ProductNotFoundException("Product with name '" + productName + "' was not found");
        }
    }

    public void setSimilarityTable(ArrayList<ArrayList<Double>> similarityTable) {
        this.similarityTable = similarityTable;
    }

    public Map<String, Integer> getMapProductsName() {
        return mapProductsName;
    }

    public void setMapProductsName(Map<String, Integer> mapProductsName) {
        this.mapProductsName = mapProductsName;
    }

    public Map<Integer, String> getMapProductsId() {
        return mapProductsId;
    }

    public void setMapProductsId(Map<Integer, String> mapProductsId) {
        this.mapProductsId = mapProductsId;
    }
}
