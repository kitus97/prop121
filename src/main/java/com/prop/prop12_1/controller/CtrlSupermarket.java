package com.prop.prop12_1.controller;

import com.prop.prop12_1.model.Supermarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CtrlSupermarket {

    private static HashMap<String, Supermarket> supermarkets = new HashMap<>();

    public CtrlSupermarket() {

    }

    /* Returns true if the supermarket is created succesfully,
    returns false otherwise (the supermarket already existed)*/
    public boolean addSupermarket(String n){
        if(supermarkets.containsKey(n)){
            return false;
        }
        else{
            supermarkets.put(n, new Supermarket(n));
            return true;
        }
    }

    public boolean removeSupermarket(String n){
        return supermarkets.remove(n) != null;
    }

    public List<String> getSupermarkets(){
        List<Supermarket> temp = new ArrayList<>(supermarkets.values());
        List<String> ret = new ArrayList<>();
        for(Supermarket s : temp){
            ret.add(s.getName());
        }

        return ret;
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
        if(m.getShelf(sh) != null){
            return m.getShelf(sh).getName();
        }
        else return null;
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

    public String getRequirements(){
        String s = "To generate a solution you need to input, in order: \n" +
                "-The name of the solution you want to create\n" +
                "-The name of the shelf to be used\n" +
                "-The name of the catalogue to be used\n" +
                "-1 or 0 to select the heuristic (1= generated, 0= predefined)\n" +
                "-1 or 0 to select the algorithm (1= BackTracking, 0= HillClimbing\n";
        return s;
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






}
