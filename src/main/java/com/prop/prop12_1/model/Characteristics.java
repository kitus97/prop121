package com.prop.prop12_1.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una característica que puede estar asociada a productos.
 * Cada característica tiene un identificador y un nombre.
 */
public class Characteristics {

    private int id;
    private String name;

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
     * Devuelve una representación en forma de cadena de la característica.
     *
     * @return una cadena que representa la característica
     */
    @Override
    public String toString() {
        return name;
    }
}
