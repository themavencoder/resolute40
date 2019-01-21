package com.aloine.resolute40.viewmap.network;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Data {

    @SerializedName("bounds")
    private ArrayList<ArrayList<Float>> bounds;

    @SerializedName("start_latlng")
    private ArrayList<Float> start_latlng;

    @SerializedName("end_latlng")
    private ArrayList<Float> end_latlng;


    public ArrayList<ArrayList<Float>> getBounds() {
        return bounds;
    }

    public ArrayList<Float> getEnd_latlng() {
        return end_latlng;
    }

    public ArrayList<Float> getStart_latlng() {
        return start_latlng;
    }

    public void setEnd_latlng(ArrayList<Float> end_latlng) {
        this.end_latlng = end_latlng;
    }

    public void setStart_latlng(ArrayList<Float> start_latlng) {
        this.start_latlng = start_latlng;
    }

    public void setBounds(ArrayList<ArrayList<Float>> bounds) {
        this.bounds = bounds;
    }
}
