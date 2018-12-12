package com.aloine.resolute40.auth.login.network;

import com.aloine.resolute40.auth.login.model.LoginModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {


    @POST("mobile_signin")
    Call<LoginResponse> startLogin(@Body LoginModel loginModel);

}
