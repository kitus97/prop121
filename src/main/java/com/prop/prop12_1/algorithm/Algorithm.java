package com.prop.prop12_1.algorithm;

import com.prop.prop12_1.model.Characteristics;
import com.prop.prop12_1.model.Product;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Interfaz para definir un algoritmo de generación de soluciones.
 */
public interface Algorithm {

    /**
     * Genera una solución para la disposición de productos en una estantería.
     *
     * @param shelf            Lista de conjuntos de restricciones asociadas a cada posición de la estantería.
     * @param products         Lista de pairs que representan los productos disponibles y sus restricciones.
     * @param similarityTable  Matriz de similitudes entre los productos.
     * @return Un par que contiene:
     *         <ul>
     *             <li>Un pair que representa la puntuación heurística de la solución generada.</li>
     *             <li>Una lista de pairs que representan la disposición de los productos en el estante,
     *                 donde cada par contiene el índice del producto y su conjunto de restricciones asociado.</li>
     *         </ul>
     */
    Pair<Double, List<Pair<Integer, Set<String>>>> generateSolution(
            List<Set<String>> shelf,
            List<Pair<Integer, Set<String>>> products,
            List<List<Double>> similarityTable
    );
}
