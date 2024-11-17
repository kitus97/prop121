package com.prop.prop12_1.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Representa una estantería que almacena productos basándose en sus restricciones.
 * <p>
 * Un objeto {@code Shelf} tiene un nombre y un sistema de distribución, donde la distribución
 * se representa como una lista de sets. Cada set en la lista corresponde a una hueco
 * específico de la estantería y contiene los nombres de las restricciones aplicables a ese hueco.
 * </p>
 */
public class Shelf{

    private String name;
    private List<Set<String>> distribution;

    /**
     * Construye una {@code Shelf} con el nombre y tamaño especificados.
     *
     * @param n El nombre de la estantería.
     * @param t El número de huecos en la estantería.
     * @throws IndexOutOfBoundsException si el tamaño especificado es negativo.
     */
    public Shelf(String n, int t) {
        if(t <= 0) throw new IndexOutOfBoundsException("Invalid size");
        name = n;
        distribution = new ArrayList<Set<String>>();
        for (int i = 0; i < t; ++i) {
            distribution.add(new HashSet<String>());
        }

    }

    public String getName() {
        return name;
    }

    public List<Set<String>> getDistribution() {
        return distribution;
    }

    /*Pre: restriction es el nombre de una caracteristica valida y t <= distribution.size()
     */
    /**
     * Asigna una restricción a un hueco específico de la estantería.
     *
     * @param restriction El nombre de la restricción a añadir.
     * @param t           El índice del hueco al que se añadirá la restricción.
     * @throws IndexOutOfBoundsException si el índice especificado está fuera de rango.
     */
    public void setRestriction(String restriction, int t){
        if(distribution.size() <= t) throw new IndexOutOfBoundsException("The index is out of range");
        else distribution.get(t).add(restriction);
    }

    /**
     * Elimina todas las restricciones de un hueco específico de la estantería.
     *
     * @param t El índice de la sección a limpiar.
     * @throws IndexOutOfBoundsException si el índice especificado está fuera de rango.
     */
    public void deleteRestrictions(int t){
        if(distribution.size() <= t) throw new IndexOutOfBoundsException("The index is out of range");
        else distribution.get(t).clear();
    }

    /**
     * Cambia el tamaño de la estantería ajustando el número de huecos.
     * <p>
     * Si el nuevo tamaño es mayor, se añadirán huecos vacíos adicionales.
     * Si el nuevo tamaño es menor, se eliminarán los huecos sobrantes.
     * </p>
     *
     * @param t El nuevo tamaño de la estantería.
     * @throws IndexOutOfBoundsException si el tamaño especificado es negativo.
     */
    public void resizeShelf(int t) {
        if (t < 0) throw new IndexOutOfBoundsException("Invalid size");

        else {
            if (distribution.size() < t) {
                for (int i = distribution.size(); i < t; ++i) {
                    distribution.add(new HashSet<String>());
                }
            } else {
                if (distribution.size() > t) {
                    distribution.subList(t, distribution.size()).clear();
                }

            }

        }
    }

    /**
     * Devuelve una representación en forma de string de la estantería, incluyendo su nombre y distribución.
     *
     * @return Una representación en forma de string de la estantería.
     */
    @Override
    public String toString() {
        return "Shelf{" +
                "name='" + name + '\'' +
                ", distribution=" + distribution +
                '}';
    }
}
