package com.prop.prop12_1.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Supermarket {
    private HashMap<String, Shelf> shelves;
    private HashMap<String, Object> catalogs; //<catalog>
    private String name;
    private HashMap<String, Object> solutions; //<solution>


    public Supermarket(String n){
        name = n;
        shelves = new HashMap<>();
        catalogs = new HashMap<>();
        solutions = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Shelf> getShelves() {
        return new ArrayList<>(shelves.values());
    }

    public Shelf getShelf(String s){
        return shelves.get(s);
    }

    public ArrayList<Object> getCatalogs() {
        return new ArrayList<>(catalogs.values());
    }

    public Object getCatalog(String s){
        return catalogs.get(s);
    }

    public ArrayList<Object> getSolutions() {
        return new ArrayList<>(solutions.values());
    }

    public Object getSolution(String s){
        return solutions.get(s);
    }

    /*shelf, catalog, heuristic , id algorithm*/
    public void generateSolution(String sh, String c, String h, int alg){

    }


    public boolean addShelf(Shelf s){
        if(shelves.containsKey(s.getName())){
            return false;
        }
        else shelves.put(s.getName(), s);
        return true;
    }

    /*Devuelve un boolean indicando si se ha eliminado o no la estanteria con nombre s*/
    public boolean deleteShelf(String s){
        return shelves.remove(s) != null;
    }


}