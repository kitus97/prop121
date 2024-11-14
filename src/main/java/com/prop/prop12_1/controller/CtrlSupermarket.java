package com.prop.prop12_1.controller;

import com.prop.prop12_1.model.Shelf;
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
    public boolean removeShelf(String supermarket, String shelf){
        Supermarket m = supermarkets.get(supermarket);
        return m.deleteShelf(shelf);
    }

    /*Pre: s exists*/
    public void createShelf(String s, String sh, int size){
        Supermarket m = supermarkets.get(s);
        Shelf shelf = new Shelf(sh, size);
        m.addShelf(shelf);
    }

    /*Pre: s exists*/
    public List<String> getShelves(String s){
        Supermarket m = supermarkets.get(s);
        List<Shelf> x = m.getShelves();
        List<String> shelfs = new ArrayList<>();
        for(Shelf sh : x){
            shelfs.add(sh.getName());
        }
        return shelfs;
    }

    /*Returns null if sh doesnt exist.*/
    public String getShelf(String s, String sh){
        Supermarket m = supermarkets.get(s);
        if(m.getShelf(sh) != null){
            return m.getShelf(sh).getName();
        }
        else return null;
    }

    /*restriction es el nombre de una caracteristica existente, se comprueba quee existe en el ctrl domini*/
    public boolean addRestriction(String supermarket, String restriction, String shelf, int index){
        Supermarket m = supermarkets.get(supermarket);
        //falta tratar errores
        Shelf sh = m.getShelf(shelf);

        sh.setRestriction(restriction, index);
        return true;

    }

    public boolean removeRestriction(String supermarket, String shelf, int index){
        Supermarket m = supermarkets.get(supermarket);
        //tratar errores
        Shelf sh = m.getShelf(shelf);
        sh.deleteRestrictions(index);
        return true;
    }

    public boolean resizeShelf(String supermarket, String shelf, int size){
        Supermarket m = supermarkets.get(supermarket);
        Shelf sh = m.getShelf(shelf);
        sh.resizeShelf(size);
        return true;
    }






}
