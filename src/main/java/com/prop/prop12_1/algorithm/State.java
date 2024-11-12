package com.prop.prop12_1.algorithm;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class State {

    static private ArrayList<Set<String>> shelf;
    static private ArrayList<ArrayList<Double>> similarityTable;
    private ArrayList<Pair<Integer, Set<String>>> solution;
    private ArrayList<Pair<Integer, Set<String>>> products;

    public State(ArrayList<Pair<Integer, Set<String>>> solution, ArrayList<Pair<Integer, Set<String>>> products) {
        this.solution = new ArrayList<>(solution);
        this.products = new ArrayList<>(products);
    }

    private boolean swap(int idx1, int idx2) {
        Pair<Integer, Set<String>> product1 = solution.get(idx1);
        Pair<Integer, Set<String>> product2 = solution.get(idx2);
        if (product1 == null && product2 == null) {
            return false;
        } else if (product1 == null) {
            if (product2.getRight().equals(shelf.get(idx1))) {
                solution.set(idx1, product2);
                solution.set(idx2, product1);
                return true;
            }
        } else if (product2 == null) {
            if (product1.getRight().equals(shelf.get(idx2))) {
                solution.set(idx1, product2);
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

    public Double calculateHeuristic() {
        double totalSimilarity = 0.0;
        for (int i = 0; i < solution.size() - 1; i++) {
            if (solution.get(i) == null || solution.get(i+1) == null) {
                continue;
            }
            int actualProduct = solution.get(i).getLeft();
            int nextProduct = solution.get(i + 1).getLeft();
            totalSimilarity += similarityTable.get(actualProduct).get(nextProduct);
        }
        if (solution.getLast() != null && solution.getFirst() != null) {
            int actualProduct = solution.getLast().getLeft();
            int nextProduct = solution.getFirst().getLeft();
            totalSimilarity += similarityTable.get(actualProduct).get(nextProduct);
        }
        return Math.round(totalSimilarity * 1e5) / 1e5;
    }

    public void changeProducts(Pair<Integer, Set<String>> product1, Pair<Integer, Set<String>> product2, Integer j) {
        products.remove(product1);
        products.add(product2);
        solution.set(j, product1);
    }

    public ArrayList<Pair<Integer, Set<String>>> getSolution() {
        return solution;
    }

    public static void setShelf(ArrayList<Set<String>> shelf) {
        State.shelf = shelf;
    }

    public static void setSimilarityTable(ArrayList<ArrayList<Double>> similarityTable) {
        State.similarityTable = similarityTable;
    }
    public static double getSimilarity(int product1, int product2) {
        if (similarityTable == null || product1 < 0 || product2 < 0 ||
                product1 >= similarityTable.size() || product2 >= similarityTable.size()) {
            throw new IllegalArgumentException("Invalid product indices or uninitialized similarityTable");
        }
        return similarityTable.get(product1).get(product2);
    }
}
