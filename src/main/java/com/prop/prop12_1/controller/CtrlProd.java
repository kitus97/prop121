package com.prop.prop12_1.controller;

import com.prop.prop12_1.exceptions.*;
import com.prop.prop12_1.model.Characteristics;
import com.prop.prop12_1.model.Product;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Este controlador es el encargado de gestionar toddo lo relacionado con productos, características
 * y restricciones, ademas es el encargado de generar las matrizes de similitud entre diferentes productos.
 * Permite añadir, modificar y consultar productos, características y restricciones, así como calcular similitudes
 * entre productos y generar las matrices de similitud..
 */
public class CtrlProd {

    private static Map<String, Product> products = new HashMap<>();
    private static Map<String, Characteristics> characteristics = new HashMap<>();
    private static List<List<Double>> similarityTable = new ArrayList<>();
    private static Map<Integer, String> mapProductsId = new HashMap<>();

    /**
     * Constructor por defecto.
     */
    public CtrlProd() {}

    /**
     * Añade una nueva característica al sistema si esta no existe ya.
     *
     * @param characteristicName el nombre de la característica a añadir
     * @throws CharacteristicAlreadyAddedException si la característica ya existe
     */
    public void addCharacteristic(String characteristicName) {
        if (findCharacteristic(characteristicName) == null) {
            Characteristics characteristic = new Characteristics(characteristics.size(), characteristicName);
            characteristics.put(characteristicName, characteristic);
        } else {
            throw new CharacteristicAlreadyAddedException("Characteristic with name '" + characteristicName + "' was already added");
        }
    }

    /**
     * Consulta las similitudes de un prodcuto en especifico con el resto
     * de productos añadidos.
     *
     * @param productName el nombre del producto
     * @return un mapa con los nombres de productos y su similitud com el producto seleccionado.
     * @throws ProductNotFoundException si el producto no existe
     */
    public Map<String, Double> checkProductSimilarities(String productName) {
        Product product = products.get(productName);
        if (product == null) {
            throw new ProductNotFoundException("Product with name '" + productName + "' was not found");
        }

        Map<String, Double> similarities = new HashMap<>();
        IntStream.range(0, similarityTable.get(product.getId()).size())
                .forEach(i -> similarities.put(mapProductsId.get(i), similarityTable.get(product.getId()).get(i)));

        return similarities;
    }

    /**
     * Modifica el nivel de similitud entre dos productos.
     *
     * @param productName1 el nombre del primer producto
     * @param productName2 el nombre del segundo producto
     * @param newValue     el nuevo valor de similitud a añadir
     * @throws ProductNotFoundException si alguno de los productos no existe
     */
    public void modifySimilarity(String productName1, String productName2, Double newValue) {
        Product product1 = products.get(productName1);
        Product product2 = products.get(productName2);

        if (product1 == null && product2 == null) {
            throw new ProductNotFoundException("Products with name '" + productName1 + "' and '" + productName2 + "' were not found");
        } else if (product1 == null) {
            throw new ProductNotFoundException("Product with name '" + productName1 + "' was not found");
        } else if (product2 == null) {
            throw new ProductNotFoundException("Product with name '" + productName2 + "' was not found");
        }
        else if (product1 == product2) {
            throw new sameProductException("Both are product " + productName1);
        }


        similarityTable.get(product1.getId()).set(product2.getId(), newValue);
        similarityTable.get(product2.getId()).set(product1.getId(), newValue);
    }

    /**
     * Comprueba la similitud entre dos productos.
     *
     * @param productName1 el nombre del primer producto
     * @param productName2 el nombre del segundo producto
     * @return el nivel de similitud
     * @throws ProductNotFoundException si alguno de los productos no existe
     */
    public Double checkProductsSimilarity(String productName1, String productName2) {
        Product product1 = products.get(productName1);
        Product product2 = products.get(productName2);

        if (product1 == null && product2 == null) {
            throw new ProductNotFoundException("Products with name '" + productName1 + "' and '" + productName2 + "' were not found");
        } else if (product1 == null) {
            throw new ProductNotFoundException("Product with name '" + productName1 + "' was not found");
        } else if (product2 == null) {
            throw new ProductNotFoundException("Product with name '" + productName2 + "' was not found");
        }
        else if (product1 == product2) {
            throw new sameProductException("Both are product " + productName1);
        }

        return similarityTable.get(product1.getId()).get(product2.getId());
    }

    /**
     * Añade un nuevo producto al sistema si este no existe.
     *
     * @param productName el nombre del producto
     * @throws ProductAlreadyAddedException si el producto ya existe
     */
    public void addProduct(String productName) {
        if (findProduct(productName) == null) {
            Product newProduct = new Product(products.size(), productName);
            products.put(productName, newProduct);
            int size = products.size();
            mapProductsId.put(size - 1, productName);
        } else {
            throw new ProductAlreadyAddedException("Product with name '" + productName + "' was already added");
        }
    }

    /**
     * Esta funcion sera llamada justo despues de añadir un nuevo producto al sistema
     * si el array que llega es null queremos que se genereren las similitudes mediante
     * caracteristicas em comun, sino vienen dadas por el usuario manualmente..
     *
     * @param similarities un array con las similitudes del nuevo producto
     * @return true si la operación fue exitosa
     * @throws SimilarityArrayIncorrectSizeException si el tamaño del array es incorrecto
     */
    public Boolean setSimilarities(Double[] similarities) {
        if (products.size() == 1) {
            List<Double> row = new ArrayList<>();
            row.add(0.0);
            similarityTable.add(row);
        } else if (similarities == null) {
            int idx1 = products.size() - 1;
            Set<Characteristics> characteristics1 = products.get(mapProductsId.get(idx1)).getCharacteristics();
            List<Double> newRow = new ArrayList<>();
            for (int i = 0; i < idx1; i++) {
                Set<Characteristics> characteristics2 = products.get(mapProductsId.get(i)).getCharacteristics();
                double similarity = calculateSimilarity(characteristics1, characteristics2);
                newRow.add(similarity);
            }
            newRow.add(0.0);
            similarityTable.add(newRow);
            for (int i = 0; i < idx1; i++) {
                similarityTable.get(i).add(newRow.get(i));
            }
        } else if (similarities.length == products.size() - 1) {
            List<Double> newRow = new ArrayList<>(Arrays.asList(similarities));
            newRow.add(0.0);
            similarityTable.add(newRow);
            for (int i = 0; i < similarities.length; i++) {
                similarityTable.get(i).add(similarities[i]);
            }
        } else {
            throw new SimilarityArrayIncorrectSizeException("The similarity array has incorrect size");
        }
        return true;
    }

    /**
     * Dado un producto la funcion modifica las similitudes con el resto
     * de productos, si el array es null lo hace mediante características
     * sino lo hace con los valores entrados por el usuario.
     *
     * @param productName  el nombre del producto cuya similitud se desea modificar
     * @param similarities un array con los nuevos valores de similitud
     * @throws ProductNotFoundException              si el producto no existe
     * @throws SimilarityArrayIncorrectSizeException si el tamaño del array de similitudes no coincide con el esperado
     */
    public void modifyProductSimilarities(String productName, Double[] similarities) {
        Product product1 = products.get(productName);
        if (product1 != null) {
            int idx = product1.getId();
            if (similarities == null) {
                Set<Characteristics> characteristics1 = products.get(mapProductsId.get(idx)).getCharacteristics();
                Set<Characteristics> characteristics2;
                for (int i = 0; i < similarityTable.size(); i++) {
                    if (i != idx) {
                        characteristics2 = products.get(mapProductsId.get(i)).getCharacteristics();
                        double similarity = calculateSimilarity(characteristics1, characteristics2);
                        similarityTable.get(idx).set(i, similarity);
                        similarityTable.get(i).set(idx, similarity);
                    }
                }
            } else if (similarities.length == products.size() - 1) {
                int j = 0;
                for (int i = 0; i < similarityTable.size(); i++) {
                    if (i != idx) {
                        similarityTable.get(idx).set(i, similarities[j]);
                        similarityTable.get(i).set(idx, similarities[j]);
                        ++j;
                    }
                }
            } else {
                throw new SimilarityArrayIncorrectSizeException("The similarity array has incorrect size");
            }
        } else {
            throw new ProductNotFoundException("Product with name '" + productName + "' was not found");
        }
    }

    /**
     * Calcula la similitud entre dos conjuntos de características utilizando
     * una formúla.
     *
     * @param characteristics1 el primer conjunto de características
     * @param characteristics2 el segundo conjunto de características
     * @return el valor de similitud, un número entre 0 y 1
     */
    public double calculateSimilarity(Set<Characteristics> characteristics1, Set<Characteristics> characteristics2) {
        Set<Characteristics> intersection = new HashSet<>(characteristics1);
        intersection.retainAll(characteristics2);
        Set<Characteristics> union = new HashSet<>(characteristics1);
        union.addAll(characteristics2);

        return union.isEmpty() ? 0 : (double) intersection.size() / union.size();
    }

    /**
     * Genera una tabla de similitudes entre todos los productos en el sistema
     * mediante la fórmula de características.
     *
     * @return una lista bidimensional representando la tabla de similitudes
     */
    public List<List<Double>> generateSimilarityTable() {
        int productsSize = products.size();
        List<List<Double>> generatedSimilarities = new ArrayList<>();

        for (int i = 0; i < productsSize; i++) {
            List<Double> row = new ArrayList<>(Collections.nCopies(productsSize, 0.0));
            generatedSimilarities.add(row);
        }

        List<Product> productsList = new ArrayList<>(products.values());

        for (int i = 0; i < productsSize; i++) {
            Product product1 = productsList.get(i);
            Set<Characteristics> characteristics1 = product1.getCharacteristics();
            for (int j = i; j < productsSize; j++) {
                Product product2 = productsList.get(j);
                Set<Characteristics> characteristics2 = product2.getCharacteristics();

                double similarity = calculateSimilarity(characteristics1, characteristics2);
                generatedSimilarities.get(product1.getId()).set(product2.getId(), similarity);
                generatedSimilarities.get(product2.getId()).set(product1.getId(), similarity);
            }
        }

        return generatedSimilarities;
    }

    /**
     * Añade una restricción a un producto.
     *
     * @param restrictionName el nombre de la restricción a añadir
     * @param productName     el nombre del producto al que se desea añadir la restricción
     * @throws CharacteristicNotFoundException           si la restricción no existe
     * @throws ProductNotFoundException                  si el producto no existe
     * @throws RestrictionAlreadyAddedToProductException si la restricción ya está añadida al producto
     */
    public void addRestrictionProduct(String restrictionName, String productName) {
        Characteristics c = findCharacteristic(restrictionName);
        if (c != null) {
            Product p = findProduct(productName);
            if (p != null) {
                if (p.getRestrictions().contains(c.getName())) {
                    throw new RestrictionAlreadyAddedToProductException("Restriction with name '" + restrictionName + "' was already added to product");
                } else {
                    p.addRestriction(c);
                    c.addAssociatedProduct(p);
                }
            } else {
                throw new ProductNotFoundException("Product with name '" + productName + "' was not found");
            }
        } else {
            throw new CharacteristicNotFoundException("Restriction with name '" + restrictionName + "' was not found");
        }
    }

    /**
     * Elimina una restricción de un producto.
     *
     * @param restrictionName el nombre de la restricción a eliminar
     * @param productName     el nombre del producto del que se desea eliminar la restricción
     * @throws CharacteristicNotFoundException       si la restricción no existe
     * @throws ProductNotFoundException              si el producto no existe
     * @throws RestrictionNotFoundInProductException si la restricción no está asociada al producto
     */
    public void removeRestrictionProduct(String restrictionName, String productName) {
        Characteristics c = findCharacteristic(restrictionName);
        if (c != null) {
            Product p = findProduct(productName);
            if (p != null) {
                if (p.getRestrictions().contains(c.getName())) {
                    p.removeRestriction(c);
                } else {
                    throw new RestrictionNotFoundInProductException("Restriction with name '" + restrictionName + "' was not found in product");
                }
            } else {
                throw new ProductNotFoundException("Product with name '" + productName + "' was not found");
            }
        } else {
            throw new CharacteristicNotFoundException("Restriction with name '" + restrictionName + "' was not found");
        }
    }

    /**
     * Añade una característica a un producto.
     *
     * @param characteristicName el nombre de la característica a añadir
     * @param productName        el nombre del producto al que se desea añadir la característica
     * @throws CharacteristicNotFoundException             si la característica no existe
     * @throws ProductNotFoundException                    si el producto no existe
     * @throws CharacteristicAlreadyAddedToProductException si la característica ya está añadida al producto
     */
    public void addCharacteristicProduct(String characteristicName, String productName) {
        Characteristics c = findCharacteristic(characteristicName);
        if (c != null) {
            Product p = findProduct(productName);
            if (p != null) {
                if (p.getCharacteristics().contains(c)) {
                    throw new CharacteristicAlreadyAddedToProductException("Characteristic with name '" + characteristicName + "' was already added");
                } else {
                    p.addCharacteristic(c);
                    c.addAssociatedProduct(p);
                }
            } else {
                throw new ProductNotFoundException("Product with name '" + productName + "' was not found");
            }
        } else {
            throw new CharacteristicNotFoundException("Characteristic with name '" + characteristicName + "' was not found");
        }
    }

    /**
     * Elimina una característica de un producto.
     *
     * @param characteristicName el nombre de la característica a eliminar
     * @param productName        el nombre del producto del que se desea eliminar la característica
     * @throws CharacteristicNotFoundException       si la característica no existe
     * @throws ProductNotFoundException              si el producto no existe
     * @throws CharacteristicNotFoundInProductException si la característica no está asociada al producto
     */
    public void removeCharacteristicProduct(String characteristicName, String productName) {
        Characteristics c = findCharacteristic(characteristicName);
        if (c != null) {
            Product p = findProduct(productName);
            if (p != null) {
                if (p.getCharacteristics().contains(c)) {
                    p.removeCharacteristic(c);
                    c.removeAssociatedProduct(p);
                } else {
                    throw new CharacteristicNotFoundInProductException("Characteristic with name '" + characteristicName + "' was not found in product");
                }
            } else {
                throw new ProductNotFoundException("Product with name '" + productName + "' was not found");
            }
        } else {
            throw new CharacteristicNotFoundException("Characteristic with name '" + characteristicName + "' was not found");
        }
    }

    /**
     * Verifica si un producto existe en el sistema.
     *
     * @param productName el nombre del producto
     * @return true si el producto existe, false en caso contrario
     */
    private Boolean productExists(String productName) {
        return products.containsKey(productName);
    }

    /**
     * Verifica si una característica existe en el sistema.
     *
     * @param characteristicName el nombre de la característica
     * @return true si la característica existe, false en caso contrario
     */
    private Boolean characteristicExists(String characteristicName) {
        return characteristics.containsKey(characteristicName);
    }

    /**
     * Busca un producto por su nombre.
     *
     * @param productName el nombre del producto
     * @return el producto si existe, null si no se encuentra
     */
    public Product findProduct(String productName) {
        return products.get(productName);
    }

    /**
     * Busca una característica por su nombre.
     *
     * @param characteristicName el nombre de la característica
     * @return la característica si existe, null si no se encuentra
     */
    public Characteristics findCharacteristic(String characteristicName) {
        return characteristics.get(characteristicName);
    }

    /**
     * Obtiene el mapa de productos almacenados en el sistema.
     *
     * @return un mapa con los nombres de productos como clave y sus objetos Product como valores
     */
    public Map<String, Product> getProducts() {
        return products;
    }

    /**
     * Lista todas las características disponibles en el sistema como array
     * de strings con su nombre.
     *
     * @return una lista de strings representando las características
     */
    public List<String> listCharacteristics() {
        return characteristics.values().stream()
                .map(Characteristics::getName)
                .collect(Collectors.toList());
    }

    /**
     * Lista todos los productos disponibles en el sistema como strings
     * con su nombre.
     *
     * @return una lista de strings representando los nombres de los productos
     */
    public List<String> listProducts() {
        return products.values().stream()
                                .map(Product::getName)
                                .collect(Collectors.toList());
    }

    /**
     * Devuelve una representación textual de un producto.
     *
     * @param productName el nombre del producto
     * @return una cadena que representa el producto
     */
    public String printProduct(String productName) {
        Product product = findProduct(productName);

        if (product == null) {
            throw new ProductNotFoundException("Product with name '" + productName + "' was not found");
        }

        return product.toString();
    }

    /**
     * Establece un nuevo mapa de productos en el sistema.
     *
     * @param products un mapa donde las claves son nombres de productos y los valores son objetos Product
     */
    public void setProducts(Map<String, Product> products) {
        CtrlProd.products = products;
    }

    /**
     * Obtiene el mapa de características almacenadas en el sistema.
     *
     * @return un mapa con los nombres de características como clave y sus objetos Characteristics como valores
     */
    public Map<String, Characteristics> getCharacteristics() {
        return characteristics;
    }

    /**
     * Establece un nuevo mapa de características en el sistema.
     *
     * @param characteristics un mapa donde las claves son nombres de características y los valores son objetos Characteristics
     */
    public void setCharacteristics(Map<String, Characteristics> characteristics) {
        CtrlProd.characteristics = characteristics;
    }

    /**
     * Obtiene la tabla de similitudes entre productos.
     *
     * @return una lista bidimensional que representa la tabla de similitudes
     */
    public List<List<Double>> getSimilarityTable() {
        return similarityTable;
    }

    /**
     * Obtiene las restricciones asociadas a un producto específico.
     *
     * @param productName el nombre del producto
     * @return un conjunto de strings que representan las restricciones del producto
     * @throws ProductNotFoundException si el producto no existe
     */
    public Set<String> getRestrictionsProducts(String productName) {
        Product p = findProduct(productName);
        if (p != null) {
            return p.getRestrictions();
        } else {
            throw new ProductNotFoundException("Product with name '" + productName + "' was not found");
        }
    }

    /**
     * Obtiene las características asociadas a un producto específico.
     *
     * @param productName el nombre del producto
     * @return una lista de strings que representan las características del producto
     * @throws ProductNotFoundException si el producto no existe
     */
    public List<String> getCharacteristicsProducts(String productName) {
        Product p = findProduct(productName);
        if (p != null) {
            return p.getCharacteristics().stream()
                    .map(Characteristics::getName)
                    .collect(Collectors.toList());
        } else {
            throw new ProductNotFoundException("Product with name '" + productName + "' was not found");
        }
    }

    /**
     * Establece una nueva tabla de similitudes entre productos.
     *
     * @param arraySimilarityTable una lista bidimensional que representa la nueva tabla de similitudes
     */
    public void setSimilarityTable(List<List<Double>> arraySimilarityTable) {
        similarityTable = arraySimilarityTable;
    }

    /**
     * Obtiene el mapa de IDs de productos.
     *
     * @return un mapa donde las claves son IDs de productos y los valores son sus nombres
     */
    public Map<Integer, String> getMapProductsId() {
        return mapProductsId;
    }

    /**
     * Establece un nuevo mapa de IDs de productos.
     *
     * @param mapProductsId un mapa donde las claves son IDs de productos y los valores son sus nombres
     */
    public void setMapProductsId(Map<Integer, String> mapProductsId) {
        CtrlProd.mapProductsId = mapProductsId;
    }

    /**
     * Busca una característica en una lista por su nombre.
     *
     * @param characteristics   una lista de características
     * @param characteristicName el nombre de la característica a buscar
     * @return true si la característica está en la lista, false en caso contrario
     */
    public static boolean findCharacteristicByName(List<Characteristics> characteristics, String characteristicName) {
        return characteristics.stream()
                .anyMatch(characteristic -> characteristic.getName().equalsIgnoreCase(characteristicName));
    }

    /**
     * Obtiene el nombre de un producto a partir de su ID.
     *
     * @param id el ID del producto
     * @return el nombre del producto asociado al ID
     */
    public String getProductName(int id) {
        return mapProductsId.get(id);
    }




}
