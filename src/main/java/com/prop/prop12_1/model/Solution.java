package com.prop.prop12_1.model;

import org.apache.commons.lang3.tuple.Pair;
import java.util.ArrayList;

public class Solution {

    private String solutionName;
    private Integer idCatalog;
    private Integer idShelf;
    private Double mark;
    private Boolean valid;
    private ArrayList<Pair<Integer,String>> distribution = new ArrayList<>();

    public Solution(String solutionName, int idCatalog, int idShelf, double mark, boolean valid, ArrayList<Pair<Integer,String>> distribution) {
        this.solutionName = solutionName;
        this.idCatalog = idCatalog;
        this.idShelf = idShelf;
        this.mark = mark;
        this.valid = valid;
        this.distribution = distribution;
    }

    public ArrayList<Pair<Integer,String>> getDistribution() {
        return distribution;
    }

    public double getMark() {
        return mark;
    }

    public int getIdShelf() {
        return idShelf;
    }

    public int getIdCatalog() {
        return idCatalog;
    }

    public String getSolutionName() {
        return solutionName;
    }

    public boolean isValid() {
        return valid;
    }

    public void setSolutionName(String solutionName) {
        this.solutionName = solutionName;
    }

    public void setIdCatalog(int idCatalog) {
        this.idCatalog = idCatalog;
    }

    public void setIdShelf(int idShelf) {
        this.idShelf = idShelf;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public void setDistribution(ArrayList<Pair<Integer,String>> distribution) {
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
