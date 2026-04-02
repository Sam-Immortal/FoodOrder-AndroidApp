package com.example.foodorderapp;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<MenuItem> cartItems;

    // Private constructor so nobody else can create a new CartManager
    private CartManager() {
        cartItems = new ArrayList<>();
    }

    // This gets our single, shared cart
    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addItem(MenuItem item) {
        cartItems.add(item);
    }

    public List<MenuItem> getCartItems() {
        return cartItems;
    }

    public void clearCart() {
        cartItems.clear();
    }
}