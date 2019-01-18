package com.aloine.resolute40.auth.quickLogin.network;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("response")
    private String response;

    @SerializedName("user_type")
    private String user_type;

    @SerializedName("auth_keys")
    private AuthKeys auth_keys;


    public String getUser_type() {
        return user_type;
    }

    public String getResponse() {
        return response;
    }

    public AuthKeys getAuth_keys() {
        return auth_keys;
    }
}
