package com.example.foodorderapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class RestaurantPortalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_portal);

        Button btnLogin = findViewById(R.id.btnPortalLogin);
        Button btnRegister = findViewById(R.id.btnPortalRegister);

        // 3.1 Route to Login
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RestaurantPortalActivity.this, ManagerLoginActivity.class);
            startActivity(intent);
        });

        // 3.2 Route to Registration
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(RestaurantPortalActivity.this, RestaurantRegisterActivity.class);
            startActivity(intent);
        });
    }
}