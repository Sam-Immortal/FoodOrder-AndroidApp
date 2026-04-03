package com.example.foodorderapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MenuApiService {

    // This matches the @PostMapping in your Spring Boot controller
    @POST("api/menu")
    Call<MenuItem> createMenuItem(@Body MenuItem newMenuItem);

}