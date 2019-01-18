package com.aloine.resolute40.auth.quickLogin.network;

import com.google.gson.annotations.SerializedName;

public class AuthKeys {

    @SerializedName("session_token")
    private String session_token;

    @SerializedName("client_token")
    private String client_token;

    public String getClient_token() {
        return client_token;
    }

    public String getSession_token() {
        return session_token;
    }
}
