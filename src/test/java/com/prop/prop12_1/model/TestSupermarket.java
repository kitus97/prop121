package com.prop.prop12_1.model;

import com.prop.prop12_1.controller.CtrlAlgorithm;
import com.prop.prop12_1.exceptions.CatalogAlreadyAdded;
import com.prop.prop12_1.exceptions.ProductAlreadyAddedException;
import com.prop.prop12_1.exceptions.ShelfAlreadyAddedException;
import com.prop.prop12_1.exceptions.SolutionAlreadyAddedException;
import org.apache.commons.lang3.tuple.Pair;
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


    @Test
    public void testGenerateSolution_ThrowsSolutionAlreadyAddedException() {
        String solutionName = "ExistingSolution";
        Solution sol = mock(Solution.class);
        supermarket.setSolutions(Map.of(solutionName, sol));
        Catalogue cat = mock(Catalogue.class);
        Shelf sh = mock(Shelf.class);
        catalogs.put("catalog1", cat);
        shelves.put("shelf1", sh);
        when(sh.getDistribution()).thenReturn(new ArrayList<>());
        when(cat.getProductsArray()).thenReturn(new ArrayList<>());

        assertThrows(SolutionAlreadyAddedException.class, () ->
                supermarket.generateSolution("ExistingSolution", "shelf1", "catalog1", true, 0));
    }

    @Test
    public void testGenerateSolution_InvalidShelf() {
        String solutionName = "ExistingSolution";
        Solution sol = mock(Solution.class);
        supermarket.setSolutions(Map.of(solutionName, sol));
        Catalogue cat = mock(Catalogue.class);
        Shelf sh = mock(Shelf.class);
        catalogs.put("catalog1", cat);
        shelves.put("Shelf1", sh);

        assertThrows(NoSuchElementException.class, () ->
                supermarket.generateSolution("new", "NoShelf", "catalog1", true, 0));
    }

    @Test
    public void testGenerateSolution_InvalidCatalog() {
        String solutionName = "ExistingSolution";
        Solution sol = mock(Solution.class);
        supermarket.setSolutions(Map.of(solutionName, sol));
        Catalogue cat = mock(Catalogue.class);
        Shelf sh = mock(Shelf.class);
        catalogs.put("catalog1", cat);
        shelves.put("Shelf1", sh);

        assertThrows(NoSuchElementException.class, () ->
                supermarket.generateSolution("new", "shelf1", "NoCat", true, 0));
    }

    @Test
    public void testInvalidateProductSolution() {
        Solution sol = mock(Solution.class);
        when(sol.deleted()).thenReturn(false);
        when(sol.getValid()).thenReturn(true);
        supermarket.setAssociatedProductSolutions(Map.of("product1", List.of(sol)));
        supermarket.invalidateProductSolution("product1");

        verify(sol, times(1)).setValid(false);
    }

    @Test
    public void testChangeSolutionProducts() {
        Solution sol = mock(Solution.class);
        supermarket.setSolutions(Map.of("solution1", sol));

        supermarket.changeSolutionProducts(0, 1, "solution1");

        verify(sol, times(1)).changeProducts(0, 1);
    }

    @Test
    public void testChangeSolutionInvalidProducts() {
        Solution sol = mock(Solution.class);
        supermarket.setSolutions(Map.of("solution1", sol));
        assertThrows(NoSuchElementException.class, () -> supermarket.changeSolutionProducts(0, 1, "solution8"));

    }

    @Test
    public void testDeleteSolutionProduct() {
        Solution sol = mock(Solution.class);
        supermarket.setSolutions(Map.of("solution1", sol));
        associatedProductSolutions.put("a", new ArrayList<>());
        associatedProductSolutions.get("a").add(sol);
        when(sol.deleteProduct(0)).thenReturn("a");
        supermarket.deleteSolutionProduct("solution1", 0);

        verify(sol, times(1)).deleteProduct(0);
    }

    @Test
    public void testDeleteInvalidSolutionProduct() {
        Solution sol = mock(Solution.class);
        supermarket.setSolutions(Map.of("solution1", sol));

        assertThrows(NoSuchElementException.class, () -> supermarket.deleteSolutionProduct("solution9", 0));

    }

    @Test
    public void testAddSolutionInvalidProduct() {
        Solution sol = mock(Solution.class);
        supermarket.setSolutions(Map.of("solutionName", sol));
        associatedProductSolutions.put("product1", new ArrayList<>());
        associatedProductSolutions.get("product1").add(sol);
        solutions.put("solutionName", sol);

        assertThrows(ProductAlreadyAddedException.class, () -> supermarket.addSolutionProduct("solutionName", "product1", 0));
    }

    @Test
    public void testAddSolutionProduct() {
        Solution sol = mock(Solution.class);
        supermarket.setSolutions(Map.of("solutionName", sol));
        solutions.put("solutionName", sol);
        supermarket.addSolutionProduct("solutionName", "product1", 0);

        verify(sol, times(1)).addProduct("product1", 0);
    }

    @Test
    public void testAddInvalidSolutionProduct() {
        Solution sol = mock(Solution.class);
        supermarket.setSolutions(Map.of("solutionName", sol));

        assertThrows(NoSuchElementException.class, () -> supermarket.addSolutionProduct("solution9", "egg", 0));

    }



   @Test
    public void testUpdateSolutionMark() {
       Solution sol = mock(Solution.class);
        supermarket.setAssociatedProductSolutions(Map.of("product1", List.of(sol)));
        List<List<Double>> similarityTable = List.of(List.of(0.0, 0.5), List.of(0.5, 0.0));

        when(sol.getHeuristic()).thenReturn("Generated");

        supermarket.updateSolutionMark("product1", similarityTable, true);

        verify(sol, times(1)).updateMark(similarityTable);
    }

    @Test
    public void testUpdateSolutionMarkDifferentHeuristic() {
        Solution sol = mock(Solution.class);
        supermarket.setAssociatedProductSolutions(Map.of("product1", List.of(sol)));
        List<List<Double>> similarityTable = List.of(List.of(0.0, 0.5), List.of(0.5, 0.0));

        when(sol.getHeuristic()).thenReturn("Defined");

        supermarket.updateSolutionMark("product1", similarityTable, true);

        verify(sol, times(0)).updateMark(similarityTable);
    }

    @Test
    public void testCheckDeleteSolutionProduct() {
        Solution sol = mock(Solution.class);
        supermarket.setSolutions(Map.of("solution1", sol));
        when(sol.checkMarkDelete(0)).thenReturn(0.8);

        double result = supermarket.checkDeleteSolutionProduct("solution1", 0);

        assertEquals(0.8, result);
    }

    @Test
    public void testInvalidCheckDeleteSolutionProduct() {
        Solution sol = mock(Solution.class);
        supermarket.setSolutions(Map.of("solution1", sol));

        assertThrows(NoSuchElementException.class, () -> supermarket.deleteSolutionProduct("NoSol", 0));

    }

    @Test
    public void testCheckAddSolutionProduct() {
        Solution sol = mock(Solution.class);
        supermarket.setSolutions(Map.of("solution1", sol));
        when(sol.checkMarkAdd("product1", 0)).thenReturn(0.7);

        double result = supermarket.checkAddSolutionProduct("solution1", "product1", 0);

        assertEquals(0.7, result);
    }

    @Test
    public void testInvalidCheckAddSolutionProduct() {
        Solution sol = mock(Solution.class);
        supermarket.setSolutions(Map.of("solution1", sol));

        assertThrows(NoSuchElementException.class, () -> supermarket.addSolutionProduct("NoSol", "egg", 0));

    }

    @Test
    public void testCheckSwapSolution() {
        Solution sol = mock(Solution.class);
        supermarket.setSolutions(Map.of("solution1", sol));
        when(sol.checkMarkSwap(0, 1)).thenReturn(0.9);

        double result = supermarket.checkSwapSolution("solution1", 0, 1);

        assertEquals(0.9, result);
    }

    @Test
    public void testInvalidCheckSwapSolutionProduct() {
        Solution sol = mock(Solution.class);
        supermarket.setSolutions(Map.of("solution1", sol));

        assertThrows(NoSuchElementException.class, () -> supermarket.checkSwapSolution("NoSol", 0, 1));

    }


}
