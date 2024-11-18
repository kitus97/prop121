package com.prop.prop12_1.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una característica que puede estar asociada a uno o más productos.
 * Cada característica tiene un identificador, un nombre y una lista
 * que contiene todos los productos con dicha característica.
 */
public class Characteristics {

    private int id;
    private String name;
    private List<Product> associatedProducts = new ArrayList<>();

    /**
     * Constructor que inicializa la característica con un identificador y un nombre.
     *
     * @param id   el identificador de la característica
     * @param name el nombre de la característica
     */
    public Characteristics(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Constructor por defecto.
     */
    public Characteristics() {}

    /**
     * Obtiene el identificador de la característica.
     *
     * @return el identificador de la característica
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador de la característica.
     *
     * @param id el nuevo identificador de la característica
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la característica.
     *
     * @return el nombre de la característica
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre de la característica.
     *
     * @param name el nuevo nombre de la característica
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene la lista de productos asociados a la característica.
     *
     * @return una lista de productos asociados
     */
    public List<Product> getAssociatedProducts() {
        return associatedProducts;
    }

    /**
     * Establece la lista de productos asociados a la característica.
     *
     * @param associatedProducts la nueva lista de productos asociados
     */
    public void setAssociatedProducts(List<Product> associatedProducts) {
        this.associatedProducts = associatedProducts;
    }

    /**
     * Añade un producto a la lista de productos asociados.
     *
     * @param product el producto a asociar
     */
    public void addAssociatedProduct(Product product) {
        associatedProducts.add(product);
    }

    /**
     * Elimina un producto de la lista de productos asociados.
     *
     * @param product el producto a eliminar de la lista de asociados
     */
    public void removeAssociatedProduct(Product product) {
        associatedProducts.remove(product);
    }

    /**
     * Devuelve una representación en forma de cadena de la característica.
     *
     * @return una cadena que representa la característica
     */
    @Override
    public String toString() {
        return name;
    }
}
