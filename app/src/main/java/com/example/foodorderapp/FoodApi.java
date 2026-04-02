package com.example.foodorderapp;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FoodApi {
    @GET("api/menu")
    Call<List<MenuItem>> getMenu();

    @POST("api/orders")
    Call<Void> placeOrder(@Body OrderRequest orderRequest);

    // ADD THIS NEW KITCHEN METHOD:
    @GET("api/orders")
    Call<List<Order>> getOrders();
}