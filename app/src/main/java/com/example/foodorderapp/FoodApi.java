package com.example.foodorderapp;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FoodApi {
    @GET("api/menu")
    Call<List<MenuItem>> getMenu();
}