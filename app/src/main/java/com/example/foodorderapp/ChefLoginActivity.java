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

public class ChefLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_login);

        EditText usernameInput = findViewById(R.id.inputChefUsername);
        EditText passwordInput = findViewById(R.id.inputChefPassword);
        Button btnLogin = findViewById(R.id.btnChefLogin);

        btnLogin.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter your Chef credentials", Toast.LENGTH_SHORT).show();
                return;
            }

            // 1. Create the login request using the name and password
            LoginRequest request = new LoginRequest(username, password);

            // 2. Set up the Retrofit network call
            ChefApiService apiService = RetrofitClient.getClient().create(ChefApiService.class);
            Call<Chef> call = apiService.loginChef(request);

            // 3. Execute the call asynchronously
            call.enqueue(new Callback<Chef>() {
                @Override
                public void onResponse(Call<Chef> call, Response<Chef> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Success! The database found a match.
                        Toast.makeText(ChefLoginActivity.this, "Welcome to the Kitchen, " + response.body().getName() + "!", Toast.LENGTH_SHORT).show();

                        // Route them to the Chef Dashboard
                        Intent intent = new Intent(ChefLoginActivity.this, ChefMainActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (response.code() == 401) {
                        // 401 Unauthorized: The credentials didn't match the database
                        Toast.makeText(ChefLoginActivity.this, "Invalid Chef username or password", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChefLoginActivity.this, "Server error: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Chef> call, Throwable t) {
                    Toast.makeText(ChefLoginActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}