package com.prop.prop12_1.controller;

import com.prop.prop12_1.model.Shelf;
import com.prop.prop12_1.model.Supermarket;

import java.util.ArrayList;
import java.util.HashMap;

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

    public ArrayList<String> getSupermarkets(){
        ArrayList<Supermarket> temp = new ArrayList<>(supermarkets.values());
        ArrayList<String> ret = new ArrayList<>();
        for(Supermarket s : temp){
            ret.add(s.getName());
        }

        return ret;
    }

    /*Pre: s exists*/
    public boolean removeShelf(String s, String sh){
        Supermarket m = supermarkets.get(s);
        return m.deleteShelf(sh);
    }

    /*Pre: s exists*/
    public boolean createShelf(String s, String sh, int size){
        Supermarket m = supermarkets.get(s);
        Shelf shelf = new Shelf(sh, size);
        return(m.addShelf(shelf));
    }

    /*Pre: s exists*/
    public ArrayList<String> getShelves(String s){
        Supermarket m = supermarkets.get(s);
        ArrayList<Shelf> x = m.getShelves();
        ArrayList<String> shelfs = new ArrayList<>();
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





}
