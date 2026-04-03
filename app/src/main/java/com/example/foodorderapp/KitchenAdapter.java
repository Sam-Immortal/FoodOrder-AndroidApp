package com.example.foodorderapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Map; // <-- New Import

public class KitchenAdapter extends RecyclerView.Adapter<KitchenAdapter.KitchenViewHolder> {

    private List<Order> orderList;
    private Map<Long, String> foodDictionary; // <-- The Translator Map
    private OnOrderReadyListener listener;

    public interface OnOrderReadyListener {
        void onOrderReadyClick(Order order);
    }

    // Constructor now requires the dictionary!
    public KitchenAdapter(List<Order> orderList, Map<Long, String> foodDictionary, OnOrderReadyListener listener) {
        this.orderList = orderList;
        this.foodDictionary = foodDictionary;
        this.listener = listener;
    }

    @NonNull
    @Override
    public KitchenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kitchen_order_card, parent, false);
        return new KitchenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KitchenViewHolder holder, int position) {
        Order order = orderList.get(position);

        holder.orderIdText.setText("Order #" + order.getId());

        // --- NEW: Translate ID to actual Food Name! ---
        String foodName = "Unknown Item";
        if (order.getMenuItemId() != null && foodDictionary.containsKey(order.getMenuItemId())) {
            foodName = foodDictionary.get(order.getMenuItemId());
        }
        // Change the text from "Item ID: 1" to the actual name!
        holder.itemIdText.setText(foodName);
        // ----------------------------------------------

        String tableStr = order.getTableNumber() != null ? String.valueOf(order.getTableNumber()) : "Unknown";
        holder.tableNumText.setText("Table: " + tableStr);

        String currentStatus = order.getStatus() != null ? order.getStatus() : "Pending";
        holder.statusText.setText("Status: " + currentStatus);

        if (currentStatus.equalsIgnoreCase("Ready")) {
            holder.readyBtn.setVisibility(View.GONE);
        } else {
            holder.readyBtn.setVisibility(View.VISIBLE);
        }

        holder.readyBtn.setOnClickListener(v -> listener.onOrderReadyClick(order));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class KitchenViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdText, itemIdText, statusText, tableNumText;
        Button readyBtn;

        public KitchenViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdText = itemView.findViewById(R.id.ticketOrderId);
            itemIdText = itemView.findViewById(R.id.ticketItemId);
            statusText = itemView.findViewById(R.id.ticketStatus);
            tableNumText = itemView.findViewById(R.id.ticketTableNumber);
            readyBtn = itemView.findViewById(R.id.markReadyBtn);
        }
    }
}