package com.example.foodorderapp;

public class StatusUpdateRequest {
    private String status;

    public StatusUpdateRequest(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}