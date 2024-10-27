package com.prop.prop12_1.controller;

import com.prop.prop12_1.exceptions.CharacteristicAlreadyAddedException;
import com.prop.prop12_1.exceptions.CharacteristicNotFoundException;
import com.prop.prop12_1.exceptions.ProductAlreadyAddedException;
import com.prop.prop12_1.exceptions.ProductNotFoundException;
import com.prop.prop12_1.model.Characteristics;
import com.prop.prop12_1.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

}
