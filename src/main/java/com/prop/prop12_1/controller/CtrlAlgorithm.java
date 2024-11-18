package com.prop.prop12_1.controller;

import com.prop.prop12_1.algorithm.Algorithm;
import com.prop.prop12_1.algorithm.BackTracking;
import com.prop.prop12_1.algorithm.Greedy;
import com.prop.prop12_1.algorithm.HillClimbing;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Controlador para gestionar la ejecución de algoritmos de solución.
 * Podemos escoger si usar la matriz de similitudes que ya tenemos o generarla toda
 * con la formula de las características.
 * Proporciona acceso a tres algoritmos principales:
 * - Backtracking
 * - Hill Climbing
 * - Greedy
 */
public class CtrlAlgorithm {

    /**
     * Constructor por defecto.
     */
    public CtrlAlgorithm() {}

    /**
     * Obtiene la solución a un problema utilizando un algoritmo específico.
     *
     * @param shelf             una lista de conjuntos que representa las estanterías a rellenar y sus restricciones
     * @param products          una lista de pares, donde cada par contiene un identificador y un conjunto de restricciones del producto
     * @param algorithm         el identificador del algoritmo a usar (0 para Backtracking, 1 para Hill Climbing, 2 para Greedy)
     * @param generatedSimilarity un booleano que indica si la tabla de similitudes debe generarse dinámicamente
     * @return un par donde el primer elemento es la puntuación total de la solución y el segundo es la lista de pares asignados de productos a estanterías
     */
    public Pair<Double, List<Pair<Integer, Set<String>>>> getSolution(List<Set<String>> shelf, List<Pair<Integer, Set<String>>> products, int algorithm, boolean generatedSimilarity) {
        List<List<Double>> similarityTable;

        // Generar o recuperar la tabla de similitudes
        if (generatedSimilarity) {
            similarityTable = new CtrlProd().generateSimilarityTable();
        } else {
            similarityTable = new CtrlProd().getSimilarityTable();
        }
        Algorithm alg = null;
        // Seleccionar y ejecutar el algoritmo adecuado
        if (algorithm == 0) {
            alg = new BackTracking();
        } else if (algorithm == 1) {
            alg = new HillClimbing();
        } else if (algorithm == 2) {
            alg = new Greedy();
        }

        if (alg != null) {
            return alg.generateSolution(shelf, products, similarityTable);
        }
        // Retornar null si no se selecciona un algoritmo válido
        return null;
    }
}
