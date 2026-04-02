package com.example.foodorderapp;

public class Order {
    private Long id;
    private Long menuItemId;
    private String status; // e.g., "Pending", "Ready"

    public Long getId() { return id; }
    public Long getMenuItemId() { return menuItemId; }
    public String getStatus() { return status; }
}