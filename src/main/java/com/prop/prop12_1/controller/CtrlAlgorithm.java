package com.prop.prop12_1.controller;


import com.prop.prop12_1.algorithm.BackTracking;
import com.prop.prop12_1.algorithm.HillClimbing;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class CtrlAlgorithm {

    public CtrlAlgorithm(){}


    public Pair<Double, List<Pair<Integer, Set<String>>>> getSolution(List<Set<String>> shelf, List<Pair<Integer, Set<String>>> products, int algorithm, boolean generatedSimilarity){
        List<List<Double>> similarityTable;

        if(generatedSimilarity){
            similarityTable = new CtrlProd().generateSimilarityTable();
        }
        else{
            similarityTable = new CtrlProd().getSimilarityTable();

        }

        if(algorithm == 0){
            Pair<Double, List<Pair<Integer, Set<String>>>> solution = new HillClimbing().generateSolution(shelf, products, similarityTable);
            return solution;
        }

        else if(algorithm == 1){
            Pair<Double, List<Pair<Integer, Set<String>>>> solution = new BackTracking().generateSolution(shelf, products, similarityTable);
            return solution;

        }

        return null;


    }



}
