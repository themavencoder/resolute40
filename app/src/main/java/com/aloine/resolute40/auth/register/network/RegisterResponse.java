package com.aloine.resolute40.auth.register.network;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    @SerializedName("response")
    private String response;

    @SerializedName("auth_keys")
    private UserReponseData auth_keys;

    public String getResponse() {
        return response;
    }

    public UserReponseData getAuth_keys() {
        return auth_keys;
    }
}
