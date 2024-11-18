package com.prop.prop12_1.controller;

import com.prop.prop12_1.exceptions.SupermarketAlreadyAddedException;
import com.prop.prop12_1.model.Catalogue;
import com.prop.prop12_1.model.Supermarket;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Controlador encargado de gestionar supermercados y sus componentes
 * como estantes, catálogos y soluciones.
 */
public class CtrlSupermarket {

    private static Map<String, Supermarket> supermarkets = new HashMap<>();

    /**
     * Constructor por defecto de la clase CtrlSupermarket.
     */
    public CtrlSupermarket() {
    }

    /**
     * Añade un nuevo supermercado al sistema.
     *
     * @param n Nombre del supermercado.
     * @throws SupermarketAlreadyAddedException si el supermercado ya existe.
     */
    public void addSupermarket(String n) {
        if (supermarkets.put(n, new Supermarket(n)) != null)
            throw new SupermarketAlreadyAddedException("Supermarket with name '" + n + "' already exists");
    }

    /**
     * Elimina un supermercado del sistema.
     *
     * @param n Nombre del supermercado.
     * @throws NoSuchElementException si el supermercado no existe.
     */
    public void removeSupermarket(String n) {
        if (supermarkets.remove(n) == null)
            throw new NoSuchElementException("Error: supermarket with name '" + n + "' not found");
    }

    /**
     * Devuelve una lista de nombres de todos los supermercados registrados.
     *
     * @return Lista de nombres de supermercados.
     */
    public List<String> getSupermarkets() {
        return supermarkets.values().stream().map(Supermarket::getName).toList();
    }

    /**
     * Comprueba si un supermercado existe en el sistema.
     *
     * @param supermarket Nombre del supermercado.
     * @return true si el supermercado existe, false en caso contrario.
     */
    public boolean existsSupermarket(String supermarket) {
        return supermarkets.containsKey(supermarket);
    }

    /**
     * Elimina un estante de un supermercado específico.
     *
     * @param supermarket Nombre del supermercado.
     * @param shelf       Nombre de la estantería.
     */
    public void removeShelf(String supermarket, String shelf) {
        supermarkets.get(supermarket).deleteShelf(shelf);
    }

    /**
     * Crea un nuevo estante en un supermercado específico.
     *
     * @param supermarket Nombre del supermercado.
     * @param shelf       Nombre de la estantería.
     * @param size        Tamaño de la estantería.
     */
    public void createShelf(String supermarket, String shelf, int size) {
        supermarkets.get(supermarket).addShelf(shelf, size);
    }

    /**
     * Devuelve una lista de los nombres de los estantes de un supermercado.
     *
     * @param supermarket Nombre del supermercado.
     * @return Lista de nombres de los estantes.
     */
    public List<String> getShelves(String supermarket) {
        return supermarkets.get(supermarket).getShelves();
    }

    /**
     * Obtiene información de una estantería específico como su distribución y restricciones.
     *
     * @param supermarket Nombre del supermercado.
     * @param sh          Nombre de la estantería.
     * @return Información de la estantería como cadena.
     */
    public String getShelf(String supermarket, String sh) {
        return supermarkets.get(supermarket).getShelf(sh);
    }

    /**
     * Añade una restricción a un estante en un supermercado.
     *
     * @param supermarket Nombre del supermercado.
     * @param shelf       Nombre de la estanteria.
     * @param restriction Nombre de la restricción.
     * @param index       Índice en el estante donde se aplica la restricción.
     */
    public void addRestriction(String supermarket, String shelf, String restriction, int index) {
        supermarkets.get(supermarket).addRestriction(shelf, restriction, index);
    }

    /**
     * Elimina las restricciones de una estantería en un índice específico.
     *
     * @param supermarket Nombre del supermercado.
     * @param shelf       Nombre de la estantería.
     * @param index       Índice en el estante donde eliminar la restricción.
     */
    public void removeRestriction(String supermarket, String shelf, int index) {
        supermarkets.get(supermarket).deleteRestrictions(shelf, index);
    }

    /**
     * Cambia el tamaño de una estantería en un supermercado.
     *
     * @param supermarket Nombre del supermercado.
     * @param shelf       Nombre de la estantería.
     * @param size        Nuevo tamaño de la estantería.
     */
    public void resizeShelf(String supermarket, String shelf, int size) {
        supermarkets.get(supermarket).resizeShelf(shelf, size);
    }

    /**
     * Genera una solución para un supermercado basado en un algoritmo específico.
     *
     * @param supermarket Nombre del supermercado.
     * @param name        Nombre de la solución.
     * @param shelf       Nombre de la estantería.
     * @param catalog     Nombre del catálogo.
     * @param heuristic   Indicador de si se usa una heurística.
     * @param algorithm   Identificador del algoritmo.
     */
    public void generateSolution(String supermarket, String name, String shelf, String catalog, boolean heuristic, int algorithm) {
        supermarkets.get(supermarket).generateSolution(name, shelf, catalog, heuristic, algorithm);
    }

    /**
     * Añade un producto a un catálogo de un supermercado.
     *
     * @param supermarket Nombre del supermercado.
     * @param product     Nombre del producto.
     * @param catalog     Nombre del catálogo.
     */
    public void addProductToCatalogue(String supermarket, String product, String catalog) {
        supermarkets.get(supermarket).addProductToCatalogue(product, catalog);
    }

    /**
     * Elimina un producto de un catálogo de un supermercado.
     *
     * @param supermarket Nombre del supermercado.
     * @param product     Nombre del producto.
     * @param catalog     Nombre del catálogo.
     */
    public void removeProductFromCatalogue(String supermarket, String product, String catalog) {
        supermarkets.get(supermarket).removeProductFromCatalogue(product, catalog);
    }

    /**
     * Invalida todas las soluciones relacionadas con un producto.
     *
     * @param product Nombre del producto.
     */
    public void invalidateProductSolution(String product) {
        supermarkets.values().forEach(m -> m.invalidateProductSolution(product));
    }

    /**
     * Devuelve información de una solución específica de un supermercado.
     *
     * @param supermarket Nombre del supermercado.
     * @param solution    Nombre de la solución.
     * @return Información de la solución como cadena.
     */
    public String getSolution(String supermarket, String solution) {
        return supermarkets.get(supermarket).getSolution(solution);
    }

    /**
     * Devuelve una lista de soluciones de un supermercado.
     *
     * @param supermarket Nombre del supermercado.
     * @return Lista de nombres de soluciones.
     */
    public List<String> getSolutions(String supermarket) {
        return supermarkets.get(supermarket).getSolutions();
    }

    /**
     * Elimina un catálogo de un supermercado.
     *
     * @param supermarket Nombre del supermercado.
     * @param catalog     Nombre del catálogo.
     */
    public void deleteCatalog(String supermarket, String catalog) {
        supermarkets.get(supermarket).deleteCatalogue(catalog);
    }

    /**
     * Elimina una solución de un supermercado.
     *
     * @param supermarket Nombre del supermercado.
     * @param solution    Nombre de la solución.
     */
    public void deleteSolution(String supermarket, String solution) {
        supermarkets.get(supermarket).deleteSolution(solution);
    }

    /**
     * Añade un catálogo a un supermercado.
     *
     * @param supermarket Nombre del supermercado.
     * @param catalog     Nombre del catálogo.
     */
    public void addCatalog(String supermarket, String catalog) {
        supermarkets.get(supermarket).addCatalogue(catalog);
    }

    /**
     * Elimina un producto de una solución de un supermercado.
     *
     * @param supermarket Nombre del supermercado.
     * @param solution    Nombre de la solución.
     * @param index       Índice del producto.
     */
    public void deleteSolutionProduct(String supermarket, String solution, int index) {
        supermarkets.get(supermarket).deleteSolutionProduct(solution, index);
    }

    /**
     * Añade un producto a una solución de un supermercado.
     *
     * @param supermarket Nombre del supermercado.
     * @param solution    Nombre de la solución.
     * @param product     Nombre del producto.
     * @param index       Índice en la solución.
     */
    public void addSolutionProduct(String supermarket, String solution, String product, int index) {
        supermarkets.get(supermarket).addSolutionProduct(solution, product, index);
    }

    /**
     * Cambia dos productos en una solución de un supermercado.
     *
     * @param supermarket Nombre del supermercado.
     * @param indx1       Índice del primer producto.
     * @param indx2       Índice del segundo producto.
     * @param solution    Nombre de la solución.
     */
    public void changeSolutionProducts(String supermarket, int indx1, int indx2, String solution) {
        supermarkets.get(supermarket).changeSolutionProducts(indx1, indx2, solution);
    }

    /**
     * Actualiza las marcas de las soluciones de todos los supermercados.
     *
     * @param product1        Nombre del producto.
     * @param similarityTable Matriz de similitudes.
     * @param generated       Indicador de si la similitud fue generada.
     */
    public void updateSolutionMarks(String product1, List<List<Double>> similarityTable, Boolean generated) {
        supermarkets.values().forEach(m -> m.updateSolutionMark(product1, similarityTable, generated));
    }

    /**
     * Verifica el impacto de eliminar un producto de una solución en un supermercado.
     *
     * @param supermarket Nombre del supermercado.
     * @param solution    Nombre de la solución.
     * @param index       Índice del producto.
     * @return Puntuación resultante después de eliminar el producto.
     */
    public double checkDeleteSolutionProduct(String supermarket, String solution, int index) {
        return supermarkets.get(supermarket).checkDeleteSolutionProduct(solution, index);
    }

    /**
     * Verifica el impacto de añadir un producto a una solución en un supermercado.
     *
     * @param supermarket Nombre del supermercado.
     * @param solution    Nombre de la solución.
     * @param product     Nombre del producto.
     * @param index       Índice en la solución.
     * @return Puntuación resultante después de añadir el producto.
     */
    public double checkAddSolutionProduct(String supermarket, String solution, String product, int index) {
        return supermarkets.get(supermarket).checkAddSolutionProduct(solution, product, index);
    }

    /**
     * Verifica el impacto de intercambiar dos productos en una solución en un supermercado.
     *
     * @param supermarket Nombre del supermercado.
     * @param solution    Nombre de la solución.
     * @param index1      Índice del primer producto.
     * @param index2      Índice del segundo producto.
     * @return Puntuación resultante después del intercambio.
     */
    public double checkSwapSolution(String supermarket, String solution, int index1, int index2) {
        return supermarkets.get(supermarket).checkSwapSolution(solution, index1, index2);
    }

    /**
     * Lista los productos de un catálogo de un supermercado.
     *
     * @param supermarket  Nombre del supermercado.
     * @param catalogueName Nombre del catálogo.
     * @return Lista de productos en el catálogo.
     */
    public List<String> listProdsCatalogue(String supermarket, String catalogueName) {
        return supermarkets.get(supermarket).listProdsCatalogue(catalogueName);
    }

    /**
     * Lista los catálogos de un supermercado.
     *
     * @param supermarketName Nombre del supermercado.
     * @return Lista de nombres de catálogos.
     */
    public List<String> listCatalogues(String supermarketName) {
        return supermarkets.get(supermarketName).getCatalogs().stream()
                .map(Catalogue::getName)
                .toList();
    }
}
