package com.prop.prop12_1.model;

import com.prop.prop12_1.controller.CtrlProd;
import com.prop.prop12_1.model.Solution;
import com.prop.prop12_1.model.Product
import com.prop.prop12_1.exceptions.*;
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

public class TestSolution {

    private Solution solution;

    @Mock
    private CtrlProd ctrlProdMock;

    @Mock
    private Product productMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configuración de mocks de CtrlProd
        when(ctrlProdMock.findProduct("Product1")).thenReturn(productMock);
        when(ctrlProdMock.getProductName(1)).thenReturn("Product1");
        when(productMock.getId()).thenReturn(1);
        when(productMock.getRestrictions()).thenReturn(Set.of("Restriction1"));

        // Configuración inicial para distribution
        List<Pair<Integer, Set<String>>> distributionData = new ArrayList<>();
        distributionData.add(ImmutablePair.of(1, Set.of("Restriction1")));
        distributionData.add(ImmutablePair.of(null, Set.of("Restriction2")));

        // Crear solución con mocks
        solution = new Solution(
                "TestSolution",
                "Catalog1",
                "Shelf1",
                "Generated",
                "Algorithm1",
                0.0,
                distributionData
        );

        // Asignar mock de CtrlProd a la instancia estática de Solution
        Solution.ctrlProd = ctrlProdMock;
    }

    @Test
    public void testGetDistribution() {
        List<Pair<Product, Set<String>>> distribution = solution.getDistribution();
        assertNotNull(distribution);
        assertEquals(2, distribution.size());
        assertEquals(productMock, distribution.get(0).getLeft());
        assertNull(distribution.get(1).getLeft());
    }

    @Test
    public void testGetValid() {
        assertTrue(solution.getValid());
        solution.setValid(false);
        assertFalse(solution.getValid());
    }

    @Test
    public void testGetSetMark() {
        assertEquals(0.0, solution.getMark());
        solution.setMark(5.0);
        assertEquals(5.0, solution.getMark());
    }

    @Test
    public void testDelete() {
        solution.delete();
        assertNull(solution.getSolutionName());
        assertNull(solution.getIdCatalog());
        assertNull(solution.getIdShelf());
        assertNull(solution.getMark());
        assertNull(solution.getDistribution());
        assertNull(solution.getValid());
    }

    @Test
    public void testDeleted() {
        assertFalse(solution.deleted());
        solution.delete();
        assertTrue(solution.deleted());
    }

    @Test
    public void testChangeProducts_Success() {
        solution.changeProducts(0, 1);
        List<Pair<Product, Set<String>>> distribution = solution.getDistribution();
        assertNull(distribution.get(0).getLeft());
        assertEquals(productMock, distribution.get(1).getLeft());
    }

    @Test
    public void testChangeProducts_NotInterchangeable() {
        assertThrows(NotInterchangeableException.class, () -> solution.changeProducts(0, 1));
    }

    @Test
    public void testDeleteProduct() {
        solution.deleteProduct(0);
        assertNull(solution.getDistribution().get(0).getLeft());
    }

    @Test
    public void testDeleteProduct_InvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> solution.deleteProduct(5));
    }

    @Test
    public void testAddProduct_Success() {
        solution.addProduct("Product1", 1);
        assertEquals(productMock, solution.getDistribution().get(1).getLeft());
    }

    @Test
    public void testAddProduct_ProductNotFound() {
        when(ctrlProdMock.findProduct("InvalidProduct")).thenReturn(null);
        assertThrows(ProductNotFoundException.class, () -> solution.addProduct("InvalidProduct", 1));
    }

    @Test
    public void testAddProduct_InvalidRestrictions() {
        when(productMock.getRestrictions()).thenReturn(Set.of("InvalidRestriction"));
        assertThrows(InvalidProductRestrictionException.class, () -> solution.addProduct("Product1", 0));
    }

    @Test
    public void testUpdateMark_Generated() {
        List<List<Double>> mockSimilarityTable = List.of(
                List.of(1.0, 0.5),
                List.of(0.5, 1.0)
        );
        when(ctrlProdMock.generateSimilarityTable()).thenReturn(mockSimilarityTable);

        solution.updateMark();
        assertEquals(0.5, solution.getMark(), 0.0001);
    }

    @Test
    public void testUpdateMark_NotGenerated() {
        List<List<Double>> mockSimilarityTable = List.of(
                List.of(1.0, 0.5),
                List.of(0.5, 1.0)
        );
        when(ctrlProdMock.getSimilarityTable()).thenReturn(mockSimilarityTable);

        solution.updateMark();
        assertEquals(0.5, solution.getMark(), 0.0001);
    }

    @Test
    public void testToString() {
        String result = solution.toString();
        assertTrue(result.contains("TestSolution"));
        assertTrue(result.contains("Catalog: Catalog1"));
    }
    /*
    @Test
    public void testToString1() {
        String result = solution.toString1();
        assertTrue(result.contains("TestSolution"));
        assertTrue(result.contains(distribution.toString()));
    }

     */
}
