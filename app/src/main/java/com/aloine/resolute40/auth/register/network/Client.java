package com.aloine.resolute40.auth.register.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    public static final String BASE_URL = "http://resolute40.pythonanywhere.com/";

    //Create Logger
    private static HttpLoggingInterceptor logger = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    //Create OkHttpClient
    private static OkHttpClient.Builder okHttp = new OkHttpClient.Builder().addInterceptor(logger);


    public static Retrofit retrofit = null;


    public static Retrofit getClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttp.build())
                    .build();

        }
        return retrofit;
    }
}
