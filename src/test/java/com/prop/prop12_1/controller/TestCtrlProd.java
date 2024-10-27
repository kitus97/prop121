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
    private ArrayList<ArrayList<Double>> similarityTable;

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
        Product product = new Product(productName);
        ArrayList<Double> similarityList = mock(ArrayList.class);

        when(mapProductsName.getOrDefault(productName,-1)).thenReturn(0);
        when(similarityTable.get(0)).thenReturn(similarityList);
        when(similarityList.size()).thenReturn(1);
        when(similarityList.get(0)).thenReturn(0.0);
        when(mapProductsId.get(0)).thenReturn(productName);

        ctrlProd.setMapProductsName(mapProductsName);
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
        ctrlProd.setMapProductsName(mapProductsName);

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
