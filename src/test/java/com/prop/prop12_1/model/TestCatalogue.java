package com.prop.prop12_1.model;

import com.prop.prop12_1.controller.CtrlProd;
import com.prop.prop12_1.exceptions.ProductAlreadyAddedException;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestCatalogue {
    private Catalogue catalogue;

    @Mock
    private CtrlProd mockCtrlProd;

    @Mock
    private Product productMock;

    @Mock
    private Product productMock2;

    @BeforeEach
    public void setUpBeforeTest(){
        MockitoAnnotations.openMocks(this);

        Map<String, Product> mockProducts = new HashMap<>();

        when(productMock.getId()).thenReturn(1);
        when(productMock.getRestrictions()).thenReturn(Set.of("Fragile"));
        when(productMock2.getName()).thenReturn("Product1");

        when(productMock2.getId()).thenReturn(2);
        when(productMock2.getRestrictions()).thenReturn(Set.of("Heavy", "Flammable"));
        when(productMock2.getName()).thenReturn("Product2");

        //intent de simular allproducts amb productMock i productMock2 afegits
        mockProducts.put("Product1", productMock);
        mockProducts.put("Product2", productMock2);
        when(mockCtrlProd.getProducts()).thenReturn(mockProducts);

        catalogue = new Catalogue("Test Catalogue");
    }

    @Test
    public void testGetName() {
        assertEquals("Test Catalogue", catalogue.getName());
    }

    @Test
    public void testSetName() {
        catalogue.setName("Updated Catalogue");
        assertEquals("Updated Catalogue", catalogue.getName());
    }

    @Test
    void testSetProducts() {
        Map<String, Product> newProducts = new HashMap<>();
        newProducts.put("Product1", productMock);
        newProducts.put("Product2", productMock2);

        catalogue.setProducts(newProducts);

        assertEquals(newProducts, catalogue.getProducts());
        assertTrue(catalogue.getProducts().containsKey("Product1"));
        assertTrue(catalogue.getProducts().containsKey("Product2"));
        assertTrue(catalogue.getProducts().containsValue(productMock));
        assertTrue(catalogue.getProducts().containsValue(productMock2));
    }

    @Test
    public void testGetProductsEmpty() {
        assertTrue(catalogue.getProducts().isEmpty());
    }

    @Test
    void testGetProductNames() {
        catalogue.addProduct("Product1");
        catalogue.addProduct("Product2");

        List<String> productNames = catalogue.getProductNames();
        assertEquals(2, productNames.size());
        assertTrue(productNames.contains("Product1"));
        assertTrue(productNames.contains("Product2"));
    }

    @Test
    void testGetProductsArray() {
        catalogue.addProduct("Product1");
        catalogue.addProduct("Product2");

        List<Pair<Integer, Set<String>>> productsArray = catalogue.getProductsArray();

        assertEquals(2, productsArray.size());
        assertTrue(productsArray.contains(Pair.of(1, Set.of("Fragile"))));
        assertTrue(productsArray.contains(Pair.of(2, Set.of("Heavy", "Flammable"))));
    }

    @Test
    public void testAddProduct() {
        catalogue.addProduct("Product1");
        catalogue.addProduct("Product2");

        assertEquals(2, catalogue.getProducts().size());
        assertTrue(catalogue.getProducts().containsKey("Product1"));
        assertTrue(catalogue.getProducts().containsKey("Product2"));
        assertTrue(catalogue.getProducts().containsValue(productMock));
        assertTrue(catalogue.getProducts().containsValue(productMock2));
    }

    @Test
    public void testAddProductNonExistent() {
        assertThrows(NoSuchElementException.class, () -> catalogue.addProduct("NonExistentProduct"));
    }

    @Test
    public void testAddProductDuplicated() {
        catalogue.addProduct("Product1");
        assertThrows(ProductAlreadyAddedException.class, () -> catalogue.addProduct("Product1"));
    }

    @Test
    public void testRemoveProduct() {
        catalogue.addProduct("Product1");
        catalogue.removeProduct("Product1");
        assertTrue(catalogue.getProducts().isEmpty());
    }

    @Test
    void testRemoveProductNonExistent() {
        assertThrows(NoSuchElementException.class, () -> catalogue.removeProduct("NonExistentProduct"));
    }

    @Test
    void testToString() {
        catalogue.addProduct("Product1");
        String expected = "Catalogue{name='Test Catalogue', products={Product1=" + productMock + "}}";
        assertEquals(expected, catalogue.toString());
    }

}
