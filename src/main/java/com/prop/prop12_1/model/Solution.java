package com.prop.prop12_1.model;

import org.apache.commons.lang3.tuple.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Solution {

    private String solutionName;
    private String idCatalog;
    private String idShelf;
    private String heuristic;
    private String algorithm;
    private Double mark;
    private List<Pair<Integer, Set<String>>> distribution;

    public Solution(String solutionName, String idCatalog, String idShelf, String heuristic, String algorthm, double mark, List<Pair<Integer,Set<String>>> distribution) {
        this.solutionName = solutionName;
        this.idCatalog = idCatalog;
        this.idShelf = idShelf;
        this.heuristic = heuristic;
        this.algorithm = algorthm;
        this.mark = mark;
        this.distribution = distribution;
    }

    public List<Pair<Integer,Set<String>>> getDistribution() {
        return distribution;
    }

    public double getMark() {
        return mark;
    }

    public String getIdShelf() {
        return idShelf;
    }

    public String getIdCatalog() {
        return idCatalog;
    }

    public String getSolutionName() {
        return solutionName;
    }


    public void setSolutionName(String solutionName) {
        this.solutionName = solutionName;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }


    public void setDistribution(List<Pair<Integer,Set<String>>> distribution) {
        this.distribution = distribution;
    }
    /*
    public ArrayList<String> changeDistribution(int idx1, int idx2) {
        String s1 = distribution.get(idx1);
        String s2 = distribution.get(idx2);
        distribution.set(idx1, s2);
        distribution.set(idx2, s1);
        return distribution;
    }
    */
}
