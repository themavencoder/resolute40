package com.aloine.resolute40.viewmap.network;

import com.google.gson.annotations.SerializedName;

public class ViewMapResponse {

    @SerializedName("response")
    private String response;

    @SerializedName("data")
    private Data data;


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }



}
