package com.prop.prop12_1.model;
import com.prop.prop12_1.model.Product;
import com.prop.prop12_1.model.Solution;
import com.prop.prop12_1.exceptions.NotInterchangeableException;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TestSolution {

    private Solution solution;

    @BeforeEach
    void setUp() {
        List<Pair<Product, Set<String>>> distribution = new ArrayList<>();
        Product product1 = new Product(0, "Product1");
        Product product2 = new Product(1, "Product2");
        Product product3 = new Product(2, "Product3");

        distribution.add(Pair.of(product1, Set.of("Restriction1")));
        distribution.add(Pair.of(product2, Set.of("Restriction1")));
        distribution.add(Pair.of(product3, Set.of("Restriction1")));

        solution = new Solution("TestSolution", "Catalog1", "Shelf1", "Generated", "Algorithm1", 5.0, new ArrayList<>());
        solution.setDistribution(distribution);
    }

    @Test
    void testGetDistribution() {
        List<Pair<Product, Set<String>>> distribution = solution.getDistribution();
        assertEquals(3, distribution.size());
        assertEquals("Product1", distribution.get(0).getLeft().getName());
    }

    @Test
    void testSetValid() {
        solution.setValid(false);
        assertFalse(solution.getValid());
    }

    @Test
    void testDelete() {
        solution.delete();
        assertTrue(solution.deleted());
        assertNull(solution.getSolutionName());
        assertNull(solution.getIdCatalog());
    }

    @Test
    void testChangeProductsSuccess() {
        solution.changeProducts(0, 1);

        List<Pair<Product, Set<String>>> distribution = solution.getDistribution();
        assertEquals("Product2", distribution.get(0).getLeft().getName());
        assertEquals("Product1", distribution.get(1).getLeft().getName());
    }

    @Test
    void testChangeProductsDifferentRestrictions() {
        List<Pair<Product, Set<String>>> newDistribution = new ArrayList<>(solution.getDistribution());
        newDistribution.set(0, Pair.of(newDistribution.get(0).getLeft(), Set.of("Restriction2")));
        solution.setDistribution(newDistribution);

        assertThrows(NotInterchangeableException.class, () -> solution.changeProducts(0, 1));
    }


    @Test
    void testDeleteProduct() {
        solution.deleteProduct(1);

        assertNull(solution.getDistribution().get(1).getLeft());
    }

    @Test
    void testSetSolutionName() {
        solution.setSolutionName("NewSolutionName");
        assertEquals("NewSolutionName", solution.getSolutionName());
    }

    @Test
    void testSetMark() {
        solution.setMark(10.0);
        assertEquals(10.0, solution.getMark());
    }

    @Test
    void testToString() {
        String expected = "{TestSolution, Catalog: Catalog1, Shelf: Shelf1, Heuristic: Generated, Algorithm: Algorithm1, Puntuation: 5.0}\n";
        assertEquals(expected, solution.toString());
    }
    /*
    @Test
    void testToStringWithDistribution() {
        String expected = "{TestSolution, Catalog: Catalog1, Shelf: Shelf1, Heuristic: Generated, Algorithm: Algorithm1, Puntuation: 5.0" +
                "[{name='Product1', characteristics=[], restrictions=[]}, {name='Product2', characteristics=[], restrictions=[]}, {name='Product3', characteristics=[], restrictions=[]}]}\n";
        assertEquals(expected, solution.toString1());
    }
    */
    @Test
    void testDeleted() {
        assertFalse(solution.deleted());
        solution.delete();
        assertTrue(solution.deleted());
    }
}
