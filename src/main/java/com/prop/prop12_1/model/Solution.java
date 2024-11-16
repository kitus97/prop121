package com.prop.prop12_1.model;

import com.prop.prop12_1.controller.CtrlProd;
import com.prop.prop12_1.exceptions.InvalidProductRestrictionException;
import com.prop.prop12_1.exceptions.NotInterchangeableException;
import com.prop.prop12_1.exceptions.ProductNotFoundException;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Clase que representa una solución de una distribución de productos en estanterias
 * incluyendo el catalogo, estanteria, heuristica, algoritmo usado y tamboen la puntuacion
 * obtenida y si siguen siendo valida en caso de que hayamos elimiando ciertos productos del catalogo
 * o cambiado estanterias.
 */
public class Solution {

    private static CtrlProd ctrlProd = new CtrlProd();

    private String solutionName;
    private String idCatalog;
    private String idShelf;
    private String heuristic;
    private String algorithm;
    private Double mark;
    private Boolean valid;
    private List<Pair<Product, Set<String>>> distribution;

    /**
     * Constructor que inicializa una solución con los parámetros dados.
     *
     * @param solutionName el nombre de la solución
     * @param idCatalog    el identificador del catálogo
     * @param idShelf      el identificador de la estantería
     * @param heuristic    el nombre de la heurística utilizada
     * @param algorithm    el nombre del algoritmo utilizado
     * @param mark         la puntuación inicial de la solución
     * @param distribution la distribución inicial de productos
     */
    public Solution(String solutionName, String idCatalog, String idShelf, String heuristic, String algorithm, double mark, List<Pair<Integer, Set<String>>> distribution) {
        this.solutionName = solutionName;
        this.idCatalog = idCatalog;
        this.idShelf = idShelf;
        this.heuristic = heuristic;
        this.algorithm = algorithm;
        this.mark = mark;
        this.valid = true;
        List<Pair<Product, Set<String>>> products = new ArrayList<>();

        for (Pair<Integer, Set<String>> pair : distribution) {
            if (pair.getLeft() == null) {
                products.add(Pair.of(null, pair.getRight()));
            } else {
                products.add(Pair.of(ctrlProd.findProduct(ctrlProd.getProductName(pair.getLeft())), pair.getRight()));
            }
        }
        this.distribution = products;
    }

    /**
     * Obtiene la distribución de productos.
     *
     * @return una lista de pares (producto, restricciones)
     */
    public List<Pair<Product, Set<String>>> getDistribution() {
        return distribution;
    }

    /**
     * Crea una copia de la solución actual.
     *
     * @return una nueva instancia de Solution con los mismos valores
     */
    private Solution copy() {
        Solution s = new Solution(solutionName, idCatalog, idShelf, heuristic, algorithm, mark, new ArrayList<>());
        s.distribution = new ArrayList<>(distribution);
        return s;
    }

    /**
     * Verifica si la solución es válida.
     *
     * @return true si es válida, false en caso contrario
     */
    public Boolean getValid() {
        return valid;
    }

    /**
     * Cambia el estado de validez de la solución.
     *
     * @param valid true si es válida, false en caso contrario
     */
    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    /**
     * Obtiene la puntuación de la solución.
     *
     * @return la puntuación de la solución
     */
    public Double getMark() {
        return mark;
    }

    /**
     * Obtiene el identificador de la estantería.
     *
     * @return el ID de la estantería
     */
    public String getIdShelf() {
        return idShelf;
    }

    /**
     * Obtiene el identificador del catálogo.
     *
     * @return el ID del catálogo
     */
    public String getIdCatalog() {
        return idCatalog;
    }

    /**
     * Obtiene el nombre de la solución.
     *
     * @return el nombre de la solución
     */
    public String getSolutionName() {
        return solutionName;
    }

    /**
     * Cambia el nombre de la solución.
     *
     * @param solutionName el nuevo nombre de la solución
     */
    public void setSolutionName(String solutionName) {
        this.solutionName = solutionName;
    }

    /**
     * Cambia la puntuación de la solución.
     *
     * @param mark la nueva puntuación
     */
    public void setMark(double mark) {
        this.mark = mark;
    }

    /**
     * Cambia la distribución de productos.
     *
     * @param distribution la nueva distribución de productos
     */
    public void setDistribution(List<Pair<Product, Set<String>>> distribution) {
        this.distribution = distribution;
    }

    /**
     * Elimina todos los datos de la solución.
     */
    public void delete() {
        this.solutionName = null;
        this.idCatalog = null;
        this.idShelf = null;
        this.heuristic = null;
        this.algorithm = null;
        this.mark = null;
        this.valid = null;
        this.distribution = null;
    }

    /**
     * Verifica si la solución ha sido eliminada.
     *
     * @return true si ha sido eliminada, false en caso contrario
     */
    public Boolean deleted() {
        return this.solutionName == null;
    }

    /**
     * Verifica la puntuación resultante de intercambiar dos productos en la distribución.
     *
     * @param idx1 índice del primer producto
     * @param idx2 índice del segundo producto
     * @return la nueva puntuación
     */
    public Double checkMarkSwap(int idx1, int idx2) {
        Solution s = copy();
        s.changeProducts(idx1, idx2);
        return s.mark;
    }

    /**
     * Verifica la puntuación resultante tras eliminar un producto de la distribución.
     *
     * @param index índice del producto a eliminar
     * @return la nueva puntuación
     */
    public Double checkMarkDelete(int index) {
        Solution s = copy();
        s.deleteProduct(index);
        return s.mark;
    }

    /**
     * Verifica la puntuación resultante tras añadir un producto a la distribución.
     *
     * @param product el nombre del producto
     * @param index   índice donde añadir el producto
     * @return la nueva puntuación
     */
    public Double checkMarkAdd(String product, int index) {
        Solution s = copy();
        s.addProduct(product, index);
        return s.mark;
    }

    /**
     * Intercambia dos productos en la distribución.
     *
     * @param idx1 índice del primer producto
     * @param idx2 índice del segundo producto
     * @throws NotInterchangeableException si los productos no son intercambiables
     */
    public void changeProducts(int idx1, int idx2) {
        if (idx1 >= distribution.size() || idx2 >= distribution.size()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        Pair<Product, Set<String>> s1 = distribution.get(idx1);
        Pair<Product, Set<String>> s2 = distribution.get(idx2);

        if (s1.getRight().equals(s2.getRight())) {
            distribution.set(idx1, s2);
            distribution.set(idx2, s1);
            updateMark(getSimilarityTable());
        } else {
            throw new NotInterchangeableException("The products selected can't be swapped");
        }
    }

    /**
     * Elimina un producto de la distribución.
     *
     * @param index índice del producto a eliminar
     */
    public void deleteProduct(int index) {
        if (index >= distribution.size()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        Pair<Product, Set<String>> pair = distribution.get(index);
        distribution.set(index, Pair.of(null, pair.getRight()));
        updateMark(getSimilarityTable());
    }

    /**
     * Añade un producto a la distribución.
     *
     * @param product el nombre del producto
     * @param index   índice donde se añadirá el producto
     * @throws InvalidProductRestrictionException si el producto no cumple con las restricciones
     */
    public void addProduct(String product, int index) {
        if (index >= distribution.size()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        Product p = ctrlProd.findProduct(product);
        if (p == null) {
            throw new ProductNotFoundException("Product not found");
        }
        Pair<Product, Set<String>> pair = distribution.get(index);
        if (p.getRestrictions().equals(pair.getRight())) {
            distribution.set(index, Pair.of(p, pair.getRight()));
            updateMark(getSimilarityTable());
        } else {
            throw new InvalidProductRestrictionException("The product does not meet the required restrictions of the cell");
        }
    }

    /**
     * Actualiza la puntuación de la solución basada en la tabla de similitudes.
     *
     * @param similarityTable la tabla de similitudes entre productos
     */
    public void updateMark(List<List<Double>> similarityTable) {
        mark = calculateHeuristic(similarityTable);
    }

    /**
     * Obtiene la tabla de similitudes según la heurística configurada.
     *
     * @return la tabla de similitudes
     */
    private List<List<Double>> getSimilarityTable() {
        if (heuristic.equals("Generated")) {
            return ctrlProd.generateSimilarityTable();
        } else {
            return ctrlProd.getSimilarityTable();
        }
    }

    /**
     * Calcula la puntuación de la solución en función de la similitud entre productos.
     *
     * @param similarityTable la tabla de similitudes entre productos
     * @return el valor de la heurística
     */
    private Double calculateHeuristic(List<List<Double>> similarityTable) {
        double totalSimilarity = 0.0;
        for (int i = 0; i < distribution.size() - 1; i++) {
            if (distribution.get(i).getLeft() == null || distribution.get(i + 1).getLeft() == null) {
                continue;
            }
            int actualProduct = distribution.get(i).getLeft().getId();
            int nextProduct = distribution.get(i + 1).getLeft().getId();
            totalSimilarity += similarityTable.get(actualProduct).get(nextProduct);
        }
        if (distribution.get(distribution.size() - 1).getLeft() != null && distribution.get(0).getLeft() != null) {
            int actualProduct = distribution.get(distribution.size() - 1).getLeft().getId();
            int nextProduct = distribution.get(0).getLeft().getId();
            totalSimilarity += similarityTable.get(actualProduct).get(nextProduct);
        }
        return Math.round(totalSimilarity * 1e5) / 1e5;
    }

    /**
     * Devuelve una representación en string de la solución.
     *
     * @return una string con los datos principales de la solución
     */
    @Override
    public String toString() {
        return "{" + solutionName + ", Catalog: " + idCatalog + ", Shelf: " + idShelf +
                ", Heuristic: " + heuristic + ", Algorithm: " + algorithm + ", Puntuation: "
                + mark + "}\n";
    }

    /**
     * Devuelve una representación detallada de la solución con la distribución.
     *
     * @return una cadena con la distribución completa de la solución
     */
    public String toString1() {
        StringBuilder distributionString = new StringBuilder("[");
        for (Pair<Product, Set<String>> pair : distribution) {
            String productName = (pair.getLeft() != null) ? pair.getLeft().getName() : "null";
            String restrictions = (pair.getRight() != null) ? pair.getRight().toString() : "null";
            distributionString.append("(Product: ").append(productName)
                    .append(", Restrictions: ").append(restrictions).append("), ");
        }
        if (!distribution.isEmpty()) {
            distributionString.setLength(distributionString.length() - 2); // Eliminar última coma y espacio
        }
        distributionString.append("]");

        return "{" + solutionName + ", Catalog: " + idCatalog + ", Shelf: " + idShelf +
                ", Heuristic: " + heuristic + ", Algorithm: " + algorithm + ", Puntuation: "
                + mark + ", Distribution: " + distributionString + "}\n";
    }
}
