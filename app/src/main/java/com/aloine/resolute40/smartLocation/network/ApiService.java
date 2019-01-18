package com.aloine.resolute40.smartLocation.network;

import com.aloine.resolute40.smartLocation.model.PostLocationData;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("post_latlng")
    Call<PostLocationResponse> postLocation(@Body PostLocationData postLocationData);
}
