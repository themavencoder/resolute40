package com.aloine.resolute40.panicalert.model;


import com.google.gson.annotations.SerializedName;

public class PanicData  {

    @SerializedName("auth")
    private Keys auth;

    @SerializedName("data")
    private PanicDetails data;

    @SerializedName("user_type")
    private String user_type;

    public PanicData() {


    }
    public PanicData(Keys auth, PanicDetails data, String user_type) {
        this.auth = auth;
        this.data = data;
        this.user_type = user_type;
    }

}
