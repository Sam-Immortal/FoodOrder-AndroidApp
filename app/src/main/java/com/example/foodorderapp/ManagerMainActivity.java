package com.example.foodorderapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);

        EditText foodNameInput = findViewById(R.id.inputFoodName);
        EditText foodPriceInput = findViewById(R.id.inputFoodPrice);

        Button btnAddMenu = findViewById(R.id.btnAddMenuItem);
        Button btnLogout = findViewById(R.id.btnManagerLogout);

        btnAddMenu.setOnClickListener(v -> {
            String name = foodNameInput.getText().toString().trim();
            String priceString = foodPriceInput.getText().toString().trim();

            // Since we don't have the text boxes for these yet, we will just use placeholders!
            String description = "Delicious new item!";
            String imageUrl = "https://placeholder-image-url.com";

            if (name.isEmpty() || priceString.isEmpty()) {
                Toast.makeText(ManagerMainActivity.this, "Please enter name and price", Toast.LENGTH_SHORT).show();
                return;
            }

            double price = Double.parseDouble(priceString);

            // 1. Create the MenuItem object
            MenuItem newItem = new MenuItem(name, description, price, imageUrl, true);

            // 2. Prepare the Retrofit network call
            MenuApiService apiService = RetrofitClient.getClient().create(MenuApiService.class);
            Call<MenuItem> call = apiService.createMenuItem(newItem);

            // 3. Execute the call asynchronously
            call.enqueue(new Callback<MenuItem>() {
                @Override
                public void onResponse(Call<MenuItem> call, Response<MenuItem> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ManagerMainActivity.this, name + " published to menu successfully!", Toast.LENGTH_SHORT).show();
                        foodNameInput.setText("");
                        foodPriceInput.setText("");
                    } else {
                        Toast.makeText(ManagerMainActivity.this, "Failed to publish: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MenuItem> call, Throwable t) {
                    Toast.makeText(ManagerMainActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(ManagerMainActivity.this, LandingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}