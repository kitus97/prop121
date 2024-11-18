package com.prop.prop12_1.controller;

import com.prop.prop12_1.exceptions.SupermarketAlreadyAddedException;
import com.prop.prop12_1.model.Catalogue;
import com.prop.prop12_1.model.Supermarket;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class CtrlSupermarket {

    private static Map<String, Supermarket> supermarkets = new HashMap<>();

    public CtrlSupermarket() {

    }

    /* Returns true if the supermarket is created succesfully,
    returns false otherwise (the supermarket already existed)*/
    public void addSupermarket(String n){
        if(supermarkets.put(n, new Supermarket(n)) != null) throw new SupermarketAlreadyAddedException("Supermarket with name '" + n + "' already exists");
    }

    public void removeSupermarket(String n){
        if (supermarkets.remove(n) == null) throw new NoSuchElementException("Error: supermarket with name '" + n + "' not found");
    }

    public List<String> getSupermarkets(){
        List<Supermarket> temp = new ArrayList<>(supermarkets.values());
        List<String> ret = new ArrayList<>();
        for(Supermarket s : temp){
            ret.add(s.getName());
        }

        return ret;
    }

    public boolean existsSupermarket(String supermarket) {
        return supermarkets.containsKey(supermarket);
    }

    /*Pre: supermarket exists*/
    public void removeShelf(String supermarket, String shelf){
        Supermarket m = supermarkets.get(supermarket);
        m.deleteShelf(shelf) ;
    }

    /*Pre: s exists*/
    public void createShelf(String supermarket, String shelf, int size){
        Supermarket m = supermarkets.get(supermarket);
        m.addShelf(shelf, size);
    }

    /*Pre: s exists*/
    public List<String> getShelves(String supermarket){
        Supermarket m = supermarkets.get(supermarket);
        return m.getShelves();
    }

    /*Returns null if sh doesnt exist.*/
    public String getShelf(String supermarket, String sh){
        Supermarket m = supermarkets.get(supermarket);
        return m.getShelf(sh);
    }

    /*restriction es el nombre de una caracteristica existente, se comprueba quee existe en el ctrl domini*/
    public void addRestriction(String supermarket, String shelf, String restriction, int index){
        Supermarket m = supermarkets.get(supermarket);
        m.addRestriction(shelf, restriction, index);

    }

    public void removeRestriction(String supermarket, String shelf, int index){
        Supermarket m = supermarkets.get(supermarket);
        m.deleteRestrictions(shelf, index);
    }

    public void resizeShelf(String supermarket, String shelf, int size){
        Supermarket m = supermarkets.get(supermarket);
        m.resizeShelf(shelf, size);
    }

    public void generateSolution(String supermarket, String name, String shelf, String catalog, boolean heuristic, int algorithm){
        Supermarket m = supermarkets.get(supermarket);
        m.generateSolution(name, shelf, catalog, heuristic, algorithm);
    }

    public void addProductToCatalogue(String supermarket, String product, String catalog){
        supermarkets.get(supermarket).addProductToCatalogue(product, catalog);
    }

    public void removeProductFromCatalogue(String supermarket, String product, String catalog){
        supermarkets.get(supermarket).removeProductFromCatalogue(product, catalog);
    }

    public void invalidateProductSolution(String product){
        for(Supermarket m : supermarkets.values()){
            m.invalidateProductSolution(product);
        }
    }

    public String getSolution(String supermarket, String solution){
        return supermarkets.get(supermarket).getSolution(solution);
    }

    public List<String> getSolutions(String supermarket){
        return supermarkets.get(supermarket).getSolutions();
    }

    public void deleteCatalog(String supermarket, String catalog){
        supermarkets.get(supermarket).deleteCatalogue(catalog);
    }

    public void deleteSolution(String supermarket, String solution){
        supermarkets.get(supermarket).deleteSolution(solution);
    }

    public void addCatalog(String supermarket, String catalog){
        supermarkets.get(supermarket).addCatalogue(catalog);
    }

    public void deleteSolutionProduct(String supermarket, String solution, int index){
        supermarkets.get(supermarket).deleteSolutionProduct(solution, index);
    }

    public void addSolutionProduct(String supermarket, String solution, String product, int index){
        supermarkets.get(supermarket).addSolutionProduct(solution, product, index);
    }

    public void changeSolutionProducts(String supermarket, int indx1, int indx2, String solution){
        supermarkets.get(supermarket).changeSolutionProducts(indx1, indx2, solution);
    }

    public void updateSolutionMarks(String product1, List<List<Double>> similarityTable, Boolean generated){
        for(Supermarket m : supermarkets.values()){
            m.updateSolutionMark(product1, similarityTable, generated);
        }
    }

    public double checkDeleteSolutionProduct(String supermarket, String solution, int index){
        return supermarkets.get(supermarket).checkDeleteSolutionProduct(solution, index);
    }

    public double checkAddSolutionProduct(String supermarket, String solution, String product, int index){
        return supermarkets.get(supermarket).checkAddSolutionProduct(solution, product, index);
    }

    public double checkSwapSolution(String supermarket, String solution, int index1, int index2){
        return supermarkets.get(supermarket).checkSwapSolution(solution, index1, index2);
    }

    public List<String> listProdsCatalogue(String supermarket, String catalogueName) {
        return supermarkets.get(supermarket).listProdsCatalogue(catalogueName);
    }

    public List<String> listCatalogues(String supermarketName) {
        return supermarkets.get(supermarketName).getCatalogs().stream()
                                                              .map(Catalogue::getName)
                                                              .toList();
    }
}
