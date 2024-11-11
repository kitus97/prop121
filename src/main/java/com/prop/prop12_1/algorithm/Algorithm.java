package com.prop.prop12_1.algorithm;

import com.prop.prop12_1.model.Characteristics;
import com.prop.prop12_1.model.Product;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Set;

public interface Algorithm {

    ArrayList<Pair<Integer, Set<String>>> generateSolution(ArrayList<Set<String>> shelf, ArrayList<Pair<Integer, Set<String>>> products,
                                        ArrayList<ArrayList<Double>> similarityTable);
}
