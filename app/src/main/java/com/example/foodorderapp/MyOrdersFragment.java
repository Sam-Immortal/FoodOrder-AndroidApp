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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyOrdersFragment extends Fragment {

    private RecyclerView myOrdersRecyclerView;
    private FoodApi api;
    private Map<Long, String> foodDictionary = new HashMap<>();

    private Handler pollingHandler = new Handler(Looper.getMainLooper());
    private Runnable pollingRunnable;
    private final int POLL_INTERVAL = 5000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);

        myOrdersRecyclerView = view.findViewById(R.id.myOrdersRecyclerView);
        myOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        api = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FoodApi.class);

        fetchMenuToBuildDictionary();

        return view;
    }

    private void fetchMenuToBuildDictionary() {
        api.getMenu().enqueue(new Callback<List<MenuItem>>() {
            @Override
            public void onResponse(Call<List<MenuItem>> call, Response<List<MenuItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (MenuItem item : response.body()) {
                        foodDictionary.put(item.getId(), item.getName());
                    }
                }
                startPolling();
            }
            @Override
            public void onFailure(Call<List<MenuItem>> call, Throwable t) {
                Toast.makeText(requireContext(), "Failed to load menu.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startPolling() {
        pollingRunnable = new Runnable() {
            @Override
            public void run() {
                fetchOrders();
                pollingHandler.postDelayed(this, POLL_INTERVAL);
            }
        };
        pollingHandler.post(pollingRunnable);
    }

    private void fetchOrders() {
        api.getOrders().enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MyOrdersAdapter adapter = new MyOrdersAdapter(response.body(), foodDictionary);
                    myOrdersRecyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {}
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (pollingHandler != null && pollingRunnable != null) {
            pollingHandler.removeCallbacks(pollingRunnable);
        }
    }
}