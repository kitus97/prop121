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

/**
 * Representa un supermercado que contiene estanterías, catálogos y soluciones.
 * <p>
 * La clase {@code Supermarket} permite gestionar estanterías, catálogos de productos y soluciones.
 * Incluye funcionalidades para agregar, eliminar, modificar y recuperar información asociada.
 * </p>
 */
public class Supermarket {
    private Map<String, Shelf> shelves;
    private Map<String, Catalogue> catalogs; //<catalog>
    private String name;
    private Map<String, Solution> solutions; //<solution>
    private Map<String, List<Solution>> associatedCatalogSolutions;
    private Map<String, List<Solution>> associatedProductSolutions;
    private Map<String, List<Solution>> associatedShelfSolutions;

    /**
     * Construye un nuevo {@code Supermarket} con el nombre especificado y sin elementos iniciales.
     *
     * @param n El nombre del supermercado.
     */
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

    /**
     * Devuelve una lista con los nombres de las estanterías del supermercado.
     *
     * @return Una lista de nombres de estanterías.
     */
    public List<String> getShelves() {
        List<String> shelfs = new ArrayList<>(shelves.keySet());
        return shelfs;
    }

    public String getShelf(String s){
        if(!shelves.containsKey(s)) throw new NoSuchElementException("No such self");
        else return shelves.get(s).toString();
    }

    /**
     * Devuelve una lista con todos los catálogos del supermercado.
     *
     * @return Una lista de objetos de tipo {@code Catalogue}.
     */
    public List<Catalogue> getCatalogs() {
        return new ArrayList<>(catalogs.values());
    }

    public Object getCatalog(String s){
        return catalogs.get(s);
    }

    /**
     * Genera una solución para una combinación de estantería y catálogo.
     *
     * @param name El nombre de la solución.
     * @param shelf El nombre de la estantería.
     * @param catalog El nombre del catálogo.
     * @param heuristic Indica si se usa una heurística de similaridades generada o definida por el usuario.
     * @param algorithm El tipo de algoritmo a usar: 0 (Backtracking), 1 (HillClimbing), 2 (Greedy).
     * @throws NoSuchElementException si la estantería o el catálogo no existen.
     * @throws SolutionAlreadyAddedException si ya existe una solución con el nombre proporcionado.
     */
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
            else if(algorithm == 1) alg = "HC";
            else alg = "Greedy";
            if(heuristic) heu = "Generated";
            else heu = "Defined";
            Pair<Double, List<Pair<Integer, Set<String>>>> res = new CtrlAlgorithm().getSolution(distribution, products, algorithm, heuristic);
            Solution s = new Solution(name, catalog, shelf, heu, alg, res.getLeft(), res.getRight());

            solutions.put(name, s);
            associatedShelfSolutions.get(shelf).add(s);
            associatedCatalogSolutions.get(catalog).add(s);
            List<String> associatedProducts = cat.getProductNames();
            for(int i = 0; i < associatedProducts.size(); i++){
                String product = associatedProducts.get(i);
                associatedProductSolutions.computeIfAbsent(product, k -> new ArrayList<>()).add(s);

            }
        }
    }

    /**
     * Invalida las soluciones que usan este catálogo
     * @param catalog
     */

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

    /**
     * invalida las soluciones que usan la estantería
     * @param shelf
     */
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

    /**
     * Invalida las soluciones asociadas a un producto.
     * Las soluciones asociadas al producto se marcan como no válidas o se eliminan si ya están marcadas como eliminadas.
     *
     * @param product El nombre del producto cuyas soluciones se invalidarán.
     */
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

    /**
     * Agrega una nueva estantería al supermercado.
     *
     * @param shelf El nombre de la nueva estantería.
     * @param size El tamaño de la estantería.
     * @throws ShelfAlreadyAddedException si ya existe una estantería con el nombre proporcionado.
     */
    public void addShelf(String shelf, int size){
        if(shelves.containsKey(shelf)){
            throw new ShelfAlreadyAddedException("Name: " + shelf + " is already used as a shelf name.");
        }
        else{
            Shelf s = new Shelf(shelf, size);
            shelves.put(s.getName(), s);
            associatedShelfSolutions.put(shelf, new ArrayList<>());
        }
    }

    /*Devuelve un boolean indicando si se ha eliminado o no la estanteria con nombre s*/
    /**
     * Elimina una estantería del supermercado.
     *
     * @param shelf El nombre de la estantería a eliminar.
     * @throws NoSuchElementException si no existe la estantería con el nombre proporcionado.
     */
    public void deleteShelf(String shelf){
        if (shelves.remove(shelf) == null) throw new NoSuchElementException("No such shelf.");
        else associatedShelfSolutions.remove(shelf);
    }

    /**
     * Agrega un catálogo al supermercado.
     *
     * @param catalog El nombre del catálogo a agregar.
     * @throws CatalogAlreadyAdded si ya existe un catálogo con el nombre proporcionado.
     */
    public void addCatalogue(String catalog){
        if(catalogs.containsKey(catalog)) throw new CatalogAlreadyAdded("Name " + catalog + " is already used as a catalogue.");
        else {
            catalogs.put(catalog, new Catalogue(catalog));
            associatedCatalogSolutions.put(catalog, new ArrayList<>());
        }
    }

    /**
     * Agrega una restricción a una estantería en un índice específico.
     *
     * @param shelf El nombre de la estantería.
     * @param restriction La restricción a agregar.
     * @param index El índice donde se aplicará la restricción.
     * @throws NoSuchElementException si no existe una estantería con el nombre proporcionado.
     */
    public void addRestriction(String shelf, String restriction, int index){
        Shelf sh = shelves.get(shelf);
        if(sh == null) throw new NoSuchElementException("The shelf " + shelf + " does not exist.");
        else{
            sh.setRestriction(restriction, index);
            invalidateShelfSolution(shelf);
        }
    }

    /**
     * Elimina las restricciones de una estantería en un índice específico.
     *
     * @param shelf El nombre de la estantería.
     * @param index El índice donde las restricciones se eliminarán.
     * @throws NoSuchElementException si no existe una estantería con el nombre proporcionado.
     */
    public void deleteRestrictions(String shelf, int index){
        Shelf sh = shelves.get(shelf);
        if(sh == null) throw new NoSuchElementException("The shelf " + shelf + " does not exist.");
        else{
            sh.deleteRestrictions(index);
            invalidateShelfSolution(shelf);

        }

    }

    /**
     * Agrega un producto a un catálogo.
     *
     * @param product El nombre del producto a agregar.
     * @param catalog El nombre del catálogo al que se agregará el producto.
     * @throws NoSuchElementException si no existe un catálogo con el nombre proporcionado.
     */
    public void addProductToCatalogue(String product, String catalog){
        Catalogue cat = catalogs.get(catalog);
        if(cat == null) throw new NoSuchElementException("Error: The catalog " + catalog + " does not exist.");
        else cat.addProduct(product);
    }

    /**
     * Elimina un producto de un catálogo.
     *
     * @param product El nombre del producto a eliminar.
     * @param catalog El nombre del catálogo del que se eliminará el producto.
     * @throws NoSuchElementException si no existe un catálogo con el nombre proporcionado.
     */
    public void removeProductFromCatalogue(String product, String catalog){
        Catalogue cat = catalogs.get(catalog);
        if(cat == null) throw new NoSuchElementException("Error: The catalog " + catalog + " does not exist.");
        else{
            cat.removeProduct(product);
            invalidateCatalogSolution(catalog);
        }
    }

    /**
     * Redimensiona una estantería a un nuevo tamaño.
     *
     * @param shelf El nombre de la estantería.
     * @param size El nuevo tamaño de la estantería.
     * @throws NoSuchElementException si no existe una estantería con el nombre proporcionado.
     */
    public void resizeShelf(String shelf, int size){
        Shelf sh = shelves.get(shelf);
        if(sh == null) throw new NoSuchElementException("Error: The shelf " + shelf + " does not exist.");
        else{
            sh.resizeShelf(size);
            invalidateShelfSolution(shelf);
        }
    }

    /**
     * Elimina un catálogo del supermercado.
     *
     * @param catalog El nombre del catálogo a eliminar.
     * @throws NoSuchElementException si no existe un catálogo con el nombre proporcionado.
     */
    public void deleteCatalogue(String catalog){
        if (catalogs.remove(catalog) == null) throw new NoSuchElementException("Error: No such catalog.");
        else associatedCatalogSolutions.remove(catalog);
    }

    /**
     * Elimina una solución por su nombre.
     *
     * @param solution El nombre de la solución a eliminar.
     * @throws NoSuchElementException si no existe una solución con el nombre proporcionado.
     */
    public void deleteSolution(String solution){
        Solution s = solutions.get(solution);
        if(s == null) throw new NoSuchElementException("Error: No such solution.");
        else {
            solutions.remove(solution);
            s.delete();
        }
    }

    /**
     * Devuelve una solución específica por su nombre.
     *
     * @param solution El nombre de la solución.
     * @throws NoSuchElementException si no existe una solución con el nombre proporcionado.
     * @return Una representación en forma de string de la solución.
     */
    public String getSolution(String solution){
        if (!solutions.containsKey(solution)) throw new NoSuchElementException("Error: No such solution.");
        else{
            return solutions.get(solution).toString();
        }
    }

    /**
     * Devuelve una lista de todas las soluciones disponibles en el supermercado.
     *
     * @return Una lista de representaciones en forma de string de las soluciones.
     */
    public List<String> getSolutions(){
        return solutions.values().stream().map(Solution::toString1).collect(Collectors.toList());
    }

    /**
     * Intercambia dos productos en una solución por sus índices.
     *
     * @param indx1 El índice del primer producto.
     * @param indx2 El índice del segundo producto.
     * @param solution El nombre de la solución donde se realizará el cambio.
     * @throws NoSuchElementException si no existe una solución con el nombre proporcionado.
     */
    public void changeSolutionProducts(int indx1, int indx2, String solution){
        if (!solutions.containsKey(solution)) throw new NoSuchElementException("No such solution.");
        else solutions.get(solution).changeProducts(indx1, indx2);
    }

    /**
     * Elimina un producto de una solución por su índice.
     *
     * @param solution El nombre de la solución.
     * @param index El índice del producto a eliminar.
     * @throws NoSuchElementException si no existe una solución con el nombre proporcionado.
     */
    public void deleteSolutionProduct(String solution, int index){
        if (!solutions.containsKey(solution)) throw new NoSuchElementException("No such solution.");

        else {
            String product = solutions.get(solution).deleteProduct(index);
            List<Solution> ss = associatedProductSolutions.get(product);
            Solution s = solutions.get(solution);
            ss.remove(s);
        }


    }

    /**
     * Agrega un producto a una solución en un índice específico.
     *
     * @param solution El nombre de la solución.
     * @param product El nombre del producto a agregar.
     * @param index El índice donde se agregará el producto.
     * @throws NoSuchElementException si no existe una solución con el nombre proporcionado.
     * @throws ProductAlreadyAddedException si el producto ya está presente en la solución.
     */
    public void addSolutionProduct(String solution, String product, int index){
        if (!solutions.containsKey(solution)) throw new NoSuchElementException("No such solution.");
        else if(associatedProductSolutions.get(product) != null && associatedProductSolutions.get(product).contains(solutions.get(solution))){
            throw new ProductAlreadyAddedException("The product is already in the solution");
        }
        else{
            solutions.get(solution).addProduct(product, index);
            associatedProductSolutions.computeIfAbsent(solution, k -> new ArrayList<>()).add(solutions.get(solution));

        }
    }


    /**
     * Actualiza la puntuación de las soluciones asociadas a un producto, basado en la tabla de similitud.
     * Dependiendo de si la solución usa una heurística generada o definida, se actualiza la puntuación
     * de la solución correspondiente.
     *
     * @param product1 El nombre del producto cuyas soluciones deben ser actualizadas.
     * @param similarityTable La tabla de similitud que se utiliza para calcular las nuevas calificaciones.
     * @param generated Indica si la heurística utilizada para la calificación es generada (true) o definida (false).
     */
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

    /**
     * Verifica la puntuaciób de una solución cuando se elimina un producto de la misma.
     * Este método calcula cómo cambia la puntuación de la solución al eliminar el producto
     * en la posición indicada dentro de la solución.
     *
     * @param solution El nombre de la solución a la que se le va a eliminar un producto.
     * @param index El índice del producto que se va a eliminar en la solución.
     * @throws NoSuchElementException Si no existe la solución especificada.
     * @return El valor de la calificación resultante al eliminar el producto.
     */
    public double checkDeleteSolutionProduct(String solution, int index){
        if (!solutions.containsKey(solution)) throw new NoSuchElementException("No such solution.");
        else return solutions.get(solution).checkMarkDelete(index);
    }

    /**
     * Verifica la puntuación de una solución cuando se agrega un producto a la misma.
     * Este método calcula cómo cambiaría la puntuación de la solución al agregar el producto
     * en la posición indicada dentro de la solución.
     *
     * @param solution El nombre de la solución a la que se le va a agregar un producto.
     * @param product El nombre del producto que se va a agregar a la solución.
     * @param index El índice en el que se agregará el producto dentro de la solución.
     * @return El valor de la calificación resultante al agregar el producto.
     * @throws NoSuchElementException Si no existe la solución especificada.
     */
    public double checkAddSolutionProduct(String solution, String product, int index){
        if (!solutions.containsKey(solution)) throw new NoSuchElementException("No such solution.");
        else return solutions.get(solution).checkMarkAdd(product, index);
    }

    /**
     * Verifica la puntuación de una solución cuando se intercambian dos productos dentro de la misma.
     * Este método calcula cómo cambiaría la puntuación de la solución al intercambiar dos productos
     * en las posiciones indicadas dentro de la solución.
     *
     * @param solution El nombre de la solución en la que se realizará el intercambio de productos.
     * @param idx1 El índice del primer producto a intercambiar.
     * @param idx2 El índice del segundo producto a intercambiar.
     * @return El valor de la calificación resultante al realizar el intercambio de los productos.
     * @throws NoSuchElementException Si no existe la solución especificada.
     */
    public double checkSwapSolution(String solution, int idx1, int idx2){
        if (!solutions.containsKey(solution)) throw new NoSuchElementException("No such solution.");
        else return solutions.get(solution).checkMarkSwap(idx1, idx2);
    }

    /**
     * Devuelve una representación en forma de cadena de texto del supermercado.
     *
     * @return Una cadena de texto que incluye el nombre del supermercado y sus elementos asociados (estanterías, catálogos y soluciones).
     */
    @Override
    public String toString() {
        return "Supermarket{" +
                "name='" + name + '\'' +
                ", shelves=" + shelves +
                ", catalogs=" + catalogs +
                ", solutions=" + solutions +
                '}';
    }

    /**
     * Devuelve los nombres de los productos de un catálogo específico.
     *
     * @param catalogueName El nombre del catálogo.
     * @return Una lista de nombres de productos en el catálogo.
     */
    public List<String> listProdsCatalogue(String catalogueName) {
        return catalogs.get(catalogueName).getProductNames();
    }
}