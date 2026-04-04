package com.example.foodorderapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerMainActivity extends AppCompatActivity {

    private ListView listMenu;
    private ListView listChefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);

        TabLayout tabLayout = findViewById(R.id.managerTabLayout);
        View layoutMenu = findViewById(R.id.layoutMenu);
        View layoutChef = findViewById(R.id.layoutChef);

        EditText foodNameInput = findViewById(R.id.inputFoodName);
        EditText foodPriceInput = findViewById(R.id.inputFoodPrice);
        Button btnAddMenu = findViewById(R.id.btnAddMenuItem);
        listMenu = findViewById(R.id.listMenu);

        EditText chefNameInput = findViewById(R.id.inputChefName);
        EditText chefPasswordInput = findViewById(R.id.inputChefPassword);
        Button btnAddChef = findViewById(R.id.btnAddChef);
        listChefs = findViewById(R.id.listChefs);

        Button btnLogout = findViewById(R.id.btnManagerLogout);

        // Load data immediately when dashboard opens
        loadMenuItems();
        loadChefs();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    layoutMenu.setVisibility(View.VISIBLE);
                    layoutChef.setVisibility(View.GONE);
                } else {
                    layoutMenu.setVisibility(View.GONE);
                    layoutChef.setVisibility(View.VISIBLE);
                }
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        // Add Menu Logic
        btnAddMenu.setOnClickListener(v -> {
            String name = foodNameInput.getText().toString().trim();
            String priceString = foodPriceInput.getText().toString().trim();

            if (name.isEmpty() || priceString.isEmpty()) {
                Toast.makeText(this, "Please enter name and price", Toast.LENGTH_SHORT).show();
                return;
            }

            double price = Double.parseDouble(priceString);
            MenuItem newItem = new MenuItem(name, "Delicious new item!", price, "https://placeholder.com", true);

            MenuApiService apiService = RetrofitClient.getClient().create(MenuApiService.class);
            apiService.createMenuItem(newItem).enqueue(new Callback<MenuItem>() {
                @Override
                public void onResponse(Call<MenuItem> call, Response<MenuItem> response) {
                    if (response.isSuccessful()) {
                        foodNameInput.setText("");
                        foodPriceInput.setText("");
                        loadMenuItems(); // REFRESH THE LIST!
                    }
                }
                @Override
                public void onFailure(Call<MenuItem> call, Throwable t) {
                    Toast.makeText(ManagerMainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Add Chef Logic
        btnAddChef.setOnClickListener(v -> {
            String name = chefNameInput.getText().toString().trim();
            String password = chefPasswordInput.getText().toString().trim();

            if (name.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter chef name and password", Toast.LENGTH_SHORT).show();
                return;
            }

            Chef newChef = new Chef(name, password);
            ChefApiService apiService = RetrofitClient.getClient().create(ChefApiService.class);
            apiService.addChef(newChef).enqueue(new Callback<Chef>() {
                @Override
                public void onResponse(Call<Chef> call, Response<Chef> response) {
                    if (response.isSuccessful()) {
                        chefNameInput.setText("");
                        chefPasswordInput.setText("");
                        loadChefs(); // REFRESH THE LIST!
                    }
                }
                @Override
                public void onFailure(Call<Chef> call, Throwable t) {
                    Toast.makeText(ManagerMainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(ManagerMainActivity.this, LandingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    // --- HELPER METHODS TO FETCH DATA ---

    private void loadMenuItems() {
        MenuApiService apiService = RetrofitClient.getClient().create(MenuApiService.class);
        apiService.getAllMenuItems().enqueue(new Callback<List<MenuItem>>() {
            @Override
            public void onResponse(Call<List<MenuItem>> call, Response<List<MenuItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> displayList = new ArrayList<>();
                    for (MenuItem item : response.body()) {
                        displayList.add(item.getName() + " - $" + item.getPrice());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ManagerMainActivity.this, android.R.layout.simple_list_item_1, displayList);
                    listMenu.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<MenuItem>> call, Throwable t) {}
        });
    }

    private void loadChefs() {
        ChefApiService apiService = RetrofitClient.getClient().create(ChefApiService.class);
        apiService.getAllChefs().enqueue(new Callback<List<Chef>>() {
            @Override
            public void onResponse(Call<List<Chef>> call, Response<List<Chef>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> displayList = new ArrayList<>();
                    for (Chef chef : response.body()) {
                        displayList.add("Chef ID: " + chef.getName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ManagerMainActivity.this, android.R.layout.simple_list_item_1, displayList);
                    listChefs.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<Chef>> call, Throwable t) {}
        });
    }
}