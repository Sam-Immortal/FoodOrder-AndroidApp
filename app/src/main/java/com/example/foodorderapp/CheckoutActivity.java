package com.example.foodorderapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        TextView summaryText = findViewById(R.id.orderSummaryText);
        EditText tableInput = findViewById(R.id.tableNumberInput);
        Button submitBtn = findViewById(R.id.submitOrderBtn);

        // 1. Get the items from our Singleton Cart
        List<MenuItem> cart = CartManager.getInstance().getCartItems();

        // 2. Display a text summary of what they are ordering
        StringBuilder summary = new StringBuilder("You are ordering:\n");
        for (MenuItem item : cart) {
            summary.append("- ").append(item.getName()).append("\n");
        }
        summaryText.setText(summary.toString());

        // 3. Setup the Button Click
        submitBtn.setOnClickListener(v -> {
            String tableString = tableInput.getText().toString();

            if (tableString.isEmpty() || cart.isEmpty()) {
                Toast.makeText(this, "Please enter a table number and add items!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Convert the typed text into a real Integer
            Integer tableNum = Integer.parseInt(tableString);

            FoodApi api = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(FoodApi.class);

            for (MenuItem item : cart) {
                // WE NOW PASS BOTH THE FOOD ID AND THE TABLE NUMBER!
                OrderRequest request = new OrderRequest(item.getId(), tableNum);

                api.placeOrder(request).enqueue(new Callback<Void>() {
                    // ... keep your existing onResponse and onFailure methods exactly the same ...
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(CheckoutActivity.this, "Order sent to kitchen!", Toast.LENGTH_SHORT).show();

                            // Clear the cart and close this screen to go back to the menu
                            CartManager.getInstance().clearCart();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(CheckoutActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}