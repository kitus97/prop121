package com.prop.prop12_1.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Shelf{

    private String name;
    private ArrayList<Set<String>> distribution;


    public Shelf(String n, int t) {
        name = n;
        distribution = new ArrayList<Set<String>>();
        for (int i = 0; i < t; ++i) {
            distribution.add(new HashSet<String>());
        }

    }

    public String getName() {
        return name;
    }

    public ArrayList<Set<String>> getDistribution() {
        return distribution;
    }

    /*Pre: restriction es el nombre de una caracteristica valida y t <= distribution.size()
     */
    public void setRestriction(String restriction, int t){
        if(distribution.size() <= t) throw new IndexOutOfBoundsException("The index is out of range");
        else distribution.get(t).add(restriction);
    }

    public void deleteRestrictions(int t){
        if(distribution.size() <= t) throw new IndexOutOfBoundsException("The index is out of range");
        else distribution.get(t).clear();
    }


    public void resizeShelf(int t) {
        if (t < 0) throw new IndexOutOfBoundsException("Invalid size");

        else {
            if (distribution.size() < t) {
                for (int i = distribution.size(); i < t; ++i) {
                    distribution.add(new HashSet<String>());
                }
            } else {
                for (int i = distribution.size() - 1; i >= t; --i) {
                    distribution.remove(i);
                }

            }

        }
    }


}
