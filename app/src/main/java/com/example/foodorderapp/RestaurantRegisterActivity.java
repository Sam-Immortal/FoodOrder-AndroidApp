package com.example.foodorderapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// Don't forget to import the Retrofit classes!
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_register);

        EditText nameInput = findViewById(R.id.regRestaurantName);
        EditText passInput = findViewById(R.id.regPassword);
        Button btnCreate = findViewById(R.id.btnCreateAccount);

        btnCreate.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String pass = passInput.getText().toString().trim();

            if (name.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // 1. Create the Manager object.
            // We pass "" (an empty string) for the email since we dropped it from the UI!
            Manager newManager = new Manager(name, pass);

            // 2. Prepare the network call
            ManagerApiService apiService = RetrofitClient.getClient().create(ManagerApiService.class);
            Call<Manager> call = apiService.registerManager(newManager);

            // 3. Execute the call asynchronously
            call.enqueue(new Callback<Manager>() {
                @Override
                public void onResponse(Call<Manager> call, Response<Manager> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(RestaurantRegisterActivity.this, "Restaurant Registered! Welcome " + name, Toast.LENGTH_LONG).show();

                        // Route the new manager to the Login screen so they can sign in with their new credentials
                        Intent intent = new Intent(RestaurantRegisterActivity.this, ManagerLoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RestaurantRegisterActivity.this, "Failed to register: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Manager> call, Throwable t) {
                    Toast.makeText(RestaurantRegisterActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}