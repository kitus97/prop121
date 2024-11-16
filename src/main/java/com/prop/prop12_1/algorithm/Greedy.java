package com.prop.prop12_1.algorithm;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Greedy {
    private List<Set<String>> shelf;
    private List<Pair<Integer, Set<String>>> products;
    private double maxScore;
    private List<Pair<Integer, Set<String>>> bestDistribution;


    public Greedy() {
    }

    public Pair<Double, List<Pair<Integer, Set<String>>>> generateSolution(
            List<Set<String>> shelf,
            List<Pair<Integer, Set<String>>> products,
            List<List<Double>> similarityTable) {
        this.shelf = shelf;
        this.products = products;
        this.maxScore = 0.0;
        this.bestDistribution = null;


        State.setShelf(shelf);
        State.setSimilarityTable(similarityTable);

        List<Pair<Integer, Set<String>>> solution = new ArrayList<>();
        for (int i = 0; i < shelf.size(); i++) {
            solution.add(Pair.of(null, shelf.get(i)));
        }

        Boolean primero = false;
        int shelfSize = shelf.size();
        if (shelfSize > 0) {
            for (int j = 0; j < products.size(); j++) {
                Set<String> currentRestrictions = shelf.get(0);
                if (products.get(j).getRight().equals(currentRestrictions)) {
                    solution.set(0, products.get(j));
                    products.remove(j);
                    primero = true;
                    break;
                }
            }
        }
        if (!primero) {
            solution.set(0, Pair.of(null, shelf.getFirst()));
        }

        for (int i = 1; i < shelfSize; i++) {
            double maxsim = 0;
            int idAnt = -1;
            int idAct = -1;
            if (solution.get(i - 1).getLeft() != null) idAnt = solution.get(i - 1).getLeft();
            for (int j = 0; j < products.size(); ++j) {
                if (products.get(j).getRight().equals(shelf.get(i))) {
                    if (idAnt == -1) {
                        solution.set(i, products.get(j));
                        break;
                    } else {
                        if (maxsim < similarityTable.get(idAnt).get(j)) {
                            idAct = j;
                            maxsim = similarityTable.get(idAnt).get(j);
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

