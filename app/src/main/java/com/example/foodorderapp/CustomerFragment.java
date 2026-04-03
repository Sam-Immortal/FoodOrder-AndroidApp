package com.example.foodorderapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomerFragment extends Fragment {

    private RecyclerView menuRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this tab
        View view = inflater.inflate(R.layout.fragment_customer, container, false);

        menuRecyclerView = view.findViewById(R.id.menuRecyclerView);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        FoodApi api = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FoodApi.class);

        // Fetch Menu
        api.getMenu().enqueue(new Callback<List<MenuItem>>() {
            @Override
            public void onResponse(Call<List<MenuItem>> call, Response<List<MenuItem>> response) {
                if(response.isSuccessful() && response.body() != null) {

                    Button checkoutBtn = view.findViewById(R.id.checkoutButton);

                    MenuAdapter adapter = new MenuAdapter(response.body(), item -> {
                        CartManager.getInstance().addItem(item);
                        int cartSize = CartManager.getInstance().getCartItems().size();
                        checkoutBtn.setText("Go to Checkout (" + cartSize + " items)");
                        Toast.makeText(requireContext(), item.getName() + " added to cart!", Toast.LENGTH_SHORT).show();
                    });

                    menuRecyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(requireContext(), "Failed to load menu.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MenuItem>> call, Throwable t) {
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Setup the Checkout Button to open the Checkout Activity
        Button checkoutBtn = view.findViewById(R.id.checkoutButton);
        checkoutBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), CheckoutActivity.class);
            startActivity(intent);
        });

        return view;
    }
}