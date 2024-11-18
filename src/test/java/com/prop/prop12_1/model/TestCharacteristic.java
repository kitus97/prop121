package com.prop.prop12_1.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestCharacteristic {

    private Characteristics characteristics;

    @Mock
    private Product product;

    @Mock
    private Product product2;

    @BeforeEach
    public void setUpBeforeTest() {
        MockitoAnnotations.initMocks(this);
        characteristics = new Characteristics(0, "Char 1");

        when(product.getId()).thenReturn(1);
        when(product2.getId()).thenReturn(2);
        when(product.getName()).thenReturn("Product 1");
        when(product2.getName()).thenReturn("Product 2");
    }

    @Test
    public void testSetId() {
        characteristics.setId(5);
        assertEquals(5, characteristics.getId());
    }

    @Test
    public void testSetName() {
        characteristics.setName("Char 2");
        assertEquals("Char 2", characteristics.getName());
    }

    @Test
    public void testGetId_Correct() {
        assertEquals(0, characteristics.getId());
    }

    @Test
    public void testGetName_Correct() {
        assertEquals("Char 1", characteristics.getName());
    }

    @Test
    public void testToString() {
        assertEquals("Char 1", characteristics.toString());
    }
}
