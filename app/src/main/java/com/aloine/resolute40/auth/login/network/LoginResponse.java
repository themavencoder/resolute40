package com.aloine.resolute40.auth.login.network;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("response")
    private String response;

    @SerializedName("auth_keys")
    private AuthKeys auth_keys;

    public String getResponse() {
        return response;
    }

    public AuthKeys getAuth_keys() {
        return auth_keys;
    }
}
