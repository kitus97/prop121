package com.prop.prop12_1.algorithm;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Clase que representa un estado en el espacio de soluciones para poder ser resuleto con diferentes algoritmos.
 * Cada estado tiene la solución actual que tenemos y los productos que quedan por asignar.
 */
public class State {

    private static List<Set<String>> shelf;
    private static List<List<Double>> similarityTable;
    private List<Pair<Integer, Set<String>>> solution;
    private List<Pair<Integer, Set<String>>> products;

    /**
     * Constructor que inicializa un estado con una solución y una lista de productos restantes.
     *
     * @param solution la solución actual como una lista de pares (ID del producto, conjunto de restricciones)
     * @param products la lista de productos restantes
     */
    public State(List<Pair<Integer, Set<String>>> solution, List<Pair<Integer, Set<String>>> products) {
        this.solution = new ArrayList<>(solution);
        this.products = new ArrayList<>(products);
    }

    /**
     * Realiza un intercambio de productos entre dos posiciones de la solución.
     *
     * @param idx1 índice del primer producto
     * @param idx2 índice del segundo producto
     * @return true si el intercambio fue válido y realizado, false en caso contrario
     */
    private boolean swap(int idx1, int idx2) {
        Pair<Integer, Set<String>> product1 = solution.get(idx1);
        Pair<Integer, Set<String>> product2 = solution.get(idx2);
        if (product1 == null && product2 == null) {
            return false;
        } else if (product1 == null) {
            if (product2.getRight().equals(shelf.get(idx1))) {
                solution.set(idx1, product2);
                solution.set(idx2, null);
                return true;
            }
        } else if (product2 == null) {
            if (product1.getRight().equals(shelf.get(idx2))) {
                solution.set(idx1, null);
                solution.set(idx2, product1);
                return true;
            }
        } else {
            if (product1.getRight().equals(shelf.get(idx2))) {
                solution.set(idx1, product2);
                solution.set(idx2, product1);
                return true;
            }
        }
        return false;
    }

    /**
     * Genera los estados vecinos realizando intercambios bien entre productos ya asignados
     * o bien entre productos que no se han podido asignar pero igual permiten mejor puntuación.
     *
     * @return una lista de estados vecinos generados a partir de la solución actual
     */
    public List<State> generateNeighbours() {
        List<State> neighbours = new ArrayList<>();
        for (int i = 0; i < solution.size(); i++) {
            for (int j = i + 1; j < solution.size(); j++) {
                State neighbour = new State(solution, products);
                if (neighbour.swap(i, j)) {
                    neighbours.add(neighbour);
                }
            }
        }

        for (Pair<Integer, Set<String>> product : products) {
            for (int j = 0; j < solution.size(); j++) {
                if (product != null && product.getRight().equals(shelf.get(j))) {
                    State neighbour = new State(solution, products);
                    neighbour.changeProducts(product, solution.get(j), j);
                    neighbours.add(neighbour);
                }
            }
        }
        return neighbours;
    }

    /**
     * Calcula una heurística basada en la similitud total entre productos consecutivos en la solución.
     *
     * @return el valor de la heurística como un número doble redondeado a 5 decimales
     */
    public Double calculateHeuristic() {
        double totalSimilarity = 0.0;
        for (int i = 0; i < solution.size() - 1; i++) {
            if (solution.get(i).getLeft() == null || solution.get(i + 1).getLeft() == null) {
                continue;
            }

            int actualProduct = solution.get(i).getLeft();
            int nextProduct = solution.get(i + 1).getLeft();
            totalSimilarity += similarityTable.get(actualProduct).get(nextProduct);
        }

        if (solution.get(solution.size() - 1).getLeft() != null && solution.get(0).getLeft() != null) {
            int actualProduct = solution.get(solution.size() - 1).getLeft();
            int nextProduct = solution.get(0).getLeft();
            totalSimilarity += similarityTable.get(actualProduct).get(nextProduct);
        }
        return Math.round(totalSimilarity * 1e5) / 1e5;
    }

    /**
     * Cambia productos entre la solución actual y los productos restantes.
     *
     * @param product1 el producto a asignar a la solución
     * @param product2 el producto a mover a los productos restantes
     * @param j        el índice de la posición donde asignar el producto
     */
    private void changeProducts(Pair<Integer, Set<String>> product1, Pair<Integer, Set<String>> product2, Integer j) {
        products.remove(product1);
        products.add(product2);
        solution.set(j, product1);
    }

    /**
     * Obtiene la solución actual del estado.
     *
     * @return una lista de pares representando la solución actual
     */
    public List<Pair<Integer, Set<String>>> getSolution() {
        return solution;
    }

    /**
     * Configura la estantería estática para el estado.
     *
     * @param shelf la lista de conjuntos que representa las estanterías disponibles
     */
    public static void setShelf(List<Set<String>> shelf) {
        State.shelf = shelf;
    }

    /**
     * Configura la tabla de similitudes estática para el estado.
     *
     * @param similarityTable una lista bidimensional representando las similitudes entre productos
     */
    public static void setSimilarityTable(List<List<Double>> similarityTable) {
        State.similarityTable = similarityTable;
    }

    /**
     * Obtiene la similitud entre dos productos según la tabla de similitudes.
     *
     * @param product1 el índice del primer producto
     * @param product2 el índice del segundo producto
     * @return el valor de similitud entre los dos productos
     * @throws IllegalArgumentException si los índices son inválidos o la tabla no está inicializada
     */
    public static double getSimilarity(int product1, int product2) {
        if (similarityTable == null || product1 < 0 || product2 < 0 ||
                product1 >= similarityTable.size() || product2 >= similarityTable.size()) {
            throw new IllegalArgumentException("Invalid product indices or uninitialized similarityTable");
        }
        return similarityTable.get(product1).get(product2);
    }
}
