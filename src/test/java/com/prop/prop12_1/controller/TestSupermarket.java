package com.prop.prop12_1.controller;

import com.prop.prop12_1.model.Shelf;
import com.prop.prop12_1.model.Supermarket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class TestSupermarket {

    @Mock
    private ArrayList<Set<String>> distribution;
    @Mock
    private String name;

    private Shelf shelf;

    private Supermarket supermarket;
    @Test
    public void setUpBeforeTest() {
        supermarket = new Supermarket("name");
    }


    public void addShelfCorrect(){
        Shelf shelf = new Shelf("name", 1);
        assertDoesNotThrow(() -> supermarket.addShelf(shelf));
        assertEquals(1, supermarket.getShelves().size());
        assertEquals(shelf, supermarket.getShelf("name"));
    }


}

