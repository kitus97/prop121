/*
package com.prop.prop12_1.controller;

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
public class TestCtrlProd {

    @Mock
    private Map<String, Characteristics> characteristics;

    @Mock
    private Map<String, Product> products;

    @Mock
    private List<List<Double>> similarityTable;

    @Mock
    private Map<String, Integer> mapProductsName;

    @Mock
    private Map<Integer,String> mapProductsId;

    private CtrlProd ctrlProd;

    @BeforeEach
    public void setUpBeforeTest() {
        ctrlProd = new CtrlProd();
    }

    @Test
    public void addCharacteristicCorrectName() {
        String characteristicName = "Test";

        assertDoesNotThrow(() -> ctrlProd.addCharacteristic(characteristicName));
        assertEquals(1, ctrlProd.getCharacteristics().size());
    }

    @Test
    public void addCharacteristicRepeatedName() {
        String characteristicName = "Test";
        when(characteristics.get(characteristicName)).thenReturn(mock(Characteristics.class));
        ctrlProd.setCharacteristics(characteristics);

        assertThrows(CharacteristicAlreadyAddedException.class, () -> ctrlProd.addCharacteristic(characteristicName));
    }

    @Test
    public void addProductCorrectName() {
        String productName = "Test";

        assertDoesNotThrow(() -> ctrlProd.addProduct(productName));
    }

    @Test
    public void addProductRepeatedName() {
        String productName = "Test";

        when(products.get(productName)).thenReturn(mock(Product.class));
        ctrlProd.setProducts(products);

        assertThrows(ProductAlreadyAddedException.class, () -> ctrlProd.addProduct(productName));
    }

    @Test
    public void checkProductSimilarityCorrect() {
        String productName = "Test";
        Product product = new Product(0,productName);
        ArrayList<Double> similarityList = mock(ArrayList.class);

        when(mapProductsName.getOrDefault(productName,-1)).thenReturn(0);
        when(similarityTable.get(0)).thenReturn(similarityList);
        when(similarityList.size()).thenReturn(1);
        when(similarityList.get(0)).thenReturn(0.0);
        when(mapProductsId.get(0)).thenReturn(productName);

        ctrlProd.setMapProductsId(mapProductsId);
        ctrlProd.setMapProductsId(mapProductsId);
        ctrlProd.setSimilarityTable(similarityTable);

        Map<String, Double> similarity = new HashMap<>();
        similarity.put("Test", 0.0);

        assertEquals(similarity, ctrlProd.checkProductSimilarities(productName));
    }

    @Test
    public void checkProductSimilarityIncorrectName() {
        String productName = "Test";

        assertThrows(ProductNotFoundException.class, () -> ctrlProd.checkProductSimilarities(productName));
    }

    @Test
    public void generateSimilarityTableCorrect() {

        Map<String, Product> products = new HashMap<>();

        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);

        Characteristics characteristic1 = new Characteristics();
        Characteristics characteristic2 = new Characteristics();

        Set<Characteristics> characteristics1 = new HashSet<>();
        characteristics1.add(characteristic1);
        characteristics1.add(characteristic2);

        Set<Characteristics> characteristics2 = new HashSet<>();
        characteristics2.add(characteristic1);

        when(product1.getCharacteristics()).thenReturn(characteristics1);
        when(product2.getCharacteristics()).thenReturn(characteristics2);
        when(mapProductsName.get("Test1")).thenReturn(0);
        when(mapProductsName.get("Test2")).thenReturn(1);

        products.put("Test1", product1);
        products.put("Test2", product2);
        ctrlProd.setProducts(products);
        ctrlProd.setMapProductsId(mapProductsId);

        ArrayList<ArrayList<Double>> expectedResult = new ArrayList<>();
        ArrayList<Double> similarity1 = new ArrayList<>();
        similarity1.add(1.0);
        similarity1.add(0.5);
        ArrayList<Double> similarity2 = new ArrayList<>();
        similarity2.add(0.5);
        similarity2.add(1.0);
        expectedResult.add(similarity1);
        expectedResult.add(similarity2);

        assertEquals(expectedResult, ctrlProd.generateSimilarityTable());
    }

}
*/
package com.prop.prop12_1.controller;
import com.prop.prop12_1.controller.CtrlProd;
import com.prop.prop12_1.exceptions.*;
import com.prop.prop12_1.model.Characteristics;
import com.prop.prop12_1.model.Product;
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
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ctrlProd = new CtrlProd();

        // Initialize maps and lists
        characteristicsMap = new HashMap<>();
        productsMap = new HashMap<>();
        similarityTable = new ArrayList<>();
        mapProductsId = new HashMap<>();

        // Set default values for mocks
        when(characteristicMock.getName()).thenReturn("Characteristic 1");
        when(characteristicMock2.getName()).thenReturn("Characteristic 2");
        when(productMock.getName()).thenReturn("Product 1");
        when(productMock.getId()).thenReturn(0);

        // Assign maps and lists to CtrlProd
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
    public void testRemoveCharacteristic_Success() {
        characteristicsMap.put("Characteristic 1", characteristicMock);
        ctrlProd.removeCharacteristic("Characteristic 1");
        assertFalse(characteristicsMap.containsKey("Characteristic 1"));
    }

    @Test
    public void testRemoveCharacteristic_NotFound() {
        assertThrows(CharacteristicNotFoundException.class, () -> ctrlProd.removeCharacteristic("Characteristic X"));
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
    public void testModifySimilarity_Success() {
        Product product2Mock = mock(Product.class);
        when(product2Mock.getId()).thenReturn(1);
        productsMap.put("Product 1", productMock);
        productsMap.put("Product 2", product2Mock);

        similarityTable.add(Arrays.asList(1.0, 0.5));
        similarityTable.add(Arrays.asList(0.5, 1.0));

        ctrlProd.modifySimilarity("Product 1", "Product 2", 0.8);

        assertEquals(0.8, similarityTable.get(0).get(1));
        assertEquals(0.8, similarityTable.get(1).get(0));
    }

    @Test
    public void testModifySimilarity_ProductNotFound() {
        assertThrows(ProductNotFoundException.class, () -> ctrlProd.modifySimilarity("Product 1", "Product X", 0.8));
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
    public void testSetSimilarities_WithSimilaritiesArray() {
        ctrlProd.addProduct("Product 1");
        ctrlProd.addProduct("Product 2");

        Double[] similarities = {0.7};
        boolean result = ctrlProd.setSimilarities(similarities);

        assertTrue(result);
        assertEquals(0.7, similarityTable.get(1).get(0));
    }

    @Test
    public void testSetSimilarities_WithNullSimilarities() {
        ctrlProd.addProduct("Product 1");
        ctrlProd.addProduct("Product 2");

        boolean result = ctrlProd.setSimilarities(null);

        assertTrue(result);
        assertEquals(1.0, similarityTable.get(1).get(1));
    }

    @Test
    public void testCalculateSimilarity_EmptySets() {
        double similarity = ctrlProd.calculateSimilarity(new HashSet<>(), new HashSet<>());
        assertEquals(0, similarity);
    }

    @Test
    public void testGenerateSimilarityTable_EmptyProducts() {
        List<List<Double>> result = ctrlProd.generateSimilarityTable();
        assertTrue(result.isEmpty());
    }

    @Test
    public void testAddRestrictionProduct_Success() {
        characteristicsMap.put("Restriction 1", characteristicMock);
        productsMap.put("Product 1", productMock);
        ctrlProd.addRestrictionProduct("Restriction 1", "Product 1");

        verify(productMock).addRestriction(characteristicMock);
    }

    @Test
    public void testAddRestrictionProduct_RestrictionAlreadyAdded() {
        characteristicsMap.put("Restriction 1", characteristicMock);
        productsMap.put("Product 1", productMock);
        when(productMock.getRestrictions()).thenReturn(Set.of("Restriction 1"));

        assertThrows(RestrictionAlreadyAddedToProductException.class, () -> ctrlProd.addRestrictionProduct("Restriction 1", "Product 1"));
    }

    @Test
    public void testRemoveRestrictionProduct_NotFound() {
        assertThrows(ProductNotFoundException.class, () -> ctrlProd.removeRestrictionProduct("Nonexistent Restriction", "Product X"));
    }

    @Test
    public void testAddCharacteristicProduct_Success() {
        characteristicsMap.put("Characteristic 1", characteristicMock);
        productsMap.put("Product 1", productMock);
        ctrlProd.addCharacteristicProduct("Characteristic 1", "Product 1");

        verify(productMock).addCharacteristic(characteristicMock);
    }

    @Test
    public void testRemoveCharacteristicProduct_CharacteristicNotFound() {
        assertThrows(CharacteristicNotFoundException.class, () -> ctrlProd.removeCharacteristicProduct("Characteristic X", "Product X"));
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
    public void testListCharacteristics_Empty() {
        assertTrue(ctrlProd.listCharacteristics().isEmpty());
    }

    @Test
    public void testListProducts_Empty() {
        assertTrue(ctrlProd.listProducts().isEmpty());
    }

    @Test
    public void testFindCharacteristicByName() {
        List<Characteristics> characteristicList = Arrays.asList(characteristicMock, characteristicMock2);
        assertTrue(CtrlProd.findCharacteristicByName(characteristicList, "Characteristic 1"));
        assertFalse(CtrlProd.findCharacteristicByName(characteristicList, "Nonexistent Characteristic"));
    }
}
