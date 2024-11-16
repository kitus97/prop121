package com.prop.prop12_1.model;

import com.prop.prop12_1.controller.CtrlProd;
import com.prop.prop12_1.exceptions.InvalidProductRestrictionException;
import com.prop.prop12_1.exceptions.NotInterchangeableException;
import com.prop.prop12_1.exceptions.ProductNotFoundException;
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

    private Solution copy(){
        Solution s = new Solution(solutionName, idCatalog, idShelf, heuristic, algorithm, mark, new ArrayList<>());
        s.distribution = new ArrayList<>(distribution);
        return s;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Double getMark() {
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

    public String getHeuristic() {
        return heuristic;
    }
    public String getAlgorithm() {
        return algorithm;
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

    public Double checkMarkSwap(int idx1, int idx2){
        Solution s = copy();
        s.changeProducts(idx1, idx2);
        return s.mark;
    }

    public Double checkMarkDelete(int index){
        Solution s = copy();
        s.deleteProduct(index);
        return s.mark;
    }

    public Double checkMarkAdd(String product, int index){
        Solution s = copy();
        s.addProduct(product, index);
        return s.mark;
    }

    public void changeProducts(int idx1, int idx2) {
        if(idx1 >= distribution.size() || idx2 >= distribution.size()) throw new IndexOutOfBoundsException("Invalid index");

        Pair<Product, Set<String>> s1 = distribution.get(idx1);
        Pair<Product, Set<String>> s2 = distribution.get(idx2);

        if(s1.getRight().equals(s2.getRight())) {
            distribution.set(idx1, s2);
            distribution.set(idx2, s1);
            updateMark(getSimilarityTable());
        }
        else throw new NotInterchangeableException("The products selected can't be swapped");

    }

    public String deleteProduct(int index){
        if(index >= distribution.size()) throw new IndexOutOfBoundsException("Invalid index");
        Pair<Product, Set<String>> pair = distribution.get(index);
        distribution.set(index, Pair.of(null, pair.getRight()));
        updateMark(getSimilarityTable());
        return pair.getLeft().getName();
    }

    public void addProduct(String product, int index){
        if(index >= distribution.size()) throw new IndexOutOfBoundsException("Invalid index");
        Product p = ctrlProd.findProduct(product);
        if(p == null) throw new ProductNotFoundException("Product not found");
        Pair<Product, Set<String>> pair = distribution.get(index);
        if(p.getRestrictions().equals(pair.getRight())) {
            distribution.set(index, Pair.of(p, pair.getRight()));
            updateMark(getSimilarityTable());
        }
        else throw new InvalidProductRestrictionException("The product does not meet the required restrictions of the cell");
    }

    public void updateMark(List<List<Double>> similarityTable){
        mark = calculateHeuristic(similarityTable);
    }

    private List<List<Double>> getSimilarityTable(){
        if(heuristic.equals("Generated")) return ctrlProd.generateSimilarityTable();
        else return ctrlProd.getSimilarityTable();
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
        StringBuilder distributionString = new StringBuilder("[");
        for (Pair<Product, Set<String>> pair : distribution) {
            String productName = (pair.getLeft() != null) ? pair.getLeft().getName() : "null";
            String restrictions = (pair.getRight() != null) ? pair.getRight().toString() : "null";
            distributionString.append("(Product: ").append(productName)
                    .append(", Restrictions: ").append(restrictions).append("), ");
        }
        if (!distribution.isEmpty()) {
            distributionString.setLength(distributionString.length() - 2); // Remove last comma and space
        }
        distributionString.append("]");

        return "{" + solutionName + ", Catalog: " + idCatalog + ", Shelf: " + idShelf +
                ", Heuristic: " + heuristic + ", Algorithm: " + algorithm + ", Puntuation: "
                + mark + ", Distribution: " + distributionString + "}\n";
    }
}
