package com.prop.prop12_1.algorithm;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Test {

    public static void main(String[] args) {

        Random rand = new Random();

        // Crear la estantería con 100 huecos, de los cuales 15 tienen restricciones
        ArrayList<Set<String>> estanteria = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Set<String> restricciones = new HashSet<>();
            if (i < 15) { // Solo los primeros 15 huecos tienen restricciones
                if (rand.nextBoolean()) restricciones.add("congelado");
                if (rand.nextBoolean()) restricciones.add("fragil");
            }
            estanteria.add(restricciones);
        }

        // Crear la lista de 100 productos, cada uno con un ID y características
        ArrayList<Pair<Integer, Set<String>>> productos = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Set<String> caracteristicas = new HashSet<>();
            if (i < 15) { // Solo los primeros 15 huecos tienen restricciones
                if (rand.nextBoolean()) caracteristicas.add("congelado");
                if (rand.nextBoolean()) caracteristicas.add("fragil");
            }
            productos.add(Pair.of(i, caracteristicas));
        }

        // Crear la matriz de similitud (100x100) con valores aleatorios entre 0 y 1, redondeados a dos decimales
        ArrayList<ArrayList<Double>> matrizSimilitud = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            ArrayList<Double> fila = new ArrayList<>();
            for (int j = 0; j < 100; j++) {
                double similitud = i == j ? 1.0 : Math.round(rand.nextDouble() * 100.0) / 100.0;
                fila.add(similitud);
            }
            matrizSimilitud.add(fila);
        }

        // Mostrar un resumen del contenido (por ejemplo, primeros 5 productos, restricciones y similitudes)
        System.out.println("Estantería (primeros 15 huecos con restricciones): " + estanteria.subList(0, 15));
        System.out.println("Productos (primeros 5 productos): " + productos.subList(0, 5));
        System.out.println("Matriz de Similitud (primeras 5 filas y columnas):");
        for (int i = 0; i < 5; i++) {
            System.out.println(matrizSimilitud.get(i).subList(0, 5));
        }

        HillClimbing algoritmo = new HillClimbing();

        ArrayList<Pair<Integer, Set<String>>> solucion = algoritmo.generateSolution(estanteria, productos, matrizSimilitud);

        System.out.println("Productos (primeros 5 productos): " + solucion.subList(0, 5));
    }
}
