package com.aloine.resolute40.panicalert.model;

import com.google.gson.annotations.SerializedName;

public class PanicDetails {

    @SerializedName("username")
    private String username;

    @SerializedName("details")
    private String details;

    @SerializedName("panic_status")
    private String panic_status;

    @SerializedName("lat")
    private float lat;

    @SerializedName("lng")
    private float lng;


    public PanicDetails() {

    }

    public PanicDetails(String username, String details, String panic_status, float lat, float lng) {
        this.username = username;
        this.details =details;
        this.panic_status = panic_status;
        this.lat = lat;
        this.lng = lng;
    }
}
