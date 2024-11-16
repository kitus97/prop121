package com.prop.prop12_1.model;

import com.prop.prop12_1.controller.CtrlProd;
import com.prop.prop12_1.exceptions.InvalidProductRestrictionException;
import com.prop.prop12_1.exceptions.NotInterchangeableException;
import com.prop.prop12_1.exceptions.ProductNotFoundException;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;


import java.util.ArrayList;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class TestSolution {
    private Solution solution;
    private Product productMock1;
    private Product productMock2;
    private CtrlProd ctrlProdMock;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        productMock1 = mock(Product.class);
        productMock2 = mock(Product.class);
        ctrlProdMock = mock(CtrlProd.class);

        Field ctrlProdField = Solution.class.getDeclaredField("ctrlProd");
        ctrlProdField.setAccessible(true);

        ctrlProdField.set(null, ctrlProdMock);

        when(ctrlProdMock.findProduct("Product1")).thenReturn(productMock1);
        when(ctrlProdMock.findProduct("NonExistentProduct")).thenReturn(null);
        when(ctrlProdMock.generateSimilarityTable()).thenReturn(List.of(
                List.of(1.0, 0.5),
                List.of(0.5, 1.0)
        ));
        when(ctrlProdMock.getSimilarityTable()).thenReturn(List.of(
                List.of(1.0, 0.8),
                List.of(0.8, 1.0)
        ));

        List<Pair<Product, Set<String>>> initialDistribution = new ArrayList<>();
        initialDistribution.add(Pair.of(productMock1, Set.of("restriction1")));
        initialDistribution.add(Pair.of(productMock2, Set.of("restriction1")));
        initialDistribution.add(Pair.of(null, Set.of("restriction2")));
        solution = new Solution(
                "TestSolution",
                "Catalog1",
                "Shelf1",
                "Generated",
                "Algorithm1",
                5.0,
                new ArrayList<>()
        );
        solution.setDistribution(initialDistribution);
    }

    @Test
    void testGetAndSetValid() {
        assertTrue(solution.getValid());
        solution.setValid(false);
        assertFalse(solution.getValid());
    }

    @Test
    void testGetAndSetMark() {
        assertEquals(5.0, solution.getMark());
        solution.setMark(8.5);
        assertEquals(8.5, solution.getMark());
    }

    @Test
    public void testCheckMarkSwap() {
        when(ctrlProdMock.generateSimilarityTable()).thenReturn(List.of(
                List.of(1.0, 0.5),
                List.of(0.5, 1.0)
        ));
        when(ctrlProdMock.getProductName(anyInt())).thenReturn("Product1", "Product2");
        when(ctrlProdMock.findProduct(anyString())).thenReturn(productMock1, productMock2);

        solution.setMark(2.0);
        Double markAfterSwap = solution.checkMarkSwap(0, 1);

        assertNotNull(markAfterSwap);
        assertNotEquals(solution.getMark(), markAfterSwap);
    }

    @Test
    public void testCheckMarkDelete() {
        when(ctrlProdMock.generateSimilarityTable()).thenReturn(List.of(
                List.of(1.0, 0.5),
                List.of(0.5, 1.0)
        ));

        solution.setMark(2.0);
        Double markAfterDelete = solution.checkMarkDelete(0);

        assertNotNull(markAfterDelete);
        assertNotEquals(solution.getMark(), markAfterDelete);
    }

    @Test
    public void testCheckMarkAdd() {
        when(ctrlProdMock.findProduct("Product1")).thenReturn(productMock1);
        when(productMock1.getRestrictions()).thenReturn(Set.of("restriction1"));
        when(ctrlProdMock.generateSimilarityTable()).thenReturn(List.of(
                List.of(1.0, 0.5),
                List.of(0.5, 1.0)
        ));

        solution.setMark(2.0);
        Double markAfterAdd = solution.checkMarkAdd("Product1", 0);

        assertNotNull(markAfterAdd);
        assertNotEquals(solution.getMark(), markAfterAdd);
    }




    @Test
    void testGetIdShelf() {
        assertEquals("Shelf1", solution.getIdShelf());
    }

    @Test
    void testGetIdCatalog() {
        assertEquals("Catalog1", solution.getIdCatalog());
    }

    @Test
    void testGetAndSetSolutionName() {
        assertEquals("TestSolution", solution.getSolutionName());
        solution.setSolutionName("NewSolutionName");
        assertEquals("NewSolutionName", solution.getSolutionName());
    }

    @Test
    void testChangeProductsSuccess() {
        solution.changeProducts(0, 1);
        assertEquals(productMock2, solution.getDistribution().get(0).getLeft());
        assertEquals(productMock1, solution.getDistribution().get(1).getLeft());
    }

    @Test
    void testChangeProductsIndexOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> solution.changeProducts(0, 5));
        assertThrows(IndexOutOfBoundsException.class, () -> solution.changeProducts(3, 1));
    }


    @Test
    void testChangeProductsNotInterchangeable() {
        List<Pair<Product, Set<String>>> newDist = new ArrayList<>();
        newDist.add(Pair.of(productMock1, Set.of("restriction1")));
        newDist.add(Pair.of(productMock2, Set.of("restriction2")));
        solution.setDistribution(newDist);
        assertThrows(NotInterchangeableException.class, () -> solution.changeProducts(0, 1));
    }

    @Test
    void testUpdateMarkWithGeneratedHeuristic() {
        when(ctrlProdMock.generateSimilarityTable()).thenReturn(List.of(
                List.of(1.0, 1.0, 0.0),
                List.of(1.0, 1.0, 0.0),
                List.of(0.0,0.0,1.0)
        ));

        solution.updateMark(ctrlProdMock.generateSimilarityTable());
        assertEquals(1.0, solution.getMark());
    }

    @Test
    void testUpdateMarkDifferentCalculate() {
        List<Pair<Product, Set<String>>> initialDistribution = new ArrayList<>();
        initialDistribution.add(Pair.of(productMock1, Set.of("restriction1")));
        initialDistribution.add(Pair.of(null, Set.of("restriction2")));
        initialDistribution.add(Pair.of(productMock2, Set.of("restriction1")));
        solution.setDistribution(initialDistribution);
        when(ctrlProdMock.getSimilarityTable()).thenReturn(List.of(
                List.of(1.0, 0.0,1.0),
                List.of(0.0, 1.0,0.0),
                List.of(1.0, 0.0,1.0)
        ));
        solution.updateMark(ctrlProdMock.generateSimilarityTable());

        assertEquals(1.0, solution.getMark());
    }

    @Test
    void testUpdateMarkWithNonGeneratedHeuristic() throws Exception {
        when(ctrlProdMock.getSimilarityTable()).thenReturn(List.of(
                List.of(1.0, 1.0,0.0),
                List.of(1.0, 1.0,0.0),
                List.of(0.0, 0.0,1.0)
        ));

        Field heuristicField = Solution.class.getDeclaredField("heuristic");
        heuristicField.setAccessible(true);
        heuristicField.set(solution, "Custom");

        solution.updateMark(ctrlProdMock.generateSimilarityTable());

        assertEquals(1.0, solution.getMark());
    }


    @Test
    void testDeleteProductSuccess() {
        solution.deleteProduct(0);
        assertNull(solution.getDistribution().get(0).getLeft());
        assertEquals(Set.of("restriction1"), solution.getDistribution().get(0).getRight());
    }

    @Test
    void testDeleteProductIndexOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> solution.deleteProduct(5));
        assertThrows(IndexOutOfBoundsException.class, () -> solution.deleteProduct(-1));
    }

    @Test
    void testAddProductSuccess() {
        when(ctrlProdMock.findProduct("Product1")).thenReturn(productMock1);
        when(ctrlProdMock.findProduct("NonExistentProduct")).thenReturn(null);

        when(productMock1.getRestrictions()).thenReturn(Set.of("restriction1"));
        when(productMock2.getRestrictions()).thenReturn(Set.of("restriction2"));

        solution.addProduct("Product1", 0);

        assertEquals(productMock1, solution.getDistribution().get(0).getLeft());
        assertEquals(Set.of("restriction1"), solution.getDistribution().get(0).getRight());
    }


    @Test
    void testAddProductIndexOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> solution.addProduct("Product1", 5));
        assertThrows(IndexOutOfBoundsException.class, () -> solution.addProduct("Product1", -1));
    }

    @Test
    void testAddProductNotFound() {
        assertThrows(ProductNotFoundException.class, () -> solution.addProduct("NonExistentProduct", 0));
    }

    @Test
    void testAddProductWithNullRestrictions() {
        List<Pair<Product, Set<String>>> newDist = new ArrayList<>();
        newDist.add(Pair.of(null, null));
        solution.setDistribution(newDist);

        when(ctrlProdMock.findProduct("Product1")).thenReturn(productMock1);
        when(productMock1.getRestrictions()).thenReturn(Set.of("restriction1"));

        assertThrows(InvalidProductRestrictionException.class, () -> solution.addProduct("Product1", 0));
    }


    @Test
    void testAddProductInvalidRestrictions() {
        assertThrows(InvalidProductRestrictionException.class, () -> solution.addProduct("Product1", 1));
    }

    @Test
    void testDelete() {
        solution.delete();

        assertNull(solution.getSolutionName());
        assertNull(solution.getIdCatalog());
        assertNull(solution.getIdShelf());
        assertNull(solution.getHeuristic());
        assertNull(solution.getAlgorithm());
        assertNull(solution.getMark());
        assertNull(solution.getValid());
        assertNull(solution.getDistribution());
    }

    @Test
    void testDeletedReturnsFalse() {
        assertFalse(solution.deleted());
    }

    @Test
    void testDeletedReturnsTrueAfterDelete() {
        solution.delete();
        assertTrue(solution.deleted());
    }
    @Test
    void testToString() {
        String expected = "{TestSolution, Catalog: Catalog1, Shelf: Shelf1, Heuristic: Generated, Algorithm: Algorithm1, Puntuation: 5.0}\n";
        assertEquals(expected, solution.toString());
    }

    @Test
    void testToString1() {
        when(productMock1.getName()).thenReturn("Product1");
        when(productMock2.getName()).thenReturn("Product2");

        List<Pair<Product, Set<String>>> newDist = new ArrayList<>();
        newDist.add(Pair.of(productMock1, Set.of("restriction1")));
        newDist.add(Pair.of(null, Set.of("restriction2")));
        newDist.add(Pair.of(productMock2, null));
        solution.setDistribution(newDist);

        String expected = "{TestSolution, Catalog: Catalog1, Shelf: Shelf1, Heuristic: Generated, Algorithm: Algorithm1, Puntuation: 5.0, Distribution: [(Product: Product1, Restrictions: [restriction1]), (Product: null, Restrictions: [restriction2]), (Product: Product2, Restrictions: null)]}\n";
        assertEquals(expected, solution.toString1());
    }
}

