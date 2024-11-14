package com.prop.prop12_1.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestProduct {

    private Product product;

    @Mock
    private Characteristics characteristicMock;

    @Mock
    private Characteristics characteristicMock2;

    @Mock
    private Characteristics restrictionMock;

    @BeforeEach
    public void setUpBeforeTest() {
        MockitoAnnotations.openMocks(this);
        product = new Product(1, "Test Product");

        when(characteristicMock.getName()).thenReturn("Characteristic 1");
        when(characteristicMock2.getName()).thenReturn("Characteristic 2");
        when(restrictionMock.getName()).thenReturn("Restriction Mock");
    }

    @Test
    public void testGetName() {
        assertEquals("Test Product", product.getName());
    }

    @Test
    public void testSetName() {
        product.setName("Updated Product");
        assertEquals("Updated Product", product.getName());
    }

    @Test
    public void testGetId() {
        assertEquals(1, product.getId());
    }

    @Test
    public void testSetId() {
        product.setId(2);
        assertEquals(2, product.getId());
    }

    @Test
    public void testGetCharacteristics_EmptySet() {
        assertTrue(product.getCharacteristics().isEmpty());
    }

    @Test
    public void testSetCharacteristics() {
        Set<Characteristics> newCharacteristics = new HashSet<>();
        newCharacteristics.add(characteristicMock);
        newCharacteristics.add(characteristicMock2);

        product.setCharacteristics(newCharacteristics);

        assertEquals(newCharacteristics, product.getCharacteristics());
        assertTrue(product.getCharacteristics().contains(characteristicMock));
        assertTrue(product.getCharacteristics().contains(characteristicMock2));
    }

    @Test
    public void testAddCharacteristic() {
        product.addCharacteristic(characteristicMock);
        product.addCharacteristic(characteristicMock2);

        assertTrue(product.getCharacteristics().contains(characteristicMock));
        assertTrue(product.getCharacteristics().contains(characteristicMock2));
    }

    @Test
    public void testAddDuplicateCharacteristic() {
        product.addCharacteristic(characteristicMock);
        product.addCharacteristic(characteristicMock);  // Intentar añadir la misma característica

        assertEquals(1, product.getCharacteristics().size());
        assertTrue(product.getCharacteristics().contains(characteristicMock));
    }

    @Test
    public void testRemoveCharacteristic() {
        product.addCharacteristic(characteristicMock);
        product.removeCharacteristic(characteristicMock);

        assertFalse(product.getCharacteristics().contains(characteristicMock));
    }

    @Test
    public void testRemoveNonExistentCharacteristic() {
        product.removeCharacteristic(characteristicMock);
        assertTrue(product.getCharacteristics().isEmpty());
    }

    @Test
    public void testAddRestriction() {
        product.addRestriction(restrictionMock);
        assertTrue(product.getRestrictions().contains("Restriction Mock"));
    }

    @Test
    public void testAddDuplicateRestriction() {
        product.addRestriction(restrictionMock);
        product.addRestriction(restrictionMock);

        assertEquals(1, product.getRestrictions().size());
        assertTrue(product.getRestrictions().contains("Restriction Mock"));
    }

    @Test
    public void testGetRestrictions_EmptySet() {
        assertTrue(product.getRestrictions().isEmpty());
    }

    @Test
    public void testSetRestrictions() {
        Set<Characteristics> newRestrictions = new HashSet<>();
        newRestrictions.add(restrictionMock);

        product.setRestrictions(newRestrictions);

        Set<String> restrictions = product.getRestrictions();
        assertTrue(restrictions.contains("Restriction Mock"));
    }

    @Test
    public void testRemoveRestriction() {
        product.addRestriction(restrictionMock);
        product.removeRestriction(restrictionMock);

        assertFalse(product.getRestrictions().contains("Restriction Mock"));
    }

    @Test
    public void testRemoveNonExistentRestriction() {
        // Intentar remover una restricción que no fue agregada
        product.removeRestriction(restrictionMock);
        assertTrue(product.getRestrictions().isEmpty());
    }

    @Test
    public void testToString() {
        product.addCharacteristic(characteristicMock);
        product.addRestriction(restrictionMock);

        String productString = product.toString();
        assertTrue(productString.contains("Test Product"));
        assertTrue(productString.contains("Characteristic 1"));
        assertTrue(productString.contains("Restriction Mock"));
    }

    @Test
    public void testToString_EmptyProduct() {
        String productString = product.toString();
        assertTrue(productString.contains("Test Product"));
        assertFalse(productString.contains("Characteristic"));
        assertFalse(productString.contains("Restriction"));
    }
}
