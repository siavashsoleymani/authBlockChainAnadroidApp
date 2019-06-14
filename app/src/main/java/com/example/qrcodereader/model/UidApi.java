package com.example.qrcodereader.model;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UidApi {
    @POST("users")
    Call<User> registerUser(@Body User user);

    @POST("users/login")
    Call<Object> loginUser(@Body LidDTO lidDTO);
}
