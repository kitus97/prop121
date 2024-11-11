package com.prop.prop12_1.controller;


import com.prop.prop12_1.algorithm.HillClimbing;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Set;


public class CtrlAlgorithm {

    public CtrlAlgorithm(){}


    public ArrayList<Pair<Integer, Set<String>>> getSolution(ArrayList<Set<String>> shelf, ArrayList<Pair<Integer, Set<String>>> products, int algorithm, boolean generatedSimilarity){
        ArrayList<ArrayList<Double>> similarityTable;

        if(generatedSimilarity){
            similarityTable = new CtrlProd().generateSimilarityTable();
        }
        else{
            similarityTable = new CtrlProd().getSimilarityTable();

        }

        if(algorithm == 0){
            ArrayList<Pair<Integer, Set<String>>> solution = new HillClimbing().generateSolution(shelf, products, similarityTable);
            return solution;
        }

        else if(algorithm == 1){
            ArrayList<Pair<Integer, Set<String>>> solution = new Backtracking().generateSolution(shelf, products, similarityTable);
            return solution;

        }

        return null;


    }



}
