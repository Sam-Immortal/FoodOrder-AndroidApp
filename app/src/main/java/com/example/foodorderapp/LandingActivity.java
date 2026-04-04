package com.example.foodorderapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        Button btnCustomer = findViewById(R.id.btnCustomer);
        Button btnChef = findViewById(R.id.btnChef);
        Button btnRestaurant = findViewById(R.id.btnRestaurant);

        // 1. Customer
        btnCustomer.setOnClickListener(v -> {
            Intent intent = new Intent(LandingActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // 2. Chef
        btnChef.setOnClickListener(v -> {
            Intent intent = new Intent(LandingActivity.this, ChefLoginActivity.class);
            startActivity(intent);
        });

        // 3. Restaurant -> Routes to the new Portal screen we are about to make
        btnRestaurant.setOnClickListener(v -> {
            Intent intent = new Intent(LandingActivity.this, RestaurantPortalActivity.class);
            startActivity(intent);
        });
    }
}