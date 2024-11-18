package com.prop.prop12_1.algorithm;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Clase que implementa un algoritmo Greedy para la generación de soluciones
 * relacionadas con la disposición de productos en un estante.
 */
public class Greedy implements Algorithm {
    /**
     * Constructor por defecto de la clase Greedy.
     */
    public Greedy() {
    }

    /**
     * Genera una solución greedy para la disposición de productos en una estantería.
     *
     * @param shelf           Lista de restricciones asociadas a cada posición del estante.
     * @param products        Lista de pairs que representan los productos disponibles y sus restricciones.
     * @param similarityTable Matriz de similitudes entre los productos.
     * @return Un pair que contiene:
     *         <ul>
     *             <li>Un valor double que representa la puntuación heurística de la solución generada.</li>
     *             <li>Una lista de pairs que representan la disposición de los productos en la estantería,
     *                 donde cada pair contiene el índice del producto y su conjunto de restricciones asociado.</li>
     *         </ul>
     */


    @Override

    public Pair<Double, List<Pair<Integer, Set<String>>>> generateSolution(
            List<Set<String>> shelf,
            List<Pair<Integer, Set<String>>> products,
            List<List<Double>> similarityTable) {



        State.setShelf(shelf);
        State.setSimilarityTable(similarityTable);

        List<Pair<Integer, Set<String>>> solution = new ArrayList<>();
        for (int i = 0; i < shelf.size(); i++) {
            solution.add(Pair.of(null, shelf.get(i)));
        }

        int shelfSize = shelf.size();
        if (shelfSize > 0) {
            Set<String> currentRestrictions = shelf.getFirst();
            for (int j = 0; j < products.size(); j++) {
                if (products.get(j).getRight().equals(currentRestrictions)) {
                    solution.set(0, products.get(j));
                    products.remove(j);
                    break;
                }
            }
        }

        for (int i = 1; i < shelfSize; i++) {
            double maxsim = 0;
            int idAnt = -1;
            int idAct = -1;
            if (solution.get(i - 1).getLeft() != null) idAnt = solution.get(i - 1).getLeft();
            for (int j = 0; j < products.size(); j++) {
                if (products.get(j).getRight().equals(shelf.get(i))) {
                    if (idAnt == -1) {
                        idAct = j;
                        break;
                    } else {
                        if (maxsim <= similarityTable.get(idAnt).get(products.get(j).getLeft())) {
                            idAct = j;
                            maxsim = similarityTable.get(idAnt).get(products.get(j).getLeft());
                        }
                    }
                }
            }
            if (idAct != -1){
                solution.set(i, products.get(idAct));
                products.remove(idAct);
            }
            else solution.set(i, Pair.of(null, shelf.get(i)));

        }
        State s = new State(solution, products);


        System.out.println("Mejor puntuación encontrada: " + s.calculateHeuristic());
        return Pair.of(s.calculateHeuristic(), solution);
    }

}

