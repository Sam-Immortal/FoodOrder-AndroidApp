package com.example.foodorderapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChefFragment extends Fragment {

    private RecyclerView kitchenRecyclerView;
    private FoodApi api;

    // --- Polling and Translation Variables ---
    private Map<Long, String> foodDictionary = new HashMap<>();
    private Handler pollingHandler = new Handler(Looper.getMainLooper());
    private Runnable pollingRunnable;
    private final int POLL_INTERVAL = 5000; // 5000 milliseconds = 5 seconds

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chef, container, false);

        kitchenRecyclerView = view.findViewById(R.id.kitchenRecyclerView);
        kitchenRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        api = RetrofitClient.getClient().create(FoodApi.class);

        // 1. Fetch the menu first to build the dictionary, THEN start polling orders
        fetchMenuToBuildDictionary();

        return view;
    }

    private void fetchMenuToBuildDictionary() {
        api.getMenu().enqueue(new Callback<List<MenuItem>>() {
            @Override
            public void onResponse(Call<List<MenuItem>> call, Response<List<MenuItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (MenuItem item : response.body()) {
                        // Save ID -> Name (e.g., 1 -> "Paneer Tikka")
                        foodDictionary.put(item.getId(), item.getName());
                    }
                }
                // Once dictionary is built, start the heartbeat loop!
                startPolling();
            }

            @Override
            public void onFailure(Call<List<MenuItem>> call, Throwable t) {
                Toast.makeText(requireContext(), "Failed to load menu dictionary.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startPolling() {
        pollingRunnable = new Runnable() {
            @Override
            public void run() {
                fetchOrders(); // Fetch the latest orders
                pollingHandler.postDelayed(this, POLL_INTERVAL); // Schedule the next run in 5 seconds
            }
        };
        pollingHandler.post(pollingRunnable); // Trigger the first run immediately
    }

    private void fetchOrders() {
        api.getOrders().enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    // Pass the dictionary into our updated Adapter!
                    KitchenAdapter adapter = new KitchenAdapter(response.body(), foodDictionary, order -> {
                        updateStatusInDatabase(order.getId());
                    });

                    kitchenRecyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                // Silently fail during polling so we don't spam the user with toasts
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
                    fetchOrders(); // Force an immediate refresh so the button disappears instantly
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(requireContext(), "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // --- Critical Battery Saving Step ---
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // If the chef closes the tab, STOP the 5-second loop!
        if (pollingHandler != null && pollingRunnable != null) {
            pollingHandler.removeCallbacks(pollingRunnable);
        }
    }
}
