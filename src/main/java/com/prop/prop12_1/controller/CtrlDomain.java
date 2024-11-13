package com.prop.prop12_1.controller;

import com.prop.prop12_1.model.Characteristics;
import com.prop.prop12_1.model.Product;
import com.prop.prop12_1.model.Shelf;
import com.prop.prop12_1.model.Supermarket;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CtrlDomain {
   // private CtrlSupermarket ctrlSupermarket;
    private CtrlProd ctrlProd;

    public CtrlDomain() {
       // ctrlSupermarket = new CtrlSupermarket();
        ctrlProd = new CtrlProd();
    }

//    public boolean addSupermarket(String n) {
//        return ctrlSupermarket.addSupermarket(n);
//    }
//
//    public boolean removeSupermarket(String n) {
//        return ctrlSupermarket.removeSupermarket(n);
//    }
//
//    public boolean addShelf(String s, String sh, int size){
//        return ctrlSupermarket.createShelf(s, sh, size);
//    }
//
//    public boolean removeShelf(String s, String sh){
//        return ctrlSupermarket.removeShelf(s, sh);
//    }
//
//    public ArrayList<String> getShelves(String s){
//        return ctrlSupermarket.getShelves(s);
//    }
//
//    public String getShelf(String s, String sh){
//        return ctrlSupermarket.getShelf(s, sh);
//    }

    public boolean addRestriction(String supermarket, String restriction, String shelf, int index){
        /*if (existeix caracteristica){
            ctrlSupermarket.addRestriction(supermarket, restriction, shelf, index);
        }*/

        return false;
    }

//    public boolean removeRestriction(String supermarket, String shelf, int index){
//        ctrlSupermarket.removeRestriction(supermarket, shelf, index);
//        // mirarlo
//        return false;
//    }

//    public boolean resizeShelf(String supermarket, String shelf, int size){
//        return ctrlSupermarket.resizeShelf(supermarket, shelf, size);
//    }

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

    public Product getProduct(String productName) {
        return ctrlProd.findProduct(productName);
    }

    public ArrayList<String> listCharacteristicsProduct(String productName) {
        //return ctrlProd.listCharacteristicsProduct(productName);
        return null;
    }

    //addProductCatalogue(String supermarket, String catalogueName, String ProductName){}
    //removeProductCatalogue(String supermarket, String catalogueName, String ProductName)
    //listProdsCatalogue(String supermarket, String catalogueName)
    //addCatalogue(String supermarket, String catalogueName)
    //removeCatalogue(String supermarket, String catalogueName)

    //generateSolution, listSolution, checkSolution,,, todo lo relacionado con solucion



}