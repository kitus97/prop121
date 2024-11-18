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
