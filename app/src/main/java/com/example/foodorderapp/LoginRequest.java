package com.example.foodorderapp;

public class LoginRequest {
    private String name; // Changed from email
    private String password;

    public LoginRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }
}