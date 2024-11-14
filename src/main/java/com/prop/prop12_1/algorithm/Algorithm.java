package com.prop.prop12_1.algorithm;

import com.prop.prop12_1.model.Characteristics;
import com.prop.prop12_1.model.Product;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface Algorithm {

    ArrayList<Pair<Integer, List<String>>> generateSolution(ArrayList<List<String>> shelf, ArrayList<Pair<Integer, List<String>>> products,
                                        ArrayList<ArrayList<Double>> similarityTable);
}
