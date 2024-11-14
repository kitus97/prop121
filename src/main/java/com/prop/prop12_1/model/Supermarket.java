package com.prop.prop12_1.model;

import java.util.*;

import com.prop.prop12_1.controller.CtrlAlgorithm;
import com.prop.prop12_1.exceptions.ShelfAlreadyAddedException;
import com.prop.prop12_1.exceptions.SolutionAlreadyAddedException;
import org.apache.commons.lang3.tuple.Pair;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

public class Supermarket {
    private Map<String, Shelf> shelves;
    private Map<String, Catalogue> catalogs; //<catalog>
    private String name;
    private Map<String, Object> solutions; //<solution>


    public Supermarket(String n){
        name = n;
        shelves = new HashMap<>();
        catalogs = new HashMap<>();
        solutions = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public List<Shelf> getShelves() {
        return new ArrayList<>(shelves.values());
    }

    public Shelf getShelf(String s){
        return shelves.get(s);
    }

    public List<Object> getCatalogs() {
        return new ArrayList<>(catalogs.values());
    }

    public Object getCatalog(String s){
        return catalogs.get(s);
    }

    public List<Object> getSolutions() {
        return new ArrayList<>(solutions.values());
    }

    public Object getSolution(String s){
        return solutions.get(s);
    }


    public void generateSolution(String name, String shelf, String catalog, boolean heuristic, int algorithm){
        Shelf sh = shelves.get(shelf);
        Catalogue cat = catalogs.get(catalog);

        List<Set<String>> distribution = sh.getDistribution();;
        List<Pair<Integer, Set<String>>> products = cat.getProductsArray();

        if(solutions.containsKey(name)) throw new SolutionAlreadyAddedException("Name: " + name + " is already used as a solution name.");

        else {
            solutions.put(name, new CtrlAlgorithm().getSolution(distribution, products, algorithm, heuristic));
        }

    }


    public void addShelf(Shelf s){
        if(shelves.containsKey(s.getName())){
            throw new ShelfAlreadyAddedException("Name: " + s.getName() + " is already used as a shelf name.");
        }
        else shelves.put(s.getName(), s);
    }

    /*Devuelve un boolean indicando si se ha eliminado o no la estanteria con nombre s*/
    public boolean deleteShelf(String s){
        return shelves.remove(s) != null;
    }


}