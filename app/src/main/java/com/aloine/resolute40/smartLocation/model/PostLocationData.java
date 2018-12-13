package com.aloine.resolute40.smartLocation.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PostLocationData {
    @SerializedName("auth")
    private Keys auth;
    @SerializedName("username")
    private String username;

    @SerializedName("data")
    private ArrayList<ArrayList<Float>> data;

    public PostLocationData(Keys auth, String username, ArrayList<ArrayList<Float>> data) {
        this.auth = auth;
        this.username = username;
        this.data = data;

    }


    public Keys getAuth() {
        return auth;
    }

    public void setAuth(Keys auth) {
        this.auth = auth;
    }


    public void setData(ArrayList<ArrayList<Float>> data) {
        this.data = data;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<ArrayList<Float>> getData() {
        return data;
    }

    public String getUsername() {
        return username;
    }
}
