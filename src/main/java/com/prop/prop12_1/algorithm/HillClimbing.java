package com.prop.prop12_1.algorithm;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Implementación del algoritmo Hill Climbing para encontarar una buena distribución de productos.
 * Este algoritmo busca soluciones óptimas en el espacio de búsqueda a través de mejoras iterativas.
 */
public class HillClimbing implements Algorithm {

    private List<Set<String>> shelf;
    private List<Pair<Integer, Set<String>>> products;

    /**
     * Constructor por defecto.
     */
    public HillClimbing() {}

    /**
     * Genera una solución al problema utilizando el algoritmo Hill Climbing.
     *
     * @param shelf           una lista de conjuntos que representan las estanterías con sus restricciones
     * @param products        una lista de pares, donde cada par contiene un identificador y un conjunto de restricciones del producto
     * @param similarityTable una tabla de similitudes entre los productos
     * @return un par donde el primer elemento es la puntuación de la solución y el segundo es la asignación de productos a estanterías
     */
    @Override
    public Pair<Double, List<Pair<Integer, Set<String>>>> generateSolution(List<Set<String>> shelf, List<Pair<Integer, Set<String>>> products, List<List<Double>> similarityTable) {
        this.shelf = shelf;
        this.products = products;

        // Configurar estanterías y tabla de similitudes en la clase State
        State.setShelf(shelf);
        State.setSimilarityTable(similarityTable);

        // Generar estado inicial
        State initialState = generateInitialState();

        // Ejecutar el algoritmo Hill Climbing
        State finalState = hillClimbingAlgorithm(initialState);


        // Retornar el puntaje de la solución y la asignación final
        return Pair.of(finalState.calculateHeuristic(), finalState.getSolution());
    }

    /**
     * Implementa el algoritmo Hill Climbing para encontrar la solución óptima.
     *
     * @param initialState el estado inicial desde el cual comienza el algoritmo
     * @return el mejor estado encontrado durante la ejecución
     */
    private State hillClimbingAlgorithm(State initialState) {
        State actualState = initialState;
        boolean upgrade = true;

        // Iterar mientras haya mejoras posibles
        while (upgrade) {
            upgrade = false;
            List<State> neighbours = actualState.generateNeighbours();
            State bestNeighbour = actualState;

            // Buscar el mejor vecino
            for (State neighbour : neighbours) {
                if (neighbour.calculateHeuristic() > bestNeighbour.calculateHeuristic()) {
                    bestNeighbour = neighbour;
                    upgrade = true;
                }
            }

            // Actualizar el estado actual si se encuentra un mejor vecino
            if (upgrade) {
                actualState = bestNeighbour;
            }
        }

        return actualState;
    }

    /**
     * Genera el estado inicial asignando productos a las estanterías basándose en las restricciones.
     *
     * @return el estado inicial generado
     */
    private State generateInitialState() {
        List<Pair<Integer, Set<String>>> initialSolution = new ArrayList<>();

        // Asignar productos a las estanterías según las restricciones
        for (Set<String> restriction : shelf) {
            boolean assigned = false;
            for (Pair<Integer, Set<String>> product : products) {
                if (product != null && product.getRight().equals(restriction)) {
                    initialSolution.add(product);
                    products.remove(product);
                    assigned = true;
                    break;
                }
            }
            // Si no se asignó un producto, agregar un valor nulo
            if (!assigned) {
                initialSolution.add(Pair.of(null, restriction));
            }
        }

        // Crear y retornar el estado inicial
        State initialState = new State(initialSolution, products);
        return initialState;
    }
}
