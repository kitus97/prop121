package com.prop.prop12_1.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestShelf {
    private Shelf shelf;

    @BeforeEach
    public void setUpBeforeTest(){
        shelf = new Shelf("Test Shelf", 3);
    }

    @Test
    public void testGetName(){
        assertEquals("Test Shelf", shelf.getName());
    }

    @Test
    public void testGetDistribution(){
        assertEquals(3, shelf.getDistribution().size());
        shelf.getDistribution().forEach(set -> assertTrue(set.isEmpty()));
    }

    @Test
    public void testSetRestrictionValidIndex() {
        shelf.setRestriction("Fragile", 2);
        shelf.setRestriction("Refrigerated", 2);
        assertTrue(shelf.getDistribution().get(2).contains("Fragile"));
        assertTrue(shelf.getDistribution().get(2).contains("Refrigerated"));
        assertEquals(2, shelf.getDistribution().get(2).size());
    }

    @Test
    public void testSetRestrictionDuplicated() {
        shelf.setRestriction("Fragile", 2);
        shelf.setRestriction("Fragile", 2);
        assertTrue(shelf.getDistribution().get(2).contains("Fragile"));
        assertEquals(1, shelf.getDistribution().get(2).size());
    }

    @Test
    public void testSetRestrictionInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> shelf.setRestriction("Invalid", 3));
    }

    @Test
    public void testDeleteRestrictionsValidIndex() {
        shelf.setRestriction("Fragile", 2);
        shelf.deleteRestrictions(2);
        assertTrue(shelf.getDistribution().get(2).isEmpty());
    }

    @Test
    public void testDeleteRestrictionsInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> shelf.deleteRestrictions(3));
    }

    @Test
    public void testResizeShelfIncrease(){
        shelf.resizeShelf(5);
        assertEquals(shelf.getDistribution().size(), 5);
        assertTrue(shelf.getDistribution().get(3).isEmpty());
        assertTrue(shelf.getDistribution().get(4).isEmpty());
    }

    @Test
    public void testResizeShelfDecrease(){
        shelf.setRestriction("Fragile", 1);
        shelf.resizeShelf(2);
        assertEquals(2, shelf.getDistribution().size());
        assertTrue(shelf.getDistribution().get(1).contains("Fragile"));
    }

    @Test
    public void testResizeShelfInvalidSize(){
        assertThrows(IndexOutOfBoundsException.class, () -> shelf.resizeShelf(-1));
    }

    @Test
    public void testToString(){
        shelf.setRestriction("Fragile", 0);
        shelf.setRestriction("Fragile", 1);
        shelf.setRestriction("Refrigerated", 1);
        String shelfString = "Shelf{name='Test Shelf', distribution=[[Fragile], [Fragile, Refrigerated], []]}";
        assertEquals(shelfString, shelf.toString());
    }

    @Test
    public void testToStringEmpty(){
        String shelfString = "Shelf{name='Test Shelf', distribution=[[], [], []]}";
        assertEquals(shelfString, shelf.toString());
    }

}
