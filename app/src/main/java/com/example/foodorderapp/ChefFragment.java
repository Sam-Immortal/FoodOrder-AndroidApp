package com.example.foodorderapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ChefFragment extends Fragment {

    private RecyclerView kitchenRecyclerView;
    private FoodApi api;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this tab
        View view = inflater.inflate(R.layout.fragment_chef, container, false);

        kitchenRecyclerView = view.findViewById(R.id.kitchenRecyclerView);
        kitchenRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        api = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FoodApi.class);

        fetchOrders();

        return view;
    }

    private void fetchOrders() {
        api.getOrders().enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    KitchenAdapter adapter = new KitchenAdapter(response.body(), order -> {
                        updateStatusInDatabase(order.getId());
                    });

                    kitchenRecyclerView.setAdapter(adapter);

                } else {
                    Toast.makeText(requireContext(), "Failed to load orders.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Toast.makeText(requireContext(), "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateStatusInDatabase(Long orderId) {
        StatusUpdateRequest request = new StatusUpdateRequest("Ready");

        api.updateOrderStatus(orderId, request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(requireContext(), "Order marked as Ready!", Toast.LENGTH_SHORT).show();
                    fetchOrders(); // Refresh the list!
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(requireContext(), "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}