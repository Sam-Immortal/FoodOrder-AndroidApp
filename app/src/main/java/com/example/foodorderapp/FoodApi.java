package com.example.foodorderapp;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path; // Make sure this is imported!

public interface FoodApi {
    // In your FoodApi.java interface
    @GET("api/menu/all") // Add the /all here!
    Call<List<MenuItem>> getMenu();

    @POST("api/orders")
    Call<Void> placeOrder(@Body OrderRequest orderRequest);

    @GET("api/orders")
    Call<List<Order>> getOrders();

    // ADD THIS NEW PUT METHOD:
    @PUT("api/orders/{id}/status")
    Call<Void> updateOrderStatus(@Path("id") Long id, @Body StatusUpdateRequest request);
}