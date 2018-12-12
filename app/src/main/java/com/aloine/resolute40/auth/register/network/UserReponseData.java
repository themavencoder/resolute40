package com.aloine.resolute40.auth.register.network;

import com.google.gson.annotations.SerializedName;

public class UserReponseData {

    @SerializedName("client_username")
    private String client_username;

    @SerializedName("client_token")
    private String client_token;

    public String getClient_token() {
        return client_token;
    }

    public String getClient_username() {
        return client_username;
    }
}
