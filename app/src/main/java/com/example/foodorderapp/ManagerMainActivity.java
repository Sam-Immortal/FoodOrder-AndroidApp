package com.example.foodorderapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ManagerMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);

        EditText foodNameInput = findViewById(R.id.inputFoodName);
        EditText foodPriceInput = findViewById(R.id.inputFoodPrice);
        Button btnAddMenu = findViewById(R.id.btnAddMenuItem);
        Button btnLogout = findViewById(R.id.btnManagerLogout);

        // Add Menu Item Logic
        btnAddMenu.setOnClickListener(v -> {
            String name = foodNameInput.getText().toString();
            String price = foodPriceInput.getText().toString();

            if (name.isEmpty() || price.isEmpty()) {
                Toast.makeText(this, "Please enter name and price", Toast.LENGTH_SHORT).show();
                return;
            }

            // We will wire this to Spring Boot next!
            Toast.makeText(this, name + " added to live menu! (API pending)", Toast.LENGTH_SHORT).show();

            // Clear the boxes
            foodNameInput.setText("");
            foodPriceInput.setText("");
        });

        // Logout Logic
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(ManagerMainActivity.this, LandingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}