package com.prop.prop12_1.controller;

import com.prop.prop12_1.algorithm.BackTracking;
import com.prop.prop12_1.algorithm.HillClimbing;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestCtrlAlgorithm {

    @InjectMocks
    private CtrlAlgorithm ctrlAlgorithm;

    @Mock
    private CtrlProd ctrlProdMock;

    @Mock
    private HillClimbing hillClimbingMock;

    @Mock
    private BackTracking backTrackingMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetSolution_HillClimbing_WithGeneratedSimilarity() {
        List<Set<String>> shelf = List.of(Set.of("A", "B"));
        List<Pair<Integer, Set<String>>> products = List.of(
                new ImmutablePair<>(1, Set.of("A")),
                new ImmutablePair<>(2, Set.of("B"))
        );

        List<List<Double>> similarityTable = List.of(
                List.of(1.0, 0.5),
                List.of(0.5, 1.0)
        );

        Pair<Double, List<Pair<Integer, Set<String>>>> expectedSolution =
                new ImmutablePair<>(10.0, products);

        when(ctrlProdMock.generateSimilarityTable()).thenReturn(similarityTable);
        when(hillClimbingMock.generateSolution(shelf, products, similarityTable))
                .thenReturn(expectedSolution);

        ctrlAlgorithm = new CtrlAlgorithm() {
            public List<List<Double>> getSimilarityTable(boolean generatedSimilarity) {
                return generatedSimilarity ? ctrlProdMock.generateSimilarityTable() : ctrlProdMock.getSimilarityTable();
            }
        };

        Pair<Double, List<Pair<Integer, Set<String>>>> solution =
                ctrlAlgorithm.getSolution(shelf, products, 0, true);

        assertNotNull(solution);
        assertEquals(expectedSolution, solution);
    }

    @Test
    public void testGetSolution_BackTracking_WithoutGeneratedSimilarity() {
        List<Set<String>> shelf = List.of(Set.of("X", "Y"));
        List<Pair<Integer, Set<String>>> products = List.of(
                new ImmutablePair<>(3, Set.of("X")),
                new ImmutablePair<>(4, Set.of("Y"))
        );

        List<List<Double>> similarityTable = List.of(
                List.of(1.0, 0.7),
                List.of(0.7, 1.0)
        );

        Pair<Double, List<Pair<Integer, Set<String>>>> expectedSolution =
                new ImmutablePair<>(15.0, products);

        when(ctrlProdMock.getSimilarityTable()).thenReturn(similarityTable);
        when(backTrackingMock.generateSolution(shelf, products, similarityTable))
                .thenReturn(expectedSolution);

        ctrlAlgorithm = new CtrlAlgorithm() {
            public List<List<Double>> getSimilarityTable(boolean generatedSimilarity) {
                return generatedSimilarity ? ctrlProdMock.generateSimilarityTable() : ctrlProdMock.getSimilarityTable();
            }
        };

        Pair<Double, List<Pair<Integer, Set<String>>>> solution =
                ctrlAlgorithm.getSolution(shelf, products, 1, false);

        assertNotNull(solution);
        assertEquals(expectedSolution, solution);
    }

    @Test
    public void testGetSolution_InvalidAlgorithm() {
        List<Set<String>> shelf = List.of(Set.of("X", "Y"));
        List<Pair<Integer, Set<String>>> products = List.of(
                new ImmutablePair<>(3, Set.of("X")),
                new ImmutablePair<>(4, Set.of("Y"))
        );

        Pair<Double, List<Pair<Integer, Set<String>>>> solution =
                ctrlAlgorithm.getSolution(shelf, products, 99, false);

        assertNull(solution);
    }


}
