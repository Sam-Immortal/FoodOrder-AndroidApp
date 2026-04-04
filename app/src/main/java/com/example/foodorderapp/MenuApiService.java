package com.example.foodorderapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;

public interface MenuApiService {

    // This matches the @PostMapping in your Spring Boot controller
    @POST("api/menu")
    Call<MenuItem> createMenuItem(@Body MenuItem newMenuItem);

    // Add this inside MenuApiService.java
    @GET("api/menu/all") // Make sure this matches your Spring Boot URL!
    Call<List<MenuItem>> getAllMenuItems();

}