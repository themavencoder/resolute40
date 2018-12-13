package com.aloine.resolute40.viewmap.network;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ViewMapResponse {

    @SerializedName("response")
    private String response;

    @SerializedName("data")
    private ArrayList<ArrayList<Float>> data;


    public ArrayList<ArrayList<Float>> getData() {
        return data;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setData(ArrayList<ArrayList<Float>> data) {
        this.data = data;
    }
}
