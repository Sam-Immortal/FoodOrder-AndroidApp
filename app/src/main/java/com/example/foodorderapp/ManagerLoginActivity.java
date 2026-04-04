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

public class ManagerLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_login);

        // Make sure these IDs match your XML layout!
        EditText nameInput = findViewById(R.id.inputLoginName);
        EditText passwordInput = findViewById(R.id.inputLoginPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (name.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter your restaurant name and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // 1. Create the request with Name and Password
            LoginRequest request = new LoginRequest(name, password);

            // 2. Set up Retrofit call
            ManagerApiService apiService = RetrofitClient.getClient().create(ManagerApiService.class);
            Call<Manager> call = apiService.loginManager(request);

            // 3. Execute the call
            call.enqueue(new Callback<Manager>() {
                @Override
                public void onResponse(Call<Manager> call, Response<Manager> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(ManagerLoginActivity.this, "Welcome back, " + name + "!", Toast.LENGTH_SHORT).show();

                        // Go to Manager Dashboard
                        Intent intent = new Intent(ManagerLoginActivity.this, ManagerMainActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (response.code() == 401) {
                        Toast.makeText(ManagerLoginActivity.this, "Invalid restaurant name or password", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ManagerLoginActivity.this, "Server error: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Manager> call, Throwable t) {
                    Toast.makeText(ManagerLoginActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}