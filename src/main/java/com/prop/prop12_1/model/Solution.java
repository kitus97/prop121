package com.prop.prop12_1.model;

import com.prop.prop12_1.controller.CtrlProd;
import org.apache.commons.lang3.tuple.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Solution {

    private static CtrlProd ctrlProd = new CtrlProd();

    private String solutionName;
    private String idCatalog;
    private String idShelf;
    private String heuristic;
    private String algorithm;
    private Double mark;
    private Boolean valid;
    private List<Pair<Product, Set<String>>> distribution;

    public Solution(String solutionName, String idCatalog, String idShelf, String heuristic, String algorthm, double mark, List<Pair<Integer,Set<String>>> distribution) {
        this.solutionName = solutionName;
        this.idCatalog = idCatalog;
        this.idShelf = idShelf;
        this.heuristic = heuristic;
        this.algorithm = algorthm;
        this.mark = mark;
        this.valid = true;
        List<Pair<Product, Set<String>>> products = new ArrayList<>();

        for(int i = 0; i < distribution.size(); i++) {
            Pair<Integer, Set<String>> pair = distribution.get(i);
            if(pair.getLeft() == null) products.add(Pair.of(null, pair.getRight()));
            else products.add(Pair.of(ctrlProd.findProduct(ctrlProd.getProductName(pair.getLeft())), pair.getRight()));

        }

        this.distribution = products;
    }

    public List<Pair<Product,Set<String>>> getDistribution() {
        return distribution;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
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


    public void setDistribution(List<Pair<Product ,Set<String>>> distribution) {
        this.distribution = distribution;
    }

    public void delete(){
        this.solutionName = null;
        this.idCatalog = null;
        this.idShelf = null;
        this.heuristic = null;
        this.algorithm = null;
        this.mark = null;
        this.valid = null;
        this.distribution = null;
    }

    public Boolean deleted(){
        return this.solutionName == null;
    }

    public void changeDistribution(int idx1, int idx2) {
        Pair<Product, Set<String>> s1 = distribution.get(idx1);
        Pair<Product, Set<String>> s2 = distribution.get(idx2);
        distribution.set(idx1, s2);
        distribution.set(idx2, s1);
    }

    public void updateMark(){
        if(heuristic == "Generated"){
            List<List<Double>> similaritytable = ctrlProd.generateSimilarityTable();
            mark = calculateHeuristic(similaritytable);
        }
        else{
            mark = calculateHeuristic(ctrlProd.getSimilarityTable());

        }
    }

    private Double calculateHeuristic(List<List<Double>> similarityTable) {
        double totalSimilarity = 0.0;
        for (int i = 0; i < distribution.size() - 1; i++) {
            if (distribution.get(i).getLeft() == null || distribution.get(i+1).getLeft() == null) {
                continue;
            }
            int actualProduct = distribution.get(i).getLeft().getId();
            int nextProduct = distribution.get(i + 1).getLeft().getId();
            totalSimilarity += similarityTable.get(actualProduct).get(nextProduct);
        }
        if (distribution.getLast().getLeft() != null && distribution.getFirst().getLeft() != null) {
            int actualProduct = distribution.getLast().getLeft().getId();
            int nextProduct = distribution.getFirst().getLeft().getId();
            totalSimilarity += similarityTable.get(actualProduct).get(nextProduct);
        }
        return Math.round(totalSimilarity * 1e5) / 1e5;
    }

    @Override
    public String toString() {
        return "{" + solutionName + ", Catalog: " + idCatalog + ", Shelf: " + idShelf +
                ", Heuristic: " + heuristic + ", Algorithm: " + algorithm + ", Puntuation: "
        + mark + "}\n";
    }

    public String toString1() {
        return "{" + solutionName + ", Catalog: " + idCatalog + ", Shelf: " + idShelf +
                ", Heuristic: " + heuristic + ", Algorithm: " + algorithm + ", Puntuation: "
                + mark + distribution.toString() + "}\n";
    }
}
