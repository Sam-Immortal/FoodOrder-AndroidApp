package com.example.foodorderapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ManagerApiService {

    // The Login endpoint
    @POST("api/manager/login")
    Call<Manager> loginManager(@Body LoginRequest loginRequest);

    // The Registration endpoint
    @POST("api/manager/register")
    Call<Manager> registerManager(@Body Manager newManager);
}