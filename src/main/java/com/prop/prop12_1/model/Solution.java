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
 * Representa una solución generada para la distribución de productos en una estantería dentro de un supermercado.
 * <p>
 * La clase {@code Solution} representa una solución en el contexto del sistema,
 * que consiste en una distribución de productos sobre una estantería,
 * junto con un conjunto de características como su puntuación, validez,
 * algoritmo utilizado, entre otros.
 * <p>
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
     * Constructora que crea una nueva solución con los parámetros especificados.
     *
     * @param solutionName El nombre de la solución.
     * @param idCatalog El identificador del catálogo.
     * @param idShelf El identificador de la estantería.
     * @param heuristic El tipo de heurística utilizada ("Generada" o "Definida").
     * @param algorthm El algoritmo utilizado ("BT", "HC" o "Greedy").
     * @param mark La puntuación de la solución.
     * @param distribution La distribución de los productos en la estantería.
     */
    public Solution(String solutionName, String idCatalog, String idShelf, String heuristic, String algorthm, double mark, List<Pair<Integer,Set<String>>> distribution) {
        this.solutionName = solutionName;
        this.idCatalog = idCatalog;
        this.idShelf = idShelf;
        this.heuristic = heuristic;
        this.algorithm = algorthm;
        this.mark = mark;
        this.valid = true;
        List<Pair<Product, Set<String>>> products = new ArrayList<>();

        for(int i = 0; i < distribution.size(); i++) {
            Pair<Integer, Set<String>> pair = distribution.get(i);
            if(pair.getLeft() == null) products.add(Pair.of(null, pair.getRight()));
            else products.add(Pair.of(ctrlProd.findProduct(ctrlProd.getProductName(pair.getLeft())), pair.getRight()));

        }

        this.distribution = products;
    }

    public List<Pair<Product,Set<String>>> getDistribution() {
        return distribution;
    }

    private Solution copy(){
        Solution s = new Solution(solutionName, idCatalog, idShelf, heuristic, algorithm, mark, new ArrayList<>());
        s.distribution = new ArrayList<>(distribution);
        return s;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Double getMark() {
        return mark;
    }

    public String getIdShelf() {
        return idShelf;
    }

    public String getIdCatalog() {
        return idCatalog;
    }

    public String getSolutionName() {
        return solutionName;
    }

    public String getHeuristic() {
        return heuristic;
    }

    public String getAlgorithm() {
        return algorithm;
    }


    public void setSolutionName(String solutionName) {
        this.solutionName = solutionName;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }


    public void setDistribution(List<Pair<Product ,Set<String>>> distribution) {
        this.distribution = distribution;
    }

    /**
     * Elimina todos los datos de la solución.
     * Pone a null todos los atributos de la solución.
     */
    public void delete(){
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
     * @return true si la solución ha sido eliminada, false si no lo ha sido.
     */
    public Boolean deleted(){
        return this.solutionName == null;
    }

    /**
     * Calcula la nueva puntuación de la solución si se intercambian dos productos.
     *
     * @param idx1 El índice del primer producto.
     * @param idx2 El índice del segundo producto.
     * @return La nueva puntuación de la solución después del intercambio.
     */
    public Double checkMarkSwap(int idx1, int idx2){
        Solution s = copy();
        s.changeProducts(idx1, idx2);
        return s.mark;
    }

    /**
     * Calcula la nueva puntuación de la solución si se elimina un producto.
     *
     * @param index El índice del producto a eliminar.
     * @return La nueva puntuación de la solución después de eliminar el producto.
     */
    public Double checkMarkDelete(int index){
        Solution s = copy();
        s.deleteProduct(index);
        return s.mark;
    }

    /**
     * Calcula la nueva puntuación de la solución si se agrega un producto.
     *
     * @param product El nombre del producto a agregar.
     * @param index El índice donde agregar el producto.
     * @return La nueva puntuación de la solución después de agregar el producto.
     */
    public Double checkMarkAdd(String product, int index){
        Solution s = copy();
        s.addProduct(product, index);
        return s.mark;
    }

    /**
     * Cambia la posición de dos productos en la distribución.
     *
     * @param idx1 El índice del primer producto.
     * @param idx2 El índice del segundo producto.
     * @throws NotInterchangeableException Si los productos no se pueden intercambiar debido a sus restricciones.
     */
    public void changeProducts(int idx1, int idx2) {
        if(idx1 >= distribution.size() || idx2 >= distribution.size()) throw new IndexOutOfBoundsException("Invalid index");

        Pair<Product, Set<String>> s1 = distribution.get(idx1);
        Pair<Product, Set<String>> s2 = distribution.get(idx2);

        if(s1.getRight().equals(s2.getRight())) {
            distribution.set(idx1, s2);
            distribution.set(idx2, s1);
            updateMark(getSimilarityTable());
        }
        else throw new NotInterchangeableException("The products selected can't be swapped");

    }

    /**
     * Elimina un producto de la distribución.
     *
     * @param index El índice del producto a eliminar.
     * @throws IndexOutOfBoundsException Si el índice está fuera de rango.
     */
    public String deleteProduct(int index){
        if(index >= distribution.size()) throw new IndexOutOfBoundsException("Invalid index");
        Pair<Product, Set<String>> pair = distribution.get(index);
        distribution.set(index, Pair.of(null, pair.getRight()));
        updateMark(getSimilarityTable());
        return pair.getLeft().getName();
    }

    /**
     * Agrega un producto a la distribución.
     *
     * @param product El nombre del producto a agregar.
     * @param index El índice donde agregar el producto.
     * @throws InvalidProductRestrictionException Si el producto no cumple con las restricciones de la celda.
     * @throws ProductNotFoundException Si el producto no existe.
     */
    public void addProduct(String product, int index){
        if(index >= distribution.size()) throw new IndexOutOfBoundsException("Invalid index");
        Product p = ctrlProd.findProduct(product);
        if(p == null) throw new ProductNotFoundException("Product not found");
        Pair<Product, Set<String>> pair = distribution.get(index);
        if(p.getRestrictions().equals(pair.getRight())) {
            distribution.set(index, Pair.of(p, pair.getRight()));
            updateMark(getSimilarityTable());
        }
        else throw new InvalidProductRestrictionException("The product does not meet the required restrictions of the cell");
    }

    /**
     * Actualiza la puntuación de la solución basándose en una tabla de similitud.
     *
     * @param similarityTable La tabla de similitud que se utilizará para calcular la puntuación.
     */
    public void updateMark(List<List<Double>> similarityTable){
        mark = calculateHeuristic(similarityTable);
    }

    private List<List<Double>> getSimilarityTable(){
        if(heuristic.equals("Generated")) return ctrlProd.generateSimilarityTable();
        else return ctrlProd.getSimilarityTable();
    }

    private Double calculateHeuristic(List<List<Double>> similarityTable) {
        double totalSimilarity = 0.0;
        for (int i = 0; i < distribution.size() - 1; i++) {
            if (distribution.get(i).getLeft() == null || distribution.get(i+1).getLeft() == null) {
                continue;
            }
            int actualProduct = distribution.get(i).getLeft().getId();
            int nextProduct = distribution.get(i + 1).getLeft().getId();
            totalSimilarity += similarityTable.get(actualProduct).get(nextProduct);
        }
        if (distribution.getLast().getLeft() != null && distribution.getFirst().getLeft() != null) {
            int actualProduct = distribution.getLast().getLeft().getId();
            int nextProduct = distribution.getFirst().getLeft().getId();
            totalSimilarity += similarityTable.get(actualProduct).get(nextProduct);
        }
        return Math.round(totalSimilarity * 1e5) / 1e5;
    }

    /**
     * Devuelve una representación en forma de string de la solución.
     *
     * @return La representación en forma de string de la solución, indicando:
     * <p>
     * - El nombre de la solución.
     * <p>
     * - El identificador del catálogo.
     * <p>
     * - El identificador de la estantería.
     * <p>
     * - La heurística utilizada en la solución.
     * <p>
     * - El algoritmo utilizado en la solución.
     * <p>
     * - La puntuación calculada para la solución.
     * <p>
     * - La distribución de productos en la estantería junto con sus restricciones, si existen.
     * <p>
     */

    @Override
    public String toString() {
        StringBuilder distributionString = new StringBuilder();
        for (Pair<Product, Set<String>> pair : distribution) {
            String productName = (pair.getLeft() != null) ? pair.getLeft().getName() : "null";
            String restrictions = (pair.getRight() != null) ? pair.getRight().toString() : "null";
            distributionString.append("(").append(productName)
                    .append(", ").append(restrictions).append("), ");
        }
        if (!distribution.isEmpty()) {
            distributionString.setLength(distributionString.length() - 2); // Remove last comma and space
        }


        return "{" + solutionName + ", Catalog: " + idCatalog + ", Shelf: " + idShelf +
                ", Heuristic: " + heuristic + ", Algorithm: " + algorithm + ", Puntuation: "
                + mark + ", Valid: "+ valid + "\nDistribution: " + distributionString + "}\n\n";
    }

    /**
     * Devuelve una representación en forma de string de la solución.
     *
     * @return La representación en forma de string de la solución, indicando:
     * <p>
     * - El nombre de la solución.
     * <p>
     * - El identificador del catálogo.
     * <p>
     * - El identificador de la estantería.
     * <p>
     * - La heurística utilizada en la solución.
     * <p>
     * - El algoritmo utilizado en la solución.
     * <p>
     * - La puntuación calculada para la solución.
     * <p>
     * - La validez de la solucón.
     * <p>
     */
    public String toString1() {
        return "{" + solutionName + ", Catalog: " + idCatalog + ", Shelf: " + idShelf +
                ", Heuristic: " + heuristic + ", Algorithm: " + algorithm + ", Mark: "
                + mark + ", Valid: "+ valid + "}\n";
    }

}
