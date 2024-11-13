package com.prop.prop12_1.model;
import com.prop.prop12_1.exceptions.CharacteristicAlreadyAddedException;
import com.prop.prop12_1.exceptions.CharacteristicNotFoundException;
import com.prop.prop12_1.exceptions.ProductAlreadyAddedException;
import com.prop.prop12_1.exceptions.ProductNotFoundException;
import com.prop.prop12_1.model.Characteristics;
import com.prop.prop12_1.model.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TestProduct {
    private Product product;

    @Mock
    private Characteristics characteristic1;

    @Mock
    private Characteristics characteristic2;

    @BeforeEach
    public void setUpBeforeTest() {
        product = new Product("TestProduct");
        characteristic1 = mock(Characteristics.class);
        characteristic2 = mock(Characteristics.class);
    }

    @Test
    public void testGetName() {
        assertEquals("TestProduct", product.getName());
    }

    @Test
    public void testSetName() {
        product.setName("UpdatedName");
        assertEquals("UpdatedName", product.getName());
    }

    @Test
    public void testAddCharacteristic() {
        product.addCharacteristic(characteristic1);
        assertTrue(product.getCharacteristics().contains(characteristic1));
    }

    @Test
    public void testAddCharacteristicRepeated() {
        product.addCharacteristic(characteristic1);
        product.addCharacteristic(characteristic1); // Agregar repetido
        assertEquals(1, product.getCharacteristics().size());
    }

    @Test
    public void testRemoveCharacteristic() {
        product.addCharacteristic(characteristic1);
        product.removeCharacteristic(characteristic1);
        assertFalse(product.getCharacteristics().contains(characteristic1));
    }

    @Test
    public void testSetCharacteristics() {
        Set<Characteristics> newCharacteristics = new HashSet<>();
        newCharacteristics.add(characteristic1);
        newCharacteristics.add(characteristic2);

        product.setCharacteristics(newCharacteristics);
        assertEquals(2, product.getCharacteristics().size());
        assertTrue(product.getCharacteristics().contains(characteristic1));
        assertTrue(product.getCharacteristics().contains(characteristic2));
    }

    @Test
    public void testAddRestriction() {
        product.addRestriction(characteristic1);
        assertTrue(product.getRestrictions().contains(characteristic1));
    }

    @Test
    public void testAddRestrictionRepeated() {
        product.addRestriction(characteristic1);
        product.addRestriction(characteristic1); // Agregar repetido
        assertEquals(1, product.getRestrictions().size());
    }

    @Test
    public void testRemoveRestriction() {
        product.addRestriction(characteristic1);
        product.removeRestriction(characteristic1);
        assertFalse(product.getRestrictions().contains(characteristic1));
    }

    @Test
    public void testSetRestrictions() {
        Set<Characteristics> newRestrictions = new HashSet<>();
        newRestrictions.add(characteristic1);
        newRestrictions.add(characteristic2);

        product.setRestrictions(newRestrictions);
        assertEquals(2, product.getRestrictions().size());
        assertTrue(product.getRestrictions().contains(characteristic1));
        assertTrue(product.getRestrictions().contains(characteristic2));
    }
}
