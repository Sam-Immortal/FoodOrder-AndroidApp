package com.example.foodorderapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ChefLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_login);

        EditText inputId = findViewById(R.id.inputRestaurantId);
        EditText inputPassword = findViewById(R.id.inputPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView textRegister = findViewById(R.id.textRegister);

        btnLogin.setOnClickListener(v -> {
            String username = inputId.getText().toString();
            String password = inputPassword.getText().toString();

            if (username.equals("admin") && password.equals("1234")) {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();

                // ROUTE TO CHEF MAIN ACTIVITY INSTEAD OF MAIN ACTIVITY!
                Intent intent = new Intent(ChefLoginActivity.this, ChefMainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });

        textRegister.setOnClickListener(v -> {
            // Send them to the Registration Screen!
            Intent intent = new Intent(ChefLoginActivity.this, RestaurantRegisterActivity.class);
            startActivity(intent);
        });
    }
}