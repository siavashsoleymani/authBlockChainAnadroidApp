package com.example.qrcodereader.model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider {
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.1.7:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public static UidApi getUidApi() {
        return retrofit.create(UidApi.class);
    }
}
