package com.example.foodorderapp;
import com.google.gson.annotations.SerializedName;

public class OrderRequest {
    @SerializedName("itemId")
    private Long menuItemId;

    // Add Table Number
    private Integer tableNumber;

    public OrderRequest(Long menuItemId, Integer tableNumber) {
        this.menuItemId = menuItemId;
        this.tableNumber = tableNumber;
    }

    public Long getMenuItemId() { return menuItemId; }
    public Integer getTableNumber() { return tableNumber; }
}