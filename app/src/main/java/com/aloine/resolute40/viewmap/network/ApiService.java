package com.aloine.resolute40.viewmap.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ApiService {

    @GET("get_latlng/{username}")
    Call<ViewMapResponse> getLocation(@Path("username") String username);

}
