package com.prop.prop12_1.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Representa un producto con un identificador y un nombre cada
 * producto tiene un conjunto de características y restricciones asociadas a el.
 */
public class Product {
    private Integer id;
    private String name;
    private Set<Characteristics> characteristics;
    private Set<Characteristics> restrictions;

    /**
     * Crea un nuevo producto con un identificador y un nombre.
     *
     * @param id   el identificador del producto
     * @param name el nombre del producto
     */
    public Product(Integer id, String name) {
        this.id = id;
        this.name = name;
        characteristics = new HashSet<>();
        restrictions = new HashSet<>();
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return el nombre del producto
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param name el nuevo nombre del producto
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el conjunto de características del producto.
     *
     * @return un conjunto de características
     */
    public Set<Characteristics> getCharacteristics() {
        return characteristics;
    }

    /**
     * Establece el conjunto de características del producto.
     *
     * @param characteristics el conjunto de características a asignar
     */
    public void setCharacteristics(Set<Characteristics> characteristics) {
        this.characteristics = characteristics;
    }

    /**
     * Añade una característica al producto.
     *
     * @param characteristic la característica a añadir
     */
    public void addCharacteristic(Characteristics characteristic) {
        characteristics.add(characteristic);
    }

    /**
     * Elimina una característica del producto.
     *
     * @param characteristic la característica a eliminar
     */
    public void removeCharacteristic(Characteristics characteristic) {
        characteristics.remove(characteristic);
    }

    /**
     * Obtiene el conjunto de restricciones del producto como representadas como strings.
     *
     * @return un conjunto de restricciones representadas como cadena de strings.
     */
    public Set<String> getRestrictions() {
        return restrictions.stream()
                .map(Characteristics::getName)
                .collect(Collectors.toSet());
    }

    /**
     * Establece el conjunto de restricciones del producto.
     *
     * @param restrictions el conjunto de restricciones a asignar
     */
    public void setRestrictions(Set<Characteristics> restrictions) {
        this.restrictions = restrictions;
    }

    /**
     * Añade una restricción al producto.
     *
     * @param restriction la restricción a añadir
     */
    public void addRestriction(Characteristics restriction) {
        restrictions.add(restriction);
    }

    /**
     * Elimina una restricción del producto.
     *
     * @param restriction la restricción a eliminar
     */
    public void removeRestriction(Characteristics restriction) {
        restrictions.remove(restriction);
    }

    /**
     * Obtiene el identificador único del producto.
     *
     * @return el identificador del producto
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el identificador único del producto.
     *
     * @param id el nuevo identificador del producto
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Devuelve una representación en forma de cadena del producto.
     *
     * @return una cadena que representa el producto
     */
    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", characteristics=" + characteristics +
                ", restrictions=" + restrictions +
                '}';
    }
}
