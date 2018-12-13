package com.aloine.resolute40.smartLocation.network;

import com.google.gson.annotations.SerializedName;

public class PostLocationResponse {

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
