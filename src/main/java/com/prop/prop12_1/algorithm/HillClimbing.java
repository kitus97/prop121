package com.prop.prop12_1.algorithm;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HillClimbing implements Algorithm{

    private ArrayList<List<String>> shelf;
    private ArrayList<Pair<Integer, List<String>>> products;

    public HillClimbing() {}

    @Override
    public ArrayList<Pair<Integer, List<String>>> generateSolution(ArrayList<List<String>> shelf, ArrayList<Pair<Integer, List<String>>> products, ArrayList<ArrayList<Double>> similarityTable) {
        this.shelf = shelf;
        this.products = products;

        State.setShelf(shelf);
        State.setSimilarityTable(similarityTable);

        State initialState = generateInitialState();

        State finalState = hillClimbingAlgorithm(initialState);
        System.out.println("Solucion final: " + finalState.calculateHeuristic());

        return finalState.getSolution();
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
        ArrayList<Pair<Integer, List<String>>> initialSolution = new ArrayList<>();

        for (List<String> restriction : shelf) {
            boolean assigned = false;
            for (Pair<Integer, List<String>> product : products) {
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
