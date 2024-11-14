package com.prop.prop12_1.algorithm;

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class TestBacktracking {

    public static void main(String[] args) {

        Random rand = new Random();

        // Crear la estantería con 100 huecos, de los cuales 15 tienen restricciones
        List<Set<String>> estanteria = new ArrayList<>();
        for (int i = 0; i < 15; i++) {

            Set<String> restricciones = new HashSet<>();

            if (i < 15) { // Solo los primeros 15 huecos tienen restricciones
                if (rand.nextBoolean()) restricciones.add("congelado");
                if (rand.nextBoolean()) restricciones.add("fragil");
            }




            estanteria.add(restricciones);
        }

        // Crear la lista de 100 productos, cada uno con un ID y características
        List<Pair<Integer, Set<String>>> productos = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Set<String> caracteristicas = new HashSet<>();

            if (i < 15) { // Solo los primeros 15 huecos tienen restricciones
                if (rand.nextBoolean()) caracteristicas.add("congelado");
                if (rand.nextBoolean()) caracteristicas.add("fragil");
            }




            productos.add(Pair.of(i, caracteristicas));
        }

        // Crear la matriz de similitud (100x100) con valores aleatorios entre 0 y 1, redondeados a dos decimales
        List<List<Double>> matrizSimilitud = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            List<Double> fila = new ArrayList<>();
            for (int j = 0; j < 15; j++) {
                double similitud = i == j ? 1.0 : Math.round(rand.nextDouble() * 100.0) / 100.0;
                fila.add(similitud);
            }
            matrizSimilitud.add(fila);
        }

        // Mostrar un resumen del contenido (por ejemplo, primeros 5 productos, restricciones y similitudes)
        System.out.println("Estantería (primeros 15 huecos con restricciones): " + estanteria.subList(0, 15));
        System.out.println("Productos (primeros 5 productos): " + productos.subList(0, 15));
        System.out.println("Matriz de Similitud (primeras 5 filas y columnas):");
        for (int i = 0; i < 15; i++) {
            System.out.println(matrizSimilitud.get(i).subList(0, 15));
        }

        BackTracking algoritmo = new BackTracking();

        List<Pair<Integer, Set<String>>> solucion = algoritmo.generateSolution(estanteria, productos, matrizSimilitud);

        System.out.println("Productos (primeros 5 productos): " + solucion.subList(0, 15));
    }
}
