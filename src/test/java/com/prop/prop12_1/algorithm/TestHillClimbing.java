package com.prop.prop12_1.algorithm;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestHillClimbing {

    private HillClimbing hillClimbing;
    private List<Set<String>> shelf;
    private List<Pair<Integer, Set<String>>> products;
    private List<List<Double>> similarityTable;

    @BeforeEach
    public void setUp() {
        hillClimbing = new HillClimbing();

        shelf = List.of(
                Set.of("A", "B"),
                Set.of("C", "D"),
                Set.of("E", "F")
        );

        products = new ArrayList<>();
        products.add(Pair.of(0, Set.of("A", "B")));
        products.add(Pair.of(1, Set.of("C", "D")));
        products.add(Pair.of(2, Set.of("E", "F")));

        similarityTable = List.of(
                List.of(1.0, 0.5, 0.2),
                List.of(0.5, 1.0, 0.3),
                List.of(0.2, 0.3, 1.0)
        );

        State.setShelf(shelf);
        State.setSimilarityTable(similarityTable);
    }

    @Test
    public void testGenerateSolution() {

        Pair<Double, List<Pair<Integer, Set<String>>>> result = hillClimbing.generateSolution(shelf, products, similarityTable);

        assertNotNull(result);
        assertNotNull(result.getLeft());
        assertNotNull(result.getRight());
        assertEquals(3, result.getRight().size());
    }

    @Test
    public void testGenerateSolutionWithNulls() {
        products = new ArrayList<>();
        products.add(Pair.of(0, Set.of("A", "B")));
        products.add(Pair.of(1, Set.of("X", "Y")));
        products.add(Pair.of(2, Set.of("E", "F")));

        similarityTable = List.of(
                List.of(1.0, 0.5, 0.2),
                List.of(0.5, 1.0, 0.3),
                List.of(0.2, 0.3, 1.0)
        );
        Pair<Double, List<Pair<Integer, Set<String>>>> result = hillClimbing.generateSolution(shelf, products, similarityTable);

        assertNotNull(result);
        assertNotNull(result.getLeft());
        assertNotNull(result.getRight());
        assertEquals(3, result.getRight().size());
    }
}
