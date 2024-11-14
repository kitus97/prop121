package com.prop.prop12_1.algorithm;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BackTracking {
    private ArrayList<List<String>> shelf;
    private ArrayList<Pair<Integer, List<String>>> products;
    private double maxScore;
    private ArrayList<Pair<Integer, List<String>>> bestDistribution;
    private long startTime;
    private long timeLimit;

    public BackTracking() {}

    public ArrayList<Pair<Integer, List<String>>> generateSolution(
            ArrayList<List<String>> shelf,
            ArrayList<Pair<Integer, List<String>>> products,
            ArrayList<ArrayList<Double>> similarityTable) {
        this.shelf = shelf;
        this.products = products;
        this.maxScore = 0.0;
        this.bestDistribution = null;
        this.timeLimit = 80000;

        State.setShelf(shelf);
        State.setSimilarityTable(similarityTable);
        this.startTime = System.currentTimeMillis();
        ArrayList<Pair<Integer, List<String>>> initialSolution = new ArrayList<>();
        for (int i = 0; i < shelf.size(); i++) {
            initialSolution.add(null);
        }


        backTracking(0, initialSolution, new ArrayList<>(products));

        if (bestDistribution == null) {
            bestDistribution = new ArrayList<>();
            for (int i = 0; i < shelf.size(); i++) {
                bestDistribution.add(null);
            }
        }

        System.out.println("Mejor puntuación encontrada: " + maxScore);
        return bestDistribution;
    }

    private void backTracking(int index, ArrayList<Pair<Integer, List<String>>> currentSolution, ArrayList<Pair<Integer, List<String>>> remainingProducts) {
        if (System.currentTimeMillis() - startTime > timeLimit) {
            return;
        }
        if (index == shelf.size()) {

            State currentState = new State(currentSolution, remainingProducts);
            double currentScore = currentState.calculateHeuristic();
            if (currentScore >= maxScore) {
                maxScore = currentScore;
                bestDistribution = new ArrayList<>(currentSolution);
            }
            return;
        }
        List<String> currentRestrictions = shelf.get(index);
        boolean trobat = false;
        for (int i = 0; i < remainingProducts.size(); i++) {
            Pair<Integer, List<String>> product = remainingProducts.get(i);

            if (currentRestrictions.equals(product.getRight())) {
                currentSolution.set(index, product);
                remainingProducts.remove(i);
                trobat = true;
                backTracking(index + 1, currentSolution, remainingProducts);
                remainingProducts.add(i, product);
                currentSolution.set(index, null);
            }
        }
        if (!trobat) {
            backTracking(index + 1, currentSolution, remainingProducts);
        }
    }

    private double computeSimilarity(Pair<Integer, List<String>> product, ArrayList<Pair<Integer, List<String>>> currentSolution, int index) {
        if (index > 0 && currentSolution.get(index - 1) != null) {
            int previousProduct = currentSolution.get(index - 1).getLeft();
            int currentProduct = product.getLeft();
            return State.getSimilarity(previousProduct, currentProduct);
        }
        return 0.0;
    }
}
