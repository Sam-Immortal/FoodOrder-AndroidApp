package com.example.foodorderapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private List<MenuItem> menuList;
    private OnOrderClickListener listener;

    // We created a listener to pass the click event back to the main screen
    public interface OnOrderClickListener {
        void onOrderClick(MenuItem item);
    }

    public MenuAdapter(List<MenuItem> menuList, OnOrderClickListener listener) {
        this.menuList = menuList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_row, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuItem item = menuList.get(position);
        holder.foodName.setText(item.getName());
        holder.foodPrice.setText("$" + item.getPrice());

        // When the user clicks the button, tell the main screen which item was clicked
        holder.orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onOrderClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView foodName;
        TextView foodPrice;
        Button orderButton; // Added the button here

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.foodNameText);
            foodPrice = itemView.findViewById(R.id.foodPriceText);
            orderButton = itemView.findViewById(R.id.orderButton); // Found the button here
        }
    }
}