package com.aloine.resolute40.auth.quickLogin.network;

import com.aloine.resolute40.auth.quickLogin.model.LoginModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {


    @POST("auth/mobile_signin")
    Call<LoginResponse> startLogin(@Body LoginModel loginModel);

}
