package com.example.foodorderapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KitchenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);

        TextView ordersText = findViewById(R.id.kitchenOrdersText);
        ordersText.setText("Loading orders from Docker...");

        // Setup Retrofit
        FoodApi api = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FoodApi.class);

        // Fetch Orders
        api.getOrders().enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    StringBuilder tickets = new StringBuilder();
                    for (Order order : response.body()) {
                        tickets.append("Order #").append(order.getId())
                                .append("\nItem ID: ").append(order.getMenuItemId())
                                .append("\nStatus: ").append(order.getStatus())
                                .append("\n-------------------\n");
                    }

                    if (tickets.length() == 0) {
                        ordersText.setText("No pending orders!");
                    } else {
                        ordersText.setText(tickets.toString());
                    }

                } else {
                    ordersText.setText("Failed to load orders.");
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                ordersText.setText("Network error: " + t.getMessage());
            }
        });
    }
}