package com.prop.prop12_1.model;

import com.prop.prop12_1.exceptions.*;
import com.prop.prop12_1.model.Characteristics;
import com.prop.prop12_1.model.Product;
import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

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
        characteristics = new Characteristics(0, "name");

        when(product.getId()).thenReturn(1);
        when(product2.getId()).thenReturn(2);
        when(product.getName()).thenReturn("name");
        when(product2.getName()).thenReturn("name2");

    }

    @Test
    public void testSetId() {
        characteristics.setId(2);
        assertEquals(2, characteristics.getId());
    }

    @Test
    public void testSetName() {
        characteristics.setName("name");
        assertEquals("name", characteristics.getName());
    }

    @Test
    public void testGetId_Correct() {
        assertEquals(1, characteristics.getId());
    }

    @Test
    public void testGetName_Correct() {
        assertEquals("name", characteristics.getName());
    }


}
