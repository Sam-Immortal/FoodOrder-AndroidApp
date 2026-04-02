package com.example.foodorderapp;

public class OrderRequest {
    private Long menuItemId;

    // Constructor
    public OrderRequest(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public Long getMenuItemId() {
        return menuItemId;
    }
}