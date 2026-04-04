package com.example.foodorderapp;

public class Chef {
    private String name;
    private String password;

    public Chef(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() { return name; }
    public String getPassword() { return password; }
}