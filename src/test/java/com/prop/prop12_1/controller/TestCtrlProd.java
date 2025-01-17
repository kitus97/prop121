package com.prop.prop12_1.controller;

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

public class TestCtrlProd {

    @InjectMocks
    private CtrlProd ctrlProd;

    @Mock
    private Characteristics characteristicMock;

    @Mock
    private Characteristics characteristicMock2;

    @Mock
    private Product productMock;

    private Map<String, Characteristics> characteristicsMap;
    private Map<String, Product> productsMap;
    private List<List<Double>> similarityTable;
    private Map<Integer, String> mapProductsId;

    @BeforeEach
    public void setUpBeforeTest() {
        MockitoAnnotations.openMocks(this);

        characteristicsMap = new HashMap<>();
        productsMap = new HashMap<>();
        similarityTable = new ArrayList<>();
        mapProductsId = new HashMap<>();

        when(characteristicMock.getName()).thenReturn("Characteristic 1");
        when(characteristicMock2.getName()).thenReturn("Characteristic 2");
        when(productMock.getName()).thenReturn("Product 1");
        when(productMock.getId()).thenReturn(0);

        ctrlProd.setCharacteristics(characteristicsMap);
        ctrlProd.setProducts(productsMap);
        ctrlProd.setSimilarityTable(similarityTable);
        ctrlProd.setMapProductsId(mapProductsId);
    }

    @Test
    public void testAddCharacteristic_Success() {
        ctrlProd.addCharacteristic("Characteristic 1");
        assertTrue(characteristicsMap.containsKey("Characteristic 1"));
    }

    @Test
    public void testAddCharacteristic_AlreadyAdded() {
        characteristicsMap.put("Characteristic 1", characteristicMock);
        assertThrows(CharacteristicAlreadyAddedException.class, () -> ctrlProd.addCharacteristic("Characteristic 1"));
    }

    @Test
    public void testCheckProductSimilarities_Success() {
        productsMap.put("Product 1", productMock);
        similarityTable.add(Arrays.asList(1.0, 0.5));
        mapProductsId.put(0, "Product 1");

        Map<String, Double> result = ctrlProd.checkProductSimilarities("Product 1");
        assertTrue(result.containsKey("Product 1"));
        assertEquals(1.0, result.get("Product 1"));
    }

    @Test
    public void testCheckProductSimilarities_ProductNotFound() {
        assertThrows(ProductNotFoundException.class, () -> ctrlProd.checkProductSimilarities("Nonexistent Product"));
    }

    @Test
    public void testAddProduct_Success() {
        ctrlProd.addProduct("New Product");
        assertTrue(productsMap.containsKey("New Product"));
        assertEquals("New Product", mapProductsId.get(productsMap.size() - 1));
    }

    @Test
    public void testAddProduct_AlreadyAdded() {
        productsMap.put("Existing Product", productMock);
        assertThrows(ProductAlreadyAddedException.class, () -> ctrlProd.addProduct("Existing Product"));
    }

    @Test
    public void testGetProducts_Success() {
        Product product1 = new Product(1, "Product A");
        Product product2 = new Product(2, "Product B");

        productsMap.put("Product A", product1);
        productsMap.put("Product B", product2);

        Map<String, Product> result = ctrlProd.getProducts();

        assertEquals(2, result.size());
        assertTrue(result.containsKey("Product A"));
        assertTrue(result.containsKey("Product B"));
        assertEquals(product1, result.get("Product A"));
        assertEquals(product2, result.get("Product B"));
    }

    @Test
    public void testGetCharacteristics_Success() {
        Characteristics characteristic1 = new Characteristics(1, "char 1");
        Characteristics characteristic2 = new Characteristics(2, "char 2");

        characteristicsMap.put("char 1", characteristic1);
        characteristicsMap.put("char 2", characteristic2);

        Map<String, Characteristics> result = ctrlProd.getCharacteristics();

        assertEquals(2, result.size());
        assertTrue(result.containsKey("char 1"));
        assertTrue(result.containsKey("char 2"));
        assertEquals(characteristic1, result.get("char 1"));
        assertEquals(characteristic2, result.get("char 2"));
    }


    @Test
    public void testGetSimilarityTable_Success() {
        List<List<Double>> expectedTable = new ArrayList<>();
        expectedTable.add(new ArrayList<>(Arrays.asList(1.0, 0.5)));
        expectedTable.add(new ArrayList<>(Arrays.asList(0.5, 1.0)));

        ctrlProd.setSimilarityTable(expectedTable);

        List<List<Double>> result = ctrlProd.getSimilarityTable();

        assertEquals(2, result.size());
        assertEquals(expectedTable, result);
    }

    @Test
    public void testGetRestrictionsProducts_ProductNotFound() {
        assertThrows(ProductNotFoundException.class, () -> ctrlProd.getRestrictionsProducts("Nonexistent Product"));
    }

    @Test
    public void testGetCharacteristicsProducts_ProductNotFound() {
        assertThrows(ProductNotFoundException.class, () -> ctrlProd.getCharacteristicsProducts("Nonexistent Product"));
    }



    @Test
    public void testAddRestrictionProduct_Success() {
        characteristicsMap.put("Restriction 1", characteristicMock);
        productsMap.put("Product 1", productMock);
        when(productMock.getRestrictions()).thenReturn(new HashSet<>());

        ctrlProd.addRestrictionProduct("Restriction 1", "Product 1");

        verify(productMock).addRestriction(characteristicMock);
    }

    @Test
    public void testAddRestrictionProduct_RestrictionNotFound() {
        productsMap.put("Product 1", productMock);

        assertThrows(CharacteristicNotFoundException.class, () -> ctrlProd.addRestrictionProduct("Nonexistent Restriction", "Product 1"));
    }

    @Test
    public void testAddRestrictionProduct_ProductNotFound() {
        characteristicsMap.put("Restriction 1", characteristicMock);

        assertThrows(ProductNotFoundException.class, () -> ctrlProd.addRestrictionProduct("Restriction 1", "Nonexistent Product"));
    }

    @Test
    public void testAddRestrictionProduct_RestrictionAlreadyAdded() {
        characteristicsMap.put("Restriction 1", characteristicMock);
        productsMap.put("Product 1", productMock);

        when(characteristicMock.getName()).thenReturn("Restriction 1");
        when(productMock.getRestrictions()).thenReturn(Set.of("Restriction 1"));

        assertThrows(RestrictionAlreadyAddedToProductException.class,
                () -> ctrlProd.addRestrictionProduct("Restriction 1", "Product 1"));
    }


    @Test
    public void testRemoveRestrictionProduct_ProductNotFound() {
        characteristicsMap.put("Restriction 1", characteristicMock);

        assertThrows(ProductNotFoundException.class, () -> ctrlProd.removeRestrictionProduct("Restriction 1", "Nonexistent Product"));
    }

    @Test
    public void testRemoveRestrictionProduct_RestrictionNotFound() {
        characteristicsMap.put("Restriction 1", characteristicMock);
        productsMap.put("Product 1", productMock);
        when(productMock.getRestrictions()).thenReturn(new HashSet<>());

        assertThrows(RestrictionNotFoundInProductException.class, () -> ctrlProd.removeRestrictionProduct("Restriction 1", "Product 1"));
    }

    @Test
    public void testAddCharacteristicProduct_Success() {
        characteristicsMap.put("Characteristic 1", characteristicMock);
        productsMap.put("Product 1", productMock);
        when(productMock.getCharacteristics()).thenReturn(new HashSet<>());

        ctrlProd.addCharacteristicProduct("Characteristic 1", "Product 1");

        verify(productMock).addCharacteristic(characteristicMock);
    }

    @Test
    public void testAddCharacteristicProduct_CharacteristicNotFound() {
        productsMap.put("Product 1", productMock);

        assertThrows(CharacteristicNotFoundException.class, () -> ctrlProd.addCharacteristicProduct("Nonexistent Characteristic", "Product 1"));
    }

    @Test
    public void testAddCharacteristicProduct_ProductNotFound() {
        characteristicsMap.put("Characteristic 1", characteristicMock);

        assertThrows(ProductNotFoundException.class, () -> ctrlProd.addCharacteristicProduct("Characteristic 1", "Nonexistent Product"));
    }

    @Test
    public void testAddCharacteristicProduct_CharacteristicAlreadyAdded() {
        characteristicsMap.put("Characteristic 1", characteristicMock);
        productsMap.put("Product 1", productMock);
        when(productMock.getCharacteristics()).thenReturn(Set.of(characteristicMock));

        assertThrows(CharacteristicAlreadyAddedToProductException.class, () -> ctrlProd.addCharacteristicProduct("Characteristic 1", "Product 1"));
    }

    @Test
    public void testRemoveCharacteristicProduct_Success() {
        characteristicsMap.put("Characteristic 1", characteristicMock);
        productsMap.put("Product 1", productMock);
        when(productMock.getCharacteristics()).thenReturn(Set.of(characteristicMock));

        ctrlProd.removeCharacteristicProduct("Characteristic 1", "Product 1");

        verify(productMock).removeCharacteristic(characteristicMock);
    }

    @Test
    public void testRemoveCharacteristicProduct_ProductNotFound() {
        characteristicsMap.put("Characteristic 1", characteristicMock);

        assertThrows(ProductNotFoundException.class, () -> ctrlProd.removeCharacteristicProduct("Characteristic 1", "Nonexistent Product"));
    }

    @Test
    public void testRemoveCharacteristicProduct_CharacteristicNotFoundInProduct() {
        characteristicsMap.put("Characteristic 1", characteristicMock);
        productsMap.put("Product 1", productMock);
        when(productMock.getCharacteristics()).thenReturn(new HashSet<>());

        assertThrows(CharacteristicNotFoundInProductException.class, () -> ctrlProd.removeCharacteristicProduct("Characteristic 1", "Product 1"));
    }

    @Test
    public void testRemoveCharacteristicProduct_CharacteristicNotFound() {
        productsMap.put("Product 1", productMock);

        assertThrows(CharacteristicNotFoundException.class, () -> ctrlProd.removeCharacteristicProduct("Nonexistent Characteristic", "Product 1"));
    }

    @Test
    public void testFindCharacteristicByName() {
        List<Characteristics> characteristicList = Arrays.asList(characteristicMock, characteristicMock2);
        assertTrue(CtrlProd.findCharacteristicByName(characteristicList, "Characteristic 1"));
        assertFalse(CtrlProd.findCharacteristicByName(characteristicList, "Nonexistent Characteristic"));
    }
    @Test
    public void testFindProduct_Exists() {
        productsMap.put("Product 1", productMock);
        assertEquals(productMock, ctrlProd.findProduct("Product 1"));
    }

    @Test
    public void testFindProduct_NotExists() {
        assertNull(ctrlProd.findProduct("Nonexistent Product"));
    }

    @Test
    public void testListCharacteristics_Empty() {
        assertTrue(ctrlProd.listCharacteristics().isEmpty());
    }

    @Test
    public void testListCharacteristics_WithValues() {
        when(characteristicMock.toString()).thenReturn("Characteristics{name='Characteristic 1'}");
        when(characteristicMock2.toString()).thenReturn("Characteristics{name='Characteristic 2'}");

        characteristicsMap.put("Characteristic 1", characteristicMock);
        characteristicsMap.put("Characteristic 2", characteristicMock2);

        List<String> result = ctrlProd.listCharacteristics();

        assertEquals(2, result.size());

        assertTrue(result.contains("Characteristic 1"));
        assertTrue(result.contains("Characteristic 2"));
    }

    // Test para listProducts
    @Test
    public void testListProducts_Empty() {
        assertTrue(ctrlProd.listProducts().isEmpty());
    }

    @Test
    public void testListProducts_WithValues() {
        productsMap.put("Product 1", productMock);

        List<String> result = ctrlProd.listProducts();
        assertEquals(1, result.size());
        assertTrue(result.contains("Product 1"));
    }

    @Test
    public void testSetSimilarities_SingleProduct() {
        ctrlProd.addProduct("Product 1");

        boolean result = ctrlProd.setSimilarities(null);

        assertTrue(result);
        assertEquals(1, ctrlProd.getSimilarityTable().size());
        assertEquals(1, ctrlProd.getSimilarityTable().get(0).size());
        assertEquals(0.0, ctrlProd.getSimilarityTable().get(0).get(0));
    }

    @Test
    public void testSetSimilarities_NullInput() {
        Product product1 = new Product(0, "Product 1");
        Product product2 = new Product(1, "Product 2");

        Characteristics characteristic1 = new Characteristics(1, "char1");
        Characteristics characteristic2 = new Characteristics(2, "char2");

        product1.setCharacteristics(Set.of(characteristic1));
        product2.setCharacteristics(Set.of(characteristic1, characteristic2));

        ctrlProd.getProducts().put("Product 1", product1);
        ctrlProd.getProducts().put("Product 2", product2);
        ctrlProd.getMapProductsId().put(0, "Product 1");
        ctrlProd.getMapProductsId().put(1, "Product 2");

        similarityTable.add(new ArrayList<>(Collections.singletonList(0.0)));

        boolean result = ctrlProd.setSimilarities(null);

        assertTrue(result);
        assertEquals(2, ctrlProd.getSimilarityTable().size());
        assertEquals(0.0, ctrlProd.getSimilarityTable().get(0).get(0));
        assertEquals(0.5, ctrlProd.getSimilarityTable().get(0).get(1));
        assertEquals(0.5, ctrlProd.getSimilarityTable().get(1).get(0));
        assertEquals(0.0, ctrlProd.getSimilarityTable().get(1).get(1));
    }


    @Test
    public void testSetSimilarities_InvalidInputSize() {
        ctrlProd.addProduct("Product 1");
        ctrlProd.addProduct("Product 2");

        Double[] invalidSimilarities = {0.8, 0.7};

        assertThrows(SimilarityArrayIncorrectSizeException.class, () -> ctrlProd.setSimilarities(invalidSimilarities));
    }

    @Test
    public void testSetSimilarities_ValidInput() {
        Product product1 = new Product(0, "Product 1");
        Product product2 = new Product(1, "Product 2");

        productsMap.put("Product 1", product1);
        productsMap.put("Product 2", product2);
        mapProductsId.put(0, "Product 1");
        mapProductsId.put(1, "Product 2");

        similarityTable.add(new ArrayList<>(Collections.singletonList(0.0)));
        Double[] similarities = {0.8};

        boolean result = ctrlProd.setSimilarities(similarities);

        assertTrue(result);
        assertEquals(0.0, similarityTable.get(0).get(0));
        assertEquals(0.8, similarityTable.get(0).get(1));
        assertEquals(0.8, similarityTable.get(1).get(0));
        assertEquals(0.0, similarityTable.get(1).get(1));
    }


    @Test
    public void testModifySimilarity_Success() {
        Product product1 = new Product(0, "Product 1");
        Product product2 = new Product(1, "Product 2");

        productsMap.put("Product 1", product1);
        productsMap.put("Product 2", product2);

        similarityTable.add(Arrays.asList(1.0, 0.5));
        similarityTable.add(Arrays.asList(0.5, 1.0));

        ctrlProd.modifySimilarity("Product 1", "Product 2", 0.9);

        assertEquals(0.9, similarityTable.get(0).get(1));
        assertEquals(0.9, similarityTable.get(1).get(0));
    }

    @Test
    public void testModifySimilarity_ProductNotFound() {
        assertThrows(ProductNotFoundException.class, () -> ctrlProd.modifySimilarity("Product 1", "Nonexistent Product", 0.8));
        assertThrows(ProductNotFoundException.class, () -> ctrlProd.modifySimilarity("Nonexistent Product 1", "Nonexistent Product 2", 0.8));
    }

    @Test
    public void testModifyProductSimilarities_ValidArray() {
        Double[] similarities = {0.4, 0.7};
        Product productMock = mock(Product.class);
        Product productMock1 = mock(Product.class);
        Product productMock2 = mock(Product.class);
        productsMap.put("Product 1", productMock);
        mapProductsId.put(0, "Product 1");
        when(productMock.getId()).thenReturn(0);
        productsMap.put("Product 2", productMock1);
        mapProductsId.put(1, "Product 2");
        when(productMock1.getId()).thenReturn(1);
        productsMap.put("Product 3", productMock2);
        mapProductsId.put(2, "Product 3");
        when(productMock2.getId()).thenReturn(2);
        similarityTable.add(Arrays.asList(1.0, 0.5, 0.3));
        similarityTable.add(Arrays.asList(0.6, 0.2,0.3));
        similarityTable.add(Arrays.asList(0.9, 0.2,0.2));

        ctrlProd.modifyProductSimilarities("Product 3", similarities);

        assertEquals(0.4, similarityTable.get(2).get(0));
        assertEquals(0.4, similarityTable.get(0).get(2));
        assertEquals(0.7, similarityTable.get(1).get(2));
        assertEquals(0.7, similarityTable.get(2).get(1));
    }

    @Test
    public void testModifyProductSimilarities_NullInput() {
        Characteristics characteristic1 = new Characteristics(1, "char1");
        Characteristics characteristic2 = new Characteristics(2, "char2");
        Characteristics characteristic3 = new Characteristics(3, "char3");

        Set<Characteristics> charSet1 = Set.of(characteristic1);
        Set<Characteristics> charSet2 = Set.of(characteristic2, characteristic3);
        Set<Characteristics> charSet3 = Set.of(characteristic1, characteristic3);

        Product product1 = new Product(0, "Product 1");
        product1.setCharacteristics(charSet1);

        Product product2 = new Product(1, "Product 2");
        product2.setCharacteristics(charSet2);

        Product product3 = new Product(2, "Product 3");
        product3.setCharacteristics(charSet3);

        // Agregar productos al mapa
        ctrlProd.getProducts().put("Product 1", product1);
        ctrlProd.getProducts().put("Product 2", product2);
        ctrlProd.getProducts().put("Product 3", product3);

        ctrlProd.getMapProductsId().put(0, "Product 1");
        ctrlProd.getMapProductsId().put(1, "Product 2");
        ctrlProd.getMapProductsId().put(2, "Product 3");



        ctrlProd.setSimilarityTable(similarityTable);
        similarityTable.add(Arrays.asList(1.0, 0.5, 0.3));
        similarityTable.add(Arrays.asList(0.6, 0.2,0.3));
        similarityTable.add(Arrays.asList(0.9, 0.2,0.2));

        ctrlProd.modifyProductSimilarities("Product 1", null);

        double expectedSimilarity12 = ctrlProd.calculateSimilarity(charSet1, charSet2);
        double expectedSimilarity13 = ctrlProd.calculateSimilarity(charSet1, charSet3);

        assertEquals(expectedSimilarity12, similarityTable.get(0).get(1));
        assertEquals(expectedSimilarity12, similarityTable.get(1).get(0));
        assertEquals(expectedSimilarity13, similarityTable.get(0).get(2));
        assertEquals(expectedSimilarity13, similarityTable.get(2).get(0));
    }




    @Test
    public void testModifyProductSimilarities_IncorrectArraySize() {
        productsMap.put("Product 1", productMock);
        mapProductsId.put(0, "Product 1");
        similarityTable.add(Arrays.asList(1.0, 0.5, 0.3));

        Double[] similarities = {0.4}; // Incorrect size
        assertThrows(SimilarityArrayIncorrectSizeException.class, () ->
                ctrlProd.modifyProductSimilarities("Product 1", similarities)
        );
    }

    @Test
    public void testModifyProductSimilarities_ProductNotFound() {
        Double[] similarities = {0.4, 0.7};
        assertThrows(ProductNotFoundException.class, () ->
                ctrlProd.modifyProductSimilarities("Nonexistent Product", similarities)
        );
    }


    @Test
    public void testCheckProductsSimilarity_Success() {
        Product product1 = new Product(0, "Product 1");
        Product product2 = new Product(1, "Product 2");

        productsMap.put("Product 1", product1);
        productsMap.put("Product 2", product2);
        mapProductsId.put(0,"Product 1");
        mapProductsId.put(1,"Product 2");

        similarityTable.add(Arrays.asList(1.0, 0.5));
        similarityTable.add(Arrays.asList(0.5, 1.0));

        Double result = ctrlProd.checkProductsSimilarity("Product 1", "Product 2");

        assertEquals(0.5, result);
    }

    @Test
    public void testCheckProductsSimilarity_ProductNotFound() {
        assertThrows(ProductNotFoundException.class, () -> ctrlProd.checkProductsSimilarity("Product 1", "Nonexistent Product"));
        assertThrows(ProductNotFoundException.class, () -> ctrlProd.checkProductsSimilarity("Nonexistent Product 1", "Nonexistent Product 2"));
    }


    @Test
    public void testGenerateSimilarityTable_EmptyProducts() {
        List<List<Double>> result = ctrlProd.generateSimilarityTable();
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGenerateSimilarityTable_WithProducts() {
        Product product1 = new Product(0, "Product 1");
        Product product2 = new Product(1, "Product 2");

        Characteristics sharedCharacteristic = new Characteristics(0, "Shared");

        Set<Characteristics> characteristics1 = new HashSet<>(Collections.singletonList(sharedCharacteristic));
        Set<Characteristics> characteristics2 = new HashSet<>(Collections.singletonList(sharedCharacteristic));

        product1.setCharacteristics(characteristics1);
        product2.setCharacteristics(characteristics2);

        productsMap.put("Product 1", product1);
        productsMap.put("Product 2", product2);

        List<List<Double>> result = ctrlProd.generateSimilarityTable();

        assertEquals(1.0, result.get(0).get(1));
        assertEquals(1.0, result.get(1).get(0));
        assertEquals(1.0, result.get(0).get(0));
        assertEquals(1.0, result.get(1).get(1));
    }


    @Test
    public void testCalculateSimilarity_EmptySets() {
        double similarity = ctrlProd.calculateSimilarity(new HashSet<>(), new HashSet<>());
        assertEquals(0.0, similarity);
    }

    @Test
    public void testCalculateSimilarity_NonEmptySets() {
        Characteristics char1 = new Characteristics(0, "Characteristic 1");
        Characteristics char2 = new Characteristics(1, "Characteristic 2");

        Set<Characteristics> set1 = new HashSet<>(Arrays.asList(char1));
        Set<Characteristics> set2 = new HashSet<>(Arrays.asList(char1, char2));

        double similarity = ctrlProd.calculateSimilarity(set1, set2);

        assertEquals(0.5, similarity);
    }
    public void testGetProductName_Success() {
        Product productMock = mock(Product.class);
        when(productMock.getName()).thenReturn("Test Product");

        productsMap.put("Test Product", productMock);
        mapProductsId.put(1, "Test Product");

        String result = ctrlProd.getProductName(1);
        assertEquals("Test Product", result);
    }
}
