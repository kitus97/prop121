package com.prop.prop12_1.model;

import com.prop.prop12_1.controller.CtrlAlgorithm;
import com.prop.prop12_1.exceptions.CatalogAlreadyAdded;
import com.prop.prop12_1.exceptions.ProductAlreadyAddedException;
import com.prop.prop12_1.exceptions.ShelfAlreadyAddedException;
import com.prop.prop12_1.exceptions.SolutionAlreadyAddedException;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


public class Supermarket {
    private Map<String, Shelf> shelves;
    private Map<String, Catalogue> catalogs; //<catalog>
    private String name;
    private Map<String, Solution> solutions; //<solution>
    private Map<String, List<Solution>> associatedCatalogSolutions;
    private Map<String, List<Solution>> associatedProductSolutions;
    private Map<String, List<Solution>> associatedShelfSolutions;

    public Supermarket(String n){
        name = n;
        shelves = new HashMap<>();
        catalogs = new HashMap<>();
        solutions = new HashMap<>();
        associatedCatalogSolutions = new HashMap<>();
        associatedProductSolutions = new HashMap<>();
        associatedShelfSolutions = new HashMap<>();
    }

    public void setShelves(Map<String, Shelf> shelves) {
        this.shelves = shelves;
    }

    public void setCatalogs(Map<String, Catalogue> catalogs) {
        this.catalogs = catalogs;
    }


    public void setSolutions(Map<String, Solution> solutions) {
        this.solutions = solutions;
    }

    public void setAssociatedCatalogSolutions(Map<String, List<Solution>> associatedCatalogSolutions) {
        this.associatedCatalogSolutions = associatedCatalogSolutions;
    }

    public void setAssociatedProductSolutions(Map<String, List<Solution>> associatedProductSolutions) {
        this.associatedProductSolutions = associatedProductSolutions;
    }

    public void setAssociatedShelfSolutions(Map<String, List<Solution>> associatedShelfSolutions) {
        this.associatedShelfSolutions = associatedShelfSolutions;
    }

    public String getName() {
        return name;
    }

    public List<String> getShelves() {
        List<String> shelfs = new ArrayList<>(shelves.keySet());
        return shelfs;
    }

    public Shelf getShelf(String s){
        return shelves.get(s);
    }

    public List<Catalogue> getCatalogs() {
        return new ArrayList<>(catalogs.values());
    }

    public Object getCatalog(String s){
        return catalogs.get(s);
    }


    public void generateSolution(String name, String shelf, String catalog, boolean heuristic, int algorithm){
        Shelf sh = shelves.get(shelf);
        if(sh == null) throw new NoSuchElementException("Error: No such shelf");
        Catalogue cat = catalogs.get(catalog);
        if(cat == null) throw new NoSuchElementException("Error: No such catalog");

        List<Set<String>> distribution = sh.getDistribution();
        List<Pair<Integer, Set<String>>> products = cat.getProductsArray();

        if(solutions.containsKey(name)) throw new SolutionAlreadyAddedException("Error: Name: " + name + " is already used as a solution name.");

        else {
            String alg;
            String heu;
            if(algorithm == 0) alg = "BT";
            else alg = "HC";
            if(heuristic) heu = "Generated";
            else heu = "Defined";
            Pair<Double, List<Pair<Integer, Set<String>>>> res = new CtrlAlgorithm().getSolution(distribution, products, algorithm, heuristic);
            Solution s = new Solution(name, catalog, shelf, heu, alg, res.getLeft(), res.getRight());

            solutions.put(name, s);
            associatedShelfSolutions.get(shelf).add(s);
            associatedCatalogSolutions.get(catalog).add(s);
            List<String> associatedProducts = cat.getProductNames();
            for(int i = 0; i < associatedProducts.size(); i++){
                associatedProductSolutions.computeIfAbsent(associatedProducts.get(i), k -> new ArrayList<>()).add(s);
            }
        }
    }

    private void invalidateCatalogSolution(String catalog){
        List<Solution> solutions = associatedCatalogSolutions.get(catalog);
        if (solutions == null) return;
        for (int i = 0; i < solutions.size(); i++) {
            if(solutions.get(i).deleted()){
                solutions.remove(i);
                --i;
            }
            else solutions.get(i).setValid(false);
        }
    }

    private void invalidateShelfSolution(String shelf){
        List<Solution> solutions = associatedShelfSolutions.get(shelf);
        if (solutions == null) return;
        for (int i = 0; i < solutions.size(); i++) {
            if(solutions.get(i).deleted()) {
                solutions.remove(i);
                --i;
            }
            else solutions.get(i).setValid(false);
        }

    }

    public void invalidateProductSolution(String product){
        List<Solution> solutions = associatedProductSolutions.get(product);
        if (solutions == null) return;
        for (int i = 0; i < solutions.size(); i++) {
            if(solutions.get(i).deleted()){
                solutions.remove(i);
                --i;
            }
            else solutions.get(i).setValid(false);
        }
    }

    public void addShelf(String shelf, int size){
        if(shelves.containsKey(shelf)){
            throw new ShelfAlreadyAddedException("Name: " + shelf + " is already used as a shelf name.");
        }
        else{
            Shelf s = new Shelf(shelf, size);
            shelves.put(s.getName(), s);
            associatedShelfSolutions.put(s.getName(), new ArrayList<>());
        }
    }

    /*Devuelve un boolean indicando si se ha eliminado o no la estanteria con nombre s*/
    public void deleteShelf(String shelf){
        if (shelves.remove(shelf) == null) throw new NoSuchElementException("No such shelf.");
        else associatedShelfSolutions.remove(shelf);
    }

    public void addCatalogue(String catalog){
        if(catalogs.containsKey(catalog)) throw new CatalogAlreadyAdded("Name " + catalog + " is already used as a catalogue.");
        else {
            catalogs.put(catalog, new Catalogue(catalog));
            associatedCatalogSolutions.put(catalog, new ArrayList<>());
        }
    }

    public void addRestriction(String shelf, String restriction, int index){
        Shelf sh = shelves.get(shelf);
        if(sh == null) throw new NoSuchElementException("The shelf " + shelf + " does not exist.");
        else{
            sh.setRestriction(restriction, index);
            invalidateShelfSolution(shelf);
        }
    }

    public void deleteRestrictions(String shelf, int index){
        Shelf sh = shelves.get(shelf);
        if(sh == null) throw new NoSuchElementException("The shelf " + shelf + " does not exist.");
        else{
            sh.deleteRestrictions(index);
            invalidateShelfSolution(shelf);

        }

    }

    public void addProductToCatalogue(String product, String catalog){
        Catalogue cat = catalogs.get(catalog);
        if(cat == null) throw new NoSuchElementException("Error: The catalog " + catalog + " does not exist.");
        else cat.addProduct(product);
    }

    public void removeProductFromCatalogue(String product, String catalog){
        Catalogue cat = catalogs.get(catalog);
        if(cat == null) throw new NoSuchElementException("Error: The catalog " + catalog + " does not exist.");
        else{
            cat.removeProduct(product);
            invalidateCatalogSolution(catalog);
        }
    }

    public void resizeShelf(String shelf, int size){
        Shelf sh = shelves.get(shelf);
        if(sh == null) throw new NoSuchElementException("Error: The shelf " + shelf + " does not exist.");
        else{
            sh.resizeShelf(size);
            invalidateShelfSolution(shelf);
        }
    }

    public void deleteCatalogue(String catalog){
        if (catalogs.remove(catalog) == null) throw new NoSuchElementException("Error: No such catalog.");
        else associatedCatalogSolutions.remove(catalog);
    }

    public void deleteSolution(String solution){
        Solution s = solutions.get(solution);
        if(s == null) throw new NoSuchElementException("Error: No such solution.");
        else {
            solutions.remove(solution);
            s.delete();
        }
    }

    public String getSolution(String solution){
        if (!solutions.containsKey(solution)) throw new NoSuchElementException("Error: No such solution.");
        else{
            return solutions.get(solution).toString();
        }
    }

    public List<String> getSolutions(){
        return solutions.values().stream().map(Solution::toString).collect(Collectors.toList());
    }

    public void changeSolutionProducts(int indx1, int indx2, String solution){
        if (!solutions.containsKey(solution)) throw new NoSuchElementException("No such solution.");
        else solutions.get(solution).changeProducts(indx1, indx2);
    }

    public void deleteSolutionProduct(String solution, int index){
        if (!solutions.containsKey(solution)) throw new NoSuchElementException("No such solution.");
        else {
            String product = solutions.get(solution).deleteProduct(index);
            List<Solution> ss = associatedProductSolutions.get(product);
            Solution s = solutions.get(solution);
            ss.remove(s);
        }
    }

    public void addSolutionProduct(String solution, String product, int index){
        if (!solutions.containsKey(solution)) throw new NoSuchElementException("No such solution.");
        else if(associatedProductSolutions.get(product) != null && associatedProductSolutions.get(product).contains(solutions.get(solution))){
            throw new ProductAlreadyAddedException("The product is already in the solution");
        }
        else{
            solutions.get(solution).addProduct(product, index);
            List<Solution> ss = associatedProductSolutions.get(product);
            Solution s = solutions.get(solution);
            ss.add(s);
        }
    }

    public void updateSolutionMark(String product1, List<List<Double>> similarityTable, Boolean generated){
        if(associatedProductSolutions.containsKey(product1)){
            List<Solution> ss = associatedProductSolutions.get(product1);
            for(Solution s : ss){
                if(generated){
                    if(s.getHeuristic().equals("Generated")) s.updateMark(similarityTable);
                }
                else{
                    if(s.getHeuristic().equals("Defined")) s.updateMark(similarityTable);
                }

            }
        }
    }

    public double checkDeleteSolutionProduct(String solution, int index){
        if (!solutions.containsKey(solution)) throw new NoSuchElementException("No such solution.");
        else return solutions.get(solution).checkMarkDelete(index);
    }

    public double checkAddSolutionProduct(String solution, String product, int index){
        if (!solutions.containsKey(solution)) throw new NoSuchElementException("No such solution.");
        else return solutions.get(solution).checkMarkAdd(product, index);
    }

    public double checkSwapSolution(String solution, int idx1, int idx2){
        if (!solutions.containsKey(solution)) throw new NoSuchElementException("No such solution.");
        else return solutions.get(solution).checkMarkSwap(idx1, idx2);
    }

    @Override
    public String toString() {
        return "Supermarket{" +
                "name='" + name + '\'' +
                ", shelves=" + shelves +
                ", catalogs=" + catalogs +
                ", solutions=" + solutions +
                '}';
    }

    public List<String> listProdsCatalogue(String catalogueName) {
        return catalogs.get(catalogueName).getProductNames();
    }
}