package com.aloine.resolute40.auth.login.network;

import com.google.gson.annotations.SerializedName;

public class AuthKeys {

    @SerializedName("session_token")
    private String session_token;

    public String getSession_token() {
        return session_token;
    }
}
