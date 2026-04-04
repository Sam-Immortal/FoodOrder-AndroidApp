package com.example.foodorderapp;

import java.util.List; // Make sure to import List
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ChefApiService {
    @POST("api/chef/add")
    Call<Chef> addChef(@Body Chef newChef);

    @POST("api/chef/login")
    Call<Chef> loginChef(@Body LoginRequest loginRequest);

    // NEW: Fetch all chefs
    @GET("api/chef/all")
    Call<List<Chef>> getAllChefs();
}