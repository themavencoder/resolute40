package com.aloine.resolute40.auth.register.network;

import com.aloine.resolute40.auth.register.model.RegisterModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("mobile_register")
    Call<RegisterResponse> registerUser(@Body RegisterModel model);

}
