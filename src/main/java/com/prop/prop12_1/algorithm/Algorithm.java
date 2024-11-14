package com.prop.prop12_1.algorithm;

import com.prop.prop12_1.model.Characteristics;
import com.prop.prop12_1.model.Product;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface Algorithm {

    Pair<Double, List<Pair<Integer, Set<String>>>> generateSolution(List<Set<String>> shelf, List<Pair<Integer, Set<String>>> products,
                                                      List<List<Double>> similarityTable);
}
