package com.example.foodorderapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private List<MenuItem> menuList;

    // This constructor takes the data from MainActivity
    public MenuAdapter(List<MenuItem> menuList) {
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This inflates the XML card design we made earlier
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_row, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        // This injects the exact food name and price into a specific card
        MenuItem item = menuList.get(position);
        holder.foodName.setText(item.getName());
        holder.foodPrice.setText("$" + item.getPrice());
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    // This nested class holds the UI elements for a single card
    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView foodName;
        TextView foodPrice;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.foodNameText);
            foodPrice = itemView.findViewById(R.id.foodPriceText);
        }
    }
}