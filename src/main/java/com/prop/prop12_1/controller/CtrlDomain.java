package com.prop.prop12_1.controller;

import com.prop.prop12_1.model.Characteristics;
import com.prop.prop12_1.model.Product;
import com.prop.prop12_1.model.Shelf;
import com.prop.prop12_1.model.Supermarket;

import java.util.List;
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

    public void addShelf(String s, String sh, int size){
        ctrlSupermarket.createShelf(s, sh, size);
    }

    public void removeShelf(String s, String sh){
        ctrlSupermarket.removeShelf(s, sh);
    }

    public List<String> getShelves(String s){
        return ctrlSupermarket.getShelves(s);
    }

    public String getShelf(String s, String sh){
        return ctrlSupermarket.getShelf(s, sh);
    }

    //revisar metode
    public void addRestriction(String supermarket, String restriction, String shelf, int index){
        if (ctrlProd.findCharacteristic(restriction) != null){
            ctrlSupermarket.addRestriction(supermarket, restriction, shelf, index);
        }
    }

    public void removeRestriction(String supermarket, String shelf, int index){
        ctrlSupermarket.removeRestriction(supermarket, shelf, index);
        // mirar tema validez
    }

    public void resizeShelf(String supermarket, String shelf, int size){
        ctrlSupermarket.resizeShelf(supermarket, shelf, size);
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

    public List<String> listCharacteristics() {
        return ctrlProd.listCharacteristics();
    }

    public List<String> listProducts() {
        return ctrlProd.listProducts();
    }

    public List<String> listCharacteristicsProduct(String productName) {
        return ctrlProd.getCharacteristicsProducts(productName);
    }
    //nuevo
    public void addProductToCatalogue(String supermarket, String catalogueName, String productName){
        ctrlSupermarket.addProductToCatalogue(supermarket, productName, catalogueName);
    }
    //nuevo
    public void removeProductCatalogue(String supermarket, String catalogueName, String productName){
        ctrlSupermarket.removeProductFromCatalogue(supermarket, productName, catalogueName);
    }
    //nuevo
    public List<String> listProdsCatalogue(String supermarket, String catalogueName){
        return ctrlSupermarket.listProdsCatalogue(supermarket, catalogueName);
    }
    //nuevo
    public void addCatalogue(String supermarket, String catalogueName){
        ctrlSupermarket.addCatalogue(supermarket, catalogueName);
    }
    //nuevo
    public boolean removeCatalogue(String supermarket, String catalogueName){
        return ctrlSupermarket.removeCatalogue(supermarket, catalogueName);
    }

    public void generateSolution(String supermarket, String solName, String shelfName, String catalogueName, int algorithm, boolean generatedSimilarity){
        ctrlSupermarket.generateSolution(supermarket, solName, shelfName, catalogueName, generatedSimilarity, algorithm);
        //no se si queremos que cuando se genere, automaticamente imprima la solucion
    }

    public List<String> listSolutions(String supermarket){
        return ctrlSupermarket.listSolutions(supermarket);
    }

    public void checkSolution(String supermarket, String solName){
        ctrlSupermarket.getSolution(supermarket, solName);
    }

    public List<String> listSupermarkets(){
        return ctrlSupermarket.getSupermarkets();
    }



}
