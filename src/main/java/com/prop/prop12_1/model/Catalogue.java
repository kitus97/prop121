package com.prop.prop12_1.model;

import java.util.*;

import com.prop.prop12_1.controller.CtrlProd;
import com.prop.prop12_1.exceptions.ProductAlreadyAddedException;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Representa un catálogo que contiene una serie de productos.
 * <p>
 * La clase {@code Catalogue} permite la gestión de productos, proporcionando funcionalidades
 * como agregar, eliminar y recuperar información de los productos.
 * Cada catálogo tiene un nombre y un mapa de sus productos.
 * </p>
 */
public class Catalogue {
    private static Map<String, Product> allProducts = new CtrlProd().getProducts();
    private static Map <Integer, String> idToString = new CtrlProd().getMapProductsId();
    private String name;
    private Map<String, Product> products;

    /**
     * Construye un nuevo {@code Catalogue} con el nombre especificado y sin ningún producto.
     *
     * @param name El nombre del catálogo.
     */
    public Catalogue(String name) {
        this.name = name;
        products = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Devuelve una lista de los nombres de productos en el catálogo.
     *
     * @return Una lista de nombres de productos actualmente en el catálogo.
     */
    public List<String> getProductNames() {
        return new ArrayList<>(products.keySet());
    }

    /**
     * Devuelve una lista de detalles de los productos a través de pares de IDs y sus restricciones asociadas.
     *
     * @return Una lista de pares, donde cada par contiene el ID de un producto y su conjunto de restricciones.
     */
    public List<Pair<Integer, Set<String>>> getProductsArray() {
        List<Pair<Integer, Set<String>>> ret= new ArrayList<Pair<Integer, Set<String>>>();
        for(Product p : products.values()) {
            ret.add(Pair.of(p.getId(), p.getRestrictions()));
        }
        return ret;
    }

    public List<String> getProductNamesAuxiliar(List<Pair<Integer, Set<String>>> solution) {
        List<Integer> productIDs = solution.stream().map(Pair::getLeft).toList();
        List <String> productNames = new ArrayList<>();
        for(Integer i : productIDs) {
            productNames.add(idToString.get(i));
        }
        return productNames;
    }



    public void setProducts(Map<String, Product> products) {
        this.products = products;
    }

    public Map<String, Product> getProducts() {
        return products;
    }

    /**
     * Agrega un producto al catálogo mediante su nombre.
     *
     * @param product El nombre del producto a agregar.
     * @throws NoSuchElementException si el producto no existe en la lista global de productos.
     * @throws ProductAlreadyAddedException si el producto ya está presente en el catálogo.
     */
    public void addProduct(String product) {
        if(!allProducts.containsKey(product)) throw new NoSuchElementException("Error: The product " + product + " does not exist.");
        else if(products.containsKey(product)) throw new ProductAlreadyAddedException("The product " + product + " already exists in the catalogue.");
        else products.put(product, allProducts.get(product));
    }

    /**
     * Elimina un producto del catálogo mediante su nombre.
     *
     * @param product El nombre del producto a eliminar.
     * @throws NoSuchElementException si el producto no se encuentra en el catálogo.
     */
    public void removeProduct(String product) {
        if(products.remove(product) == null) throw new NoSuchElementException("Error: The product " + product + " does not exist in the catalogue.");
    }

    /**
     * Devuelve una representación en forma de string del catálogo, incluyendo su nombre y sus productos.
     *
     * @return Una representación en forma de string del catálogo.
     */
    @Override
    public String toString() {
        return "Catalogue{" +
                "name='" + name + '\'' +
                ", products=" + products +
                '}';
    }
}
