package com.prop.prop12_1.controller;

import com.prop.prop12_1.exceptions.CatalogAlreadyAdded;
import com.prop.prop12_1.exceptions.ShelfAlreadyAddedException;
import com.prop.prop12_1.exceptions.SolutionAlreadyAddedException;
import com.prop.prop12_1.model.Catalogue;
import com.prop.prop12_1.model.Shelf;
import com.prop.prop12_1.model.Solution;
import com.prop.prop12_1.model.Supermarket;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestSupermarket {

    private Supermarket supermarket;

    private Map<String, Shelf> shelves;
    private Map<String, Catalogue> catalogs;
    private String name;
    private Map<String, Solution> solutions;
    private Map<String, List<Solution>> associatedCatalogSolutions;
    private Map<String, List<Solution>> associatedProductSolutions;
    private Map<String, List<Solution>> associatedShelfSolutions;



    @Mock
    private Shelf shelfMock;

    @BeforeEach
    void setUp() {
        supermarket = new Supermarket("TestSupermarket");
        shelves = new HashMap<>();
        catalogs = new HashMap<>();
        name = "TestSupermarket";
        solutions  = new HashMap<>();
        associatedCatalogSolutions =  new HashMap<>();
        associatedProductSolutions = new HashMap<>();
        associatedShelfSolutions = new HashMap<>();

        supermarket.setAssociatedCatalogSolutions(associatedCatalogSolutions);
        supermarket.setAssociatedProductSolutions(associatedProductSolutions);
        supermarket.setAssociatedShelfSolutions(associatedShelfSolutions);
        supermarket.setShelves(shelves);
        supermarket.setCatalogs(catalogs);
        supermarket.setSolutions(solutions);

    }

    @Test
    void testAddShelf() {
        Shelf shelf = mock(Shelf.class);
        shelves.put("Shelf1", shelf);
        assertEquals(1, supermarket.getShelves().size());
        assertTrue(supermarket.getShelves().contains("Shelf1"));
    }

    @Test
    void testAddShelfAlreadyExists() {
        Shelf shelf = mock(Shelf.class);
        shelves.put("Shelf1", shelf);
        assertThrows(ShelfAlreadyAddedException.class, () -> supermarket.addShelf("Shelf1", 10));
    }

    @Test
    void testDeleteShelf() {
        Shelf shelf = mock(Shelf.class);
        shelves.put("Shelf1", shelf);
        supermarket.deleteShelf("Shelf1");
        assertFalse(supermarket.getShelves().contains("Shelf1"));
    }

    @Test
    void testDeleteShelfNotFound() {
        assertThrows(NoSuchElementException.class, () -> supermarket.deleteShelf("NonExistentShelf"));
    }

    @Test
    void testAddCatalogue() {
        Catalogue cat = mock(Catalogue.class);
        catalogs.put("Catalogue1", cat);
        assertEquals(1, supermarket.getCatalogs().size());
    }

    @Test
    void testAddCatalogueAlreadyExists() {
        Catalogue cat = mock(Catalogue.class);
        catalogs.put("Catalogue1", cat);
        assertThrows(CatalogAlreadyAdded.class, () -> supermarket.addCatalogue("Catalogue1"));
    }

    @Test
    void testDeleteCatalogue() {
        Catalogue cat = mock(Catalogue.class);
        catalogs.put("Catalogue1", cat);
        supermarket.deleteCatalogue("Catalogue1");
        assertEquals(0, supermarket.getCatalogs().size());
    }

    @Test
    void testDeleteCatalogueNotFound() {
        assertThrows(NoSuchElementException.class, () -> supermarket.deleteCatalogue("NonExistentCatalogue"));
    }

    @Test
    void testAddProductToCatalogue() {

        Catalogue cat = mock(Catalogue.class);
        when(cat.getName()).thenReturn("Catalogue1");
        catalogs.put("Catalogue1", cat);
        supermarket.addProductToCatalogue("Product1", "Catalogue1");
        verify(cat).addProduct("Product1");
    }

    @Test
    void testAddProductToCatalogueNotFound() {
        assertThrows(NoSuchElementException.class, () -> supermarket.addProductToCatalogue("Product1", "NonExistentCatalogue"));
    }

    @Test
    void testRemoveProductFromCatalogue() {
        Catalogue cat = mock(Catalogue.class);
        catalogs.put("Catalogue1", cat);

        Solution sol = mock(Solution.class);
        solutions.put("Solution1", sol);

        List<Solution> list = new ArrayList<Solution>();
        list.add(sol);

        associatedCatalogSolutions.put("Catalogue1", list);

        supermarket.addProductToCatalogue("Product1", "Catalogue1");
        supermarket.removeProductFromCatalogue("Product1", "Catalogue1");
        verify(cat).removeProduct("Product1");
    }

    @Test
    void testRemoveProductFromCatalogueNotFound() {
        assertThrows(NoSuchElementException.class, () -> supermarket.removeProductFromCatalogue("Product1", "NonExistentCatalogue"));
    }

    @Test
    void testAddSolutionAlreadyExists() {
        Catalogue cat = mock(Catalogue.class);
        catalogs.put("Catalogue1", cat);

        Shelf shelf = mock(Shelf.class);
        shelves.put("Shelf1", shelf);

        Solution sol = mock(Solution.class);
        solutions.put("Solution1", sol);

        assertThrows(SolutionAlreadyAddedException.class, () -> supermarket.generateSolution("Solution1", "Shelf1", "Catalogue1", false, 0));
    }

    @Test
    void testDeleteSolution() {
        Catalogue cat = mock(Catalogue.class);
        when(cat.getName()).thenReturn("Catalogue1");
        catalogs.put("Catalogue1", cat);

        Solution sol = mock(Solution.class);
        solutions.put("Solution1", sol);

        supermarket.deleteSolution("Solution1");
        assertThrows(NoSuchElementException.class, () -> supermarket.getSolution("Solution1"));
    }

    @Test
    void testDeleteSolutionNotFound() {
        assertThrows(NoSuchElementException.class, () -> supermarket.deleteSolution("NonExistentSolution"));
    }


    @Test
    void testGetSolutionNotFound() {
        assertThrows(NoSuchElementException.class, () -> supermarket.getSolution("NonExistentSolution"));
    }

    @Test
    void testResizeShelf() {
        Shelf shelf = mock(Shelf.class);
        shelves.put("Shelf1", shelf);

        Solution sol = mock(Solution.class);
        solutions.put("Solution1", sol);

        List<Solution> list = new ArrayList<Solution>();
        list.add(sol);

        associatedShelfSolutions.put("Shelf1", list);

        supermarket.resizeShelf("Shelf1", 20);
        verify(shelf).resizeShelf(20);
    }

    @Test
    void testResizeShelfNotFound() {
        assertThrows(NoSuchElementException.class, () -> supermarket.resizeShelf("NonExistentShelf", 20));
    }

    @Test
    void testAddRestrictionToShelf() {
        Shelf shelf = mock(Shelf.class);
        shelves.put("Shelf1", shelf);

        Solution sol = mock(Solution.class);
        solutions.put("Solution1", sol);

        List<Solution> list = new ArrayList<Solution>();
        list.add(sol);

        associatedShelfSolutions.put("Shelf1", list);

        supermarket.addRestriction("Shelf1", "Restriction1", 0);
        verify(shelf).setRestriction("Restriction1", 0);
    }

    @Test
    void testDeleteRestrictionFromShelf() {
        Shelf shelf = mock(Shelf.class);
        shelves.put("Shelf1", shelf);
        Solution sol = mock(Solution.class);
        solutions.put("Solution1", sol);

        List<Solution> list = new ArrayList<Solution>();
        list.add(sol);

        associatedShelfSolutions.put("Shelf1", list);

        supermarket.deleteRestrictions("Shelf1", 0);
        verify(shelf).deleteRestrictions(0);
    }
}
