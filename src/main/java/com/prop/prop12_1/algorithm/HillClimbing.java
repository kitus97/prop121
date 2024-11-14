package com.prop.prop12_1.algorithm;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HillClimbing implements Algorithm{

    private List<Set<String>> shelf;
    private List<Pair<Integer, Set<String>>> products;

    public HillClimbing() {}

    @Override
    public Pair <Double, List<Pair<Integer, Set<String>>>> generateSolution(List<Set<String>> shelf, List<Pair<Integer, Set<String>>> products, List<List<Double>> similarityTable) {
        this.shelf = shelf;
        this.products = products;

        State.setShelf(shelf);
        State.setSimilarityTable(similarityTable);

        State initialState = generateInitialState();

        State finalState = hillClimbingAlgorithm(initialState);
        System.out.println("Solucion final: " + finalState.calculateHeuristic());

        return Pair.of(finalState.calculateHeuristic(), finalState.getSolution());
    }

    private State hillClimbingAlgorithm(State initialState) {
        State actualState = initialState;
        boolean upgrade = true;

        while (upgrade) {
            upgrade = false;
            List<State> neighbours = actualState.generateNeighbours();
            State bestNeighbour = actualState;

            for (State neighbour : neighbours) {
                if (neighbour.calculateHeuristic() > bestNeighbour.calculateHeuristic()) {
                    bestNeighbour = neighbour;
                    upgrade = true;
                }
            }

            if (upgrade) {
                actualState = bestNeighbour;
            }
        }

        return actualState;
    }

     private State generateInitialState() {
        List<Pair<Integer, Set<String>>> initialSolution = new ArrayList<>();

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
            if (!assigned) {
                initialSolution.add(null);
            }
        }

        State initialState = new State(initialSolution, products);
        System.out.println("Solucion inicial: " + initialState.calculateHeuristic());
        return initialState;
    }


}
