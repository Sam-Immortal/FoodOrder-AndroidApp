package com.example.foodorderapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Map;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.MyOrdersViewHolder> {

    private List<Order> orderList;
    private Map<Long, String> foodDictionary;

    public MyOrdersAdapter(List<Order> orderList, Map<Long, String> foodDictionary) {
        this.orderList = orderList;
        this.foodDictionary = foodDictionary;
    }

    @NonNull
    @Override
    public MyOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_order_row, parent, false);
        return new MyOrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrdersViewHolder holder, int position) {
        Order order = orderList.get(position);

        String foodName = "Unknown Item";
        if (order.getMenuItemId() != null && foodDictionary.containsKey(order.getMenuItemId())) {
            foodName = foodDictionary.get(order.getMenuItemId());
        }

        holder.titleText.setText("Order #" + order.getId() + " - " + foodName);

        String tableStr = order.getTableNumber() != null ? String.valueOf(order.getTableNumber()) : "Unknown";
        holder.tableText.setText("Table: " + tableStr);

        String status = order.getStatus() != null ? order.getStatus() : "Pending";
        holder.statusText.setText(status);

        // Color code the status! Orange for Pending, Green for Ready.
        if (status.equalsIgnoreCase("Ready")) {
            holder.statusText.setTextColor(Color.parseColor("#4CAF50")); // Green
        } else {
            holder.statusText.setTextColor(Color.parseColor("#FF9800")); // Orange
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class MyOrdersViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, tableText, statusText;

        public MyOrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.myOrderTitle);
            tableText = itemView.findViewById(R.id.myOrderTable);
            statusText = itemView.findViewById(R.id.myOrderStatus);
        }
    }
}