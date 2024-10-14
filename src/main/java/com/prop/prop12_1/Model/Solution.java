package com.prop.prop12_1.Model;

import java.util.ArrayList;

public class Solution {

    String solutionName;
    int idCatalog;
    int idShelf;
    double mark;
    boolean valid;
    ArrayList<String> distribution = new ArrayList<>();

    public Solution(String solutionName, int idCatalog, int idShelf, double mark, boolean valid, ArrayList<String> distribution) {
        this.solutionName = solutionName;
        this.idCatalog = idCatalog;
        this.idShelf = idShelf;
        this.mark = mark;
        this.valid = valid;
        this.distribution = distribution;
    }

    public ArrayList<String> getDistribution() {
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

    public void setDistribution(ArrayList<String> distribution) {
        this.distribution = distribution;
    }

    public void getSolution() {
        System.out.println("Name: " + solutionName +"\n");
        System.out.println("Id catalog: " + idCatalog + "\n");
        System.out.println("Id shelf: " + idShelf + "\n");
        System.out.println("Mark: " + mark + "\n");
        System.out.println("Valid: " + valid + "\n");
    }

    public void showSolution() {
        getSolution();
        for (int i = 0 ; i < distribution.size() ; i++) {
            System.out.println("distribution:\n");
            System.out.println("Space " + i + ": " +  distribution.get(i)+ "\n");
        }
    }

    public ArrayList<String> changeDistribution(int idx1, int idx2) {
        String s1 = distribution.get(idx1);
        String s2 = distribution.get(idx2);
        distribution.set(idx1, s2);
        distribution.set(idx2, s1);
        return distribution;
    }
}
