package com.prop.prop12_1.controller;

import com.prop.prop12_1.model.Characteristics;
import com.prop.prop12_1.model.Product;
import com.prop.prop12_1.model.Shelf;
import com.prop.prop12_1.model.Supermarket;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CtrlDomain {
    private CtrlSupermarket ctrlSupermarket;
    private CtrlProd ctrlProd;
    private CtrlAlgorithm ctrlAlg;

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
    public void addRestrictionToShelf(String supermarket, String restriction, String shelf, int index){
        if (ctrlProd.findCharacteristic(restriction) != null){
            ctrlSupermarket.addRestriction(supermarket, restriction, shelf, index);
        }
    }

    public void removeRestrictionsFromShelf(String supermarket, String shelf, int index){
        ctrlSupermarket.removeRestriction(supermarket, shelf, index);
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
        ctrlSupermarket.invalidateProductSolution(productName);
    }

    public void removeCharacteristicProduct(String characteristicName, String productName) {
        ctrlProd.removeCharacteristicProduct(characteristicName, productName);
        ctrlSupermarket.invalidateProductSolution(productName);
    }

    public void addRestrictionProduct(String restrictionName, String productName) {
        ctrlProd.addRestrictionProduct(restrictionName, productName);
        ctrlSupermarket.invalidateProductSolution(productName);
    }

    public void removeRestrictionProduct(String restrictionName, String productName) {
        ctrlProd.removeRestrictionProduct(restrictionName, productName);
        ctrlSupermarket.invalidateProductSolution(productName);
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

    //enlloc de set list millor?
    public Set<String> listRestrictionsProduct(String productName) {
        return ctrlProd.getRestrictionsProducts(productName);
    }

    public void addProductToCatalogue(String supermarket, String catalogueName, String productName){
        ctrlSupermarket.addProductToCatalogue(supermarket, productName, catalogueName);
    }

    public void removeProductCatalogue(String supermarket, String catalogueName, String productName){
        ctrlSupermarket.removeProductFromCatalogue(supermarket, productName, catalogueName);

    }

    public List<String> listProdsCatalogue(String supermarket, String catalogueName){
        return ctrlSupermarket.listProdsCatalogue(supermarket, catalogueName);
    }

    public void addCatalogue(String supermarket, String catalogueName){
        ctrlSupermarket.addCatalog(supermarket, catalogueName);
    }

    public void removeCatalogue(String supermarket, String catalogueName){
        ctrlSupermarket.deleteCatalog(supermarket, catalogueName);
    }

    public void generateSolution(String supermarket, String solName, String shelfName, String catalogueName, int algorithm, boolean generatedSimilarity){
        ctrlSupermarket.generateSolution(supermarket, solName, shelfName, catalogueName, generatedSimilarity, algorithm);
        //no se si queremos que cuando se genere, automaticamente imprima la solucion
    }

    public List<String> listSolutions(String supermarket){
        return ctrlSupermarket.getSolutions(supermarket);
    }

    public String checkSolution(String supermarket, String solName){
        return ctrlSupermarket.getSolution(supermarket, solName);
    }

    public List<String> listSupermarkets(){
        return ctrlSupermarket.getSupermarkets();
    }

    public void deleteSolution(String supermarket, String solName){
        ctrlSupermarket.deleteSolution(supermarket, solName);
    }

    public void deleteSolutionProduct(String supermarket, String solution, int index){
        ctrlSupermarket.deleteSolutionProduct(supermarket, solution, index);
    }

    public void addSolutionProduct(String supermarket, String solution, String product, int index){
        ctrlSupermarket.addSolutionProduct(supermarket, solution, product, index);
    }

    public void changeSolutionProducts(String supermarket, int indx1, int indx2, String solution){
        ctrlSupermarket.changeSolutionProducts(supermarket, indx1, indx2, solution);
    }



}
