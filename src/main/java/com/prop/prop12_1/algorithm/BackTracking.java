package com.prop.prop12_1.algorithm;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Set;

public class BackTracking {
    private ArrayList<Set<String>> shelf;
    private ArrayList<Pair<Integer, Set<String>>> products;
    private double milllorPuntuacio = Double.NEGATIVE_INFINITY;
    private ArrayList<Pair<Integer, Set<String>>> bestDistribution;

    @Override
    public ArrayList<Pair<Integer, Set<String>>> generateSolution(ArrayList<Set<String>> shelf, ArrayList<Pair<Integer, Set<String>>> products, ArrayList<ArrayList<Double>> similarityTable) {
        this.shelf = shelf;
        this.products = products;

        State.setShelf(shelf);
        State.setSimilarityTable(similarityTable);

        State inici = new State()

        State solucioGenerada = backTracking();

    }

    public void backTracking(State state, int index) {
        if (index == shelf.size()) {
            double puntuacio = state.calculateHeuristic();
            if (puntuacio > milllorPuntuacio) {
                milllorPuntuacio = puntuacio;
                bestDistribution = new ArrayList<>(state.getSolution());

            }
            return;
        }

        for (int i = 0; i < st)
    }
}
