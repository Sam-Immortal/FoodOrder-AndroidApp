package com.example.foodorderapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // Listen for Tab Clicks
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            // Route strictly to Customer features
            if (item.getItemId() == R.id.nav_customer) {
                selectedFragment = new CustomerFragment();
            } else if (item.getItemId() == R.id.nav_my_orders) {
                selectedFragment = new MyOrdersFragment();
            }

            // Swap the screen to the chosen tab
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });

        // Set the default tab to the Customer Menu when the app opens
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new CustomerFragment())
                    .commit();
        }
    }
}