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

        // Customer goes straight to the Main App
        btnCustomer.setOnClickListener(v -> {
            Intent intent = new Intent(LandingActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Chef goes to the Login Screen
        btnChef.setOnClickListener(v -> {
            Intent intent = new Intent(LandingActivity.this, ChefLoginActivity.class);
            startActivity(intent);
        });
    }
}