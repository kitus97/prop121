package com.prop.prop12_1.controller;

import com.prop.prop12_1.model.Characteristics;
import com.prop.prop12_1.model.Product;
import com.prop.prop12_1.model.Shelf;
import com.prop.prop12_1.model.Supermarket;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class CtrlDomain {
    private CtrlSupermarket ctrlSupermarket;
    private CtrlProd ctrlProd;

    public CtrlDomain() {
        ctrlSupermarket = new CtrlSupermarket();
        ctrlProd = new CtrlProd();
        ctrlAlg = new CtrlAlgorithm();
    }

    public boolean addSupermarket(String n) {
        return ctrlSupermarket.addSupermarket(n);
    }

    public boolean removeSupermarket(String n) {
        return ctrlSupermarket.removeSupermarket(n);
    }

    public boolean addShelf(String s, String sh, int size){
        return ctrlSupermarket.createShelf(s, sh, size);
    }

    public boolean removeShelf(String s, String sh){
        return ctrlSupermarket.removeShelf(s, sh);
    }

    public ArrayList<String> getShelves(String s){
        return ctrlSupermarket.getShelves(s);
    }

    public String getShelf(String s, String sh){
        return ctrlSupermarket.getShelf(s, sh);
    }

    //revisar metode
    public boolean addRestriction(String supermarket, String restriction, String shelf, int index){

        boolean exists = ctrlProd.getCharacteristic(restriction);
        if (exists){
            ctrlSupermarket.addRestriction(supermarket, restriction, shelf, index);
            return true;
        }

        return false;
    }

    public boolean removeRestriction(String supermarket, String shelf, int index){
        ctrlSupermarket.removeRestriction(supermarket, shelf, index);
        // mirarlo
        return false;
    }

    public boolean resizeShelf(String supermarket, String shelf, int size){
        return ctrlSupermarket.resizeShelf(supermarket, shelf, size);
    }

    public void addCharacteristic(String characteristicName){
        ctrlProd.addCharacteristic(characteristicName);
    }

    public void removeCharacteristic(String characteristicName) {
        ctrlProd.removeCharacteristic(characteristicName);
    }

    public Map<String,Double> checkProductSimilarities(String productName) {
        return ctrlProd.checkProductSimilarities(productName);
    }

    public void modifySimilarity(String productName1, String productName2, Double newValue) {
        ctrlProd.modifySimilarity(productName1, productName2, newValue);
    }

    public Double checkProductsSimilarity(String productName1, String productName2) {
        return ctrlProd.checkProductsSimilarity(productName1, productName2);
    }

    public void addProduct(String productName){
        ctrlProd.addProduct(productName);
    }

    public Boolean setSimilarities(Double[] similarities) {
        return ctrlProd.setSimilarities(similarities);
    }

    public void addCharacteristicProduct(String characteristicName, String productName) {
        ctrlProd.addCharacteristicProduct(characteristicName, productName);
        //soluciones no validas
    }

    public void removeCharacteristicProduct(String characteristicName, String productName) {
        ctrlProd.removeCharacteristicProduct(characteristicName, productName);
        //soluciones no validas
    }

    public ArrayList<String> listCharacteristics() {
        return ctrlProd.listCharacteristics();
    }

    public ArrayList<String> listProducts() {
        return ctrlProd.listProducts();
    }

    public ArrayList<String> listCharacteristicsProduct(String productName) {
        return ctrlProd.listCharacteristicsProduct(productName);
    }

    public void addProductCatalogue(String supermarket, String catalogueName, String ProductName){
        Product p = ctrlProd.getProduct(ProductName);
        ctrlSupermarket.addProduct(supermarket, catalogueName, p);
    }

    public void removeProductCatalogue(String supermarket, String catalogueName, String ProductName){
        ctrlSupermarket.removeProduct(supermarket, catalogueName, ProductName);
    }

    public ArrayList<String> listProdsCatalogue(String supermarket, String catalogueName){
        return ctrlSupermarket.listProdsCatalogue(supermarket, catalogueName);
    }

    public void addCatalogue(String supermarket, String catalogueName){
        ctrlSupermarket.addCatalogue(supermarket, catalogueName);
    }

    public void removeCatalogue(String supermarket, String catalogueName){
        ctrlSupermarket.removeCatalogue(supermarket, catalogueName);
    }

    public boolean generateSolution(String supermarket, String catalogueName, String shelf, int algorithm, boolean generatedSimilarity){
        ArrayList<Set<String>> sh = ctrlSupermarket.getDistribution(supermarket,shelf);
        ArrayList<Pair<Integer, Set<String>>> pr = ctrlSupermarket.getProds(supermarket, catalogueName);
        ArrayList<Pair<Integer, Set<String>>> sol = ctrlAlg.getSolution(sh, pr, algorithm, generatedSimilarity);
        return true;
    }

    public ArrayList<String> listSolutions(String supermarket){
        return ctrlSupermarket.listSolutions(supermarket);
    }

    public void checkSolution(String supermarket, Strinf solName){
        ctrlSupermarket.getSolution(supermarket, solName);
    }



}
