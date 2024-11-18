package com.prop.prop12_1.algorithm;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Implementación del algoritmo de Backtracking para encontrar soluciones de distribución.
 * Busca todas las combinaciones posibles para encontrar la mejor distribución de productos
 * en estanterías respetando restricciones y maximizando la similitud en un tiempo reducido.
 */
public class BackTracking implements Algorithm {

    private List<Set<String>> shelf;
    private List<Pair<Integer, Set<String>>> products;
    private double maxScore;
    private List<Pair<Integer, Set<String>>> bestDistribution;
    private long startTime;
    private long timeLimit;

    /**
     * Constructor por defecto.
     */
    public BackTracking() {}

    /**
     * Genera la mejor solución posible utilizando el algoritmo de Backtracking.
     *
     * @param shelf           una lista de conjuntos que representan las restricciones de las estanterías
     * @param products        una lista de pares, donde cada par contiene un identificador y un conjunto de restricciones del producto
     * @param similarityTable una tabla de similitudes entre los productos
     * @return un par donde el primer elemento es la puntuación máxima obtenida y el segundo es la mejor distribución encontrada
     */
    @Override
    public Pair<Double, List<Pair<Integer, Set<String>>>> generateSolution(
            List<Set<String>> shelf,
            List<Pair<Integer, Set<String>>> products,
            List<List<Double>> similarityTable) {
        this.shelf = shelf;
        this.products = products;
        this.maxScore = 0.0;
        this.bestDistribution = null;
        this.timeLimit = 30000;

        // Configurar las restricciones y la tabla de similitudes en el estado
        State.setShelf(shelf);
        State.setSimilarityTable(similarityTable);

        // Inicializar el tiempo y la solución inicial
        this.startTime = System.currentTimeMillis();
        List<Pair<Integer, Set<String>>> initialSolution = new ArrayList<>();
        for (int i = 0; i < shelf.size(); i++) {
            initialSolution.add(Pair.of(null, shelf.get(i)));
        }

        // Ejecutar el algoritmo de Backtracking
        backTracking(0, initialSolution, new ArrayList<>(products));

        // Si no se encuentra una solución, generar una distribución vacía
        if (bestDistribution == null) {
            bestDistribution = new ArrayList<>();
            for (int i = 0; i < shelf.size(); i++) {
                bestDistribution.add(Pair.of(null, shelf.get(i)));
            }
        }
        return Pair.of(maxScore, bestDistribution);
    }

    /**
     * Método recursivo que implementa el algoritmo de Backtracking.
     *
     * @param index             el índice actual de la estantería
     * @param currentSolution   la solución parcial en construcción
     * @param remainingProducts los productos restantes por asignar
     */
    private void backTracking(int index, List<Pair<Integer, Set<String>>> currentSolution, List<Pair<Integer, Set<String>>> remainingProducts) {
        // Comprobar límite de tiempo
        if (System.currentTimeMillis() - startTime > timeLimit) {
            return;
        }

        // Si se han asignado todas las estanterías, calcular la heurística
        if (index == shelf.size()) {
            State currentState = new State(currentSolution, remainingProducts);
            double currentScore = currentState.calculateHeuristic();
            if (currentScore >= maxScore) {
                maxScore = currentScore;
                bestDistribution = new ArrayList<>(currentSolution);
            }
            return;
        }

        // Obtener las restricciones de la estantería actual
        Set<String> currentRestrictions = shelf.get(index);
        boolean assigned = false;

        // Probar cada producto restante
        for (int i = 0; i < remainingProducts.size(); i++) {
            Pair<Integer, Set<String>> product = remainingProducts.get(i);

            // Verificar si el producto cumple con las restricciones
            if (currentRestrictions.equals(product.getRight())) {
                currentSolution.set(index, product);
                remainingProducts.remove(i);
                assigned = true;

                // Llamada recursiva para la siguiente estantería
                backTracking(index + 1, currentSolution, remainingProducts);

                // Deshacer los cambios para explorar otras posibilidades
                remainingProducts.add(i, product);
                currentSolution.set(index, Pair.of(null, currentRestrictions));
            }
        }

        // Si ningún producto cumple las restricciones, avanzar sin asignar producto
        if (!assigned) {
            backTracking(index + 1, currentSolution, remainingProducts);
        }
    }


}
