package com.prop.prop12_1.algorithm;

import com.prop.prop12_1.model.Shelf;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class Test {

    public static void main(String[] args) {

        Random rand = new Random();

        // Crear la lista de 100 productos, cada uno con un ID y características
        List<Pair<Integer, Set<String>>> productos = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Set<String> caracteristicas = new HashSet<>();
            if (i < 100) { // Solo los primeros 15 huecos tienen restricciones
                if (rand.nextBoolean()) caracteristicas.add("congelado");
                if (rand.nextBoolean()) caracteristicas.add("fragil");
                if (rand.nextBoolean()) caracteristicas.add("c9");
                if (rand.nextBoolean()) caracteristicas.add("c8");
                if (rand.nextBoolean()) caracteristicas.add("c7");
                if (rand.nextBoolean()) caracteristicas.add("c6");
                if (rand.nextBoolean()) caracteristicas.add("c5");
                if (rand.nextBoolean()) caracteristicas.add("c4");
                if (rand.nextBoolean()) caracteristicas.add("c3");
                if (rand.nextBoolean()) caracteristicas.add("c2");
                if (rand.nextBoolean()) caracteristicas.add("c1");
            }
            productos.add(Pair.of(i, caracteristicas));
        }

        // Crear la matriz de similitud (100x100) con valores aleatorios entre 0 y 1, redondeados a dos decimales
        List<List<Double>> matrizSimilitud = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            ArrayList<Double> fila = new ArrayList<>();
            for (int j = 0; j < 30; j++) {
                double similitud = i == j ? 1.0 : Math.round(rand.nextDouble() * 100.0) / 100.0;
                fila.add(similitud);
            }
            matrizSimilitud.add(fila);
        }

        // Mostrar un resumen del contenido (por ejemplo, primeros 5 productos, restricciones y similitudes)
//        System.out.println("Estantería (primeros 15 huecos con restricciones): " + estanteria.subList(0, 15));
//        System.out.println("Productos (primeros 5 productos): " + productos.subList(0, 5));
//        System.out.println("Matriz de Similitud (primeras 5 filas y columnas):");
//        for (int i = 0; i < 5; i++) {
//            System.out.println(matrizSimilitud.get(i).subList(0, 5));
//        }

        List<Set<String>> shelf = new ArrayList<>();

        HillClimbing algoritmo = new HillClimbing();

//        Pair<Double, List<Pair<Integer, Set<String>>>> solucion = algoritmo.generateSolution(estanteria, productos, matrizSimilitud);

//        System.out.println("Productos (primeros 5 productos): " + solucion.getRight().subList(0, 5));
    }
}
