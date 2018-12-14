package com.aloine.resolute40.panicalert.network;

import com.aloine.resolute40.panicalert.model.PanicData;
import com.aloine.resolute40.panicalert.model.PanicResponse;
import com.aloine.resolute40.smartLocation.model.PostLocationData;
import com.aloine.resolute40.smartLocation.network.PostLocationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("create_panic")
    Call<PanicResponse> postLocation(@Body PanicData panicData);

}
