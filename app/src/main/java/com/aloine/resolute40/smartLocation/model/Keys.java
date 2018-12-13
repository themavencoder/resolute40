package com.aloine.resolute40.smartLocation.model;

import com.google.gson.annotations.SerializedName;

public class Keys {


    @SerializedName("client_token")
    private String client_token;

    @SerializedName("session_token")
    private String session_token;

    public Keys(String client_token, String session_token) {
        this.client_token =client_token;
        this.session_token = session_token;

    }
    public void setSession_token(String session_token) {
        this.session_token = session_token;
    }

    public void setClient_token(String client_token) {
        this.client_token = client_token;
    }
}
