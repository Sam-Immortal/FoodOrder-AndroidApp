package com.example.foodorderapp;

import android.os.Bundle;
import android.widget.Button; // <--- Added this import
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private RecyclerView menuRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect the Orange Button to the new Checkout Screen
        Button checkoutBtn = findViewById(R.id.checkoutButton);
        checkoutBtn.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(MainActivity.this, CheckoutActivity.class);
            startActivity(intent);
        });

        // Connect the Chef Button to the Kitchen Screen
        Button chefBtn = findViewById(R.id.chefModeButton);
        chefBtn.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(MainActivity.this, KitchenActivity.class);
            startActivity(intent);
        });

        // 1. Setup the RecyclerView to scroll vertically
        menuRecyclerView = findViewById(R.id.menuRecyclerView);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Setup Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FoodApi api = retrofit.create(FoodApi.class);

        // 2. Make the Network Call
        api.getMenu().enqueue(new Callback<List<MenuItem>>() {
            @Override
            public void onResponse(Call<List<MenuItem>> call, Response<List<MenuItem>> response) {
                if(response.isSuccessful() && response.body() != null) {

                    // Grab the new orange checkout button from the screen
                    Button checkoutBtn = findViewById(R.id.checkoutButton);

                    // 3. Hand the data to the Adapter, and listen for button clicks!
                    MenuAdapter adapter = new MenuAdapter(response.body(), new MenuAdapter.OnOrderClickListener() {
                        @Override
                        public void onOrderClick(MenuItem item) {

                            // --- CHANGED THIS SECTION ---
                            // Add the tapped item to our Singleton Cart Memory
                            CartManager.getInstance().addItem(item);

                            // Update the orange button text to show how many items are in the cart
                            int cartSize = CartManager.getInstance().getCartItems().size();
                            checkoutBtn.setText("Go to Checkout (" + cartSize + " items)");

                            // Show a quick popup confirming it was added
                            Toast.makeText(MainActivity.this, item.getName() + " added to cart!", Toast.LENGTH_SHORT).show();
                            // ----------------------------

                        }
                    });

                    menuRecyclerView.setAdapter(adapter);

                } else {
                    // Show a pop-up toast if the server returns an empty or bad response
                    Toast.makeText(MainActivity.this, "Failed to load menu.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MenuItem>> call, Throwable t) {
                // Show a pop-up toast if the emulator can't reach Docker
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}