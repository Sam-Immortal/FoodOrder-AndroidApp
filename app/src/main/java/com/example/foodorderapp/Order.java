package com.example.foodorderapp;
import com.google.gson.annotations.SerializedName;

public class Order {
    private Long id;

    @SerializedName("itemId")
    private Long menuItemId;

    private String status;

    // Add Table Number to read from the database
    private Integer tableNumber;

    public Long getId() { return id; }
    public Long getMenuItemId() { return menuItemId; }
    public String getStatus() { return status; }
    public Integer getTableNumber() { return tableNumber; }
}