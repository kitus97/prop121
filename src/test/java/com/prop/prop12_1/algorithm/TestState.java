package com.prop.prop12_1.algorithm;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestState {

    private State state;

    @Mock
    private List<Set<String>> shelfMock;

    @Mock
    private List<List<Double>> similarityTableMock;

    private List<Pair<Integer, Set<String>>> solution;
    private List<Pair<Integer, Set<String>>> products;

    @BeforeEach
    public void setUpBeforeTest() {
        MockitoAnnotations.openMocks(this);

        solution = new ArrayList<>();
        solution.add(new ImmutablePair<>(0, Set.of("A", "B")));
        solution.add(new ImmutablePair<>(null, Set.of("C", "D")));
        solution.add(new ImmutablePair<>(2, Set.of("E", "F")));

        products = new ArrayList<>();
        products.add(new ImmutablePair<>(0, Set.of("A", "B")));
        products.add(new ImmutablePair<>(2, Set.of("E", "F")));

        State.setShelf(shelfMock);
        State.setSimilarityTable(similarityTableMock);

        state = new State(solution, products);
    }

    @Test
    public void testGenerateNeighbours() {
        when(shelfMock.get(0)).thenReturn(Set.of("A", "B"));
        when(shelfMock.get(1)).thenReturn(Set.of("C", "D"));
        when(shelfMock.get(2)).thenReturn(Set.of("E", "F"));

        State neighbour = mock(State.class);
        when(neighbour.getSolution()).thenReturn(solution);

        List<State> neighbours = state.generateNeighbours();

        assertNotNull(neighbours);
        assertFalse(neighbours.isEmpty());

    }

    @Test
    public void testGenerateNeighboursWithValidSwap() {
        State.setShelf(List.of(
                Set.of("A", "B"),
                Set.of("A", "B"),
                Set.of("E", "F")
        ));

        List<Pair<Integer, Set<String>>> testSolution = new ArrayList<>();
        testSolution.add(new ImmutablePair<>(0, Set.of("A", "B")));
        testSolution.add(new ImmutablePair<>(2, Set.of("A", "B")));

        List<Pair<Integer,Set<String>>> testProducts = new ArrayList<>();
        testProducts.add(new ImmutablePair<>(0, Set.of("A", "B")));
        testProducts.add(new ImmutablePair<>(2, Set.of("A", "B")));


        State state = new State(testSolution, testProducts);

        List<State> neighbours = state.generateNeighbours();

        assertFalse(neighbours.isEmpty());
        assertTrue(neighbours.stream().anyMatch(neighbour ->
                neighbour.getSolution().get(0).equals(new ImmutablePair<>(2, Set.of("A", "B"))) &&
                        neighbour.getSolution().get(1).equals(new ImmutablePair<>(0, Set.of("A", "B")))
        ));
    }


    @Test
    public void testSwapWithValidIndices() {
        when(shelfMock.get(0)).thenReturn(Set.of("A", "B"));
        when(shelfMock.get(1)).thenReturn(Set.of("A", "B"));

        State neighbour = new State(solution, products);
        boolean swapped = neighbour.getSolution().get(0).getRight().equals(shelfMock.get(1));

        assertTrue(swapped);
    }

    @Test
    public void testSwapWithInvalidIndices() {
        when(shelfMock.get(0)).thenReturn(Set.of("A", "B"));
        when(shelfMock.get(1)).thenReturn(Set.of("X", "Y"));

        State neighbour = new State(solution, products);
        boolean swapped = neighbour.getSolution().get(0).getRight().equals(shelfMock.get(1));

        assertFalse(swapped);
    }


    @Test
    public void testCalculateHeuristic() {
        when(similarityTableMock.get(0)).thenReturn(List.of(1.0, 0.5, 0.2));
        when(similarityTableMock.get(1)).thenReturn(List.of(0.5, 1.0, 0.3));
        when(similarityTableMock.get(2)).thenReturn(List.of(0.2, 0.3, 1.0));

        double heuristic = state.calculateHeuristic();

        assertEquals(0.2, heuristic, 0.0001);
    }

    @Test
    public void testCalculateHeuristic2() {
        when(similarityTableMock.get(0)).thenReturn(List.of(1.0, 0.5, 0.2));
        when(similarityTableMock.get(1)).thenReturn(List.of(0.5, 1.0, 0.3));
        when(similarityTableMock.get(2)).thenReturn(List.of(0.2, 0.3, 1.0));

        solution = new ArrayList<>();
        solution.add(new ImmutablePair<>(0, Set.of("A", "B")));
        solution.add(new ImmutablePair<>(1, Set.of("C", "D")));
        solution.add(new ImmutablePair<>(null, Set.of("E", "F")));

        State state = new State(solution, products);
        double heuristic = state.calculateHeuristic();
        assertEquals(0.5, heuristic, 0.0001);
    }

    @Test
    public void testSetShelf() {
        State.setShelf(shelfMock);
        assertEquals(shelfMock, shelfMock);
    }

    @Test
    public void testSetSimilarityTable() {
        State.setSimilarityTable(similarityTableMock);
        assertEquals(similarityTableMock, similarityTableMock);
    }

    @Test
    public void testGetSimilarity_Valid() {
        List<List<Double>> mockSimilarityTable = new ArrayList<>();
        mockSimilarityTable.add(List.of(1.0, 0.5, 0.2));
        mockSimilarityTable.add(List.of(0.5, 1.0, 0.3));
        mockSimilarityTable.add(List.of(0.2, 0.3, 1.0));

        State.setSimilarityTable(mockSimilarityTable);

        double similarity = State.getSimilarity(0, 1);
        assertEquals(0.5, similarity, 0.0001);
    }


    @Test
    public void testGetSimilarity_Invalid() {
        assertThrows(IllegalArgumentException.class, () -> State.getSimilarity(-1, 1));
        assertThrows(IllegalArgumentException.class, () -> State.getSimilarity(0, 99));
    }

    @Test
    public void testGetSolution() {
        List<Pair<Integer, Set<String>>> returnedSolution = state.getSolution();

        assertNotNull(returnedSolution);
        assertEquals(solution, returnedSolution);
        assertEquals(3, returnedSolution.size());
        assertEquals(new ImmutablePair<>(0, Set.of("A", "B")), returnedSolution.get(0));
        assertNull(returnedSolution.get(1).getLeft());
        assertEquals(new ImmutablePair<>(2, Set.of("E", "F")), returnedSolution.get(2));
    }
}
