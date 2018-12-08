package edu.cs.cs184.photoapp;

/**
 *   CustomFilter - used to store filter, matching features, and their certainties
 *
 *  Mitchell Lewis - 12/2/18

 */


import com.zomato.photofilters.imageprocessors.Filter;

import java.util.ArrayList;

public class CustomFilter {

    private Filter mFilter;
    private ArrayList<String> features;
    private ArrayList<Double> certs;
    private String filterName;

    public void CustomFilter(){

    }

    public void addFilter(String name){
        filterName = name;
        features = new ArrayList<>();
        certs = new ArrayList<>();
    }

    public void addFeature(String feature, Double cert){
        features.add(feature);
        certs.add(cert);
    }

    public Double getScore(){
        Double sum = 0.0;
        for(Double cert : certs){
            sum += cert;
        }
        return sum;
    }

    public String getFilterName(){
        return filterName;
    }

    public Object[] getFeatures(){
        return features.toArray();
    }

    public Object[] getCerts(){
        return certs.toArray();
    }
}
