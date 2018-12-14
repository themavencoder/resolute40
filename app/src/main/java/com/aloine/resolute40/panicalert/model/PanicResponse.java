package com.aloine.resolute40.panicalert.model;

import com.google.gson.annotations.SerializedName;

public class PanicResponse {


    @SerializedName("response")
    private String response;

    @SerializedName("message")
    private String message;

    public String getResponse() {
        return response;
    }

    public String getMessage() {
        return message;
    }
}
