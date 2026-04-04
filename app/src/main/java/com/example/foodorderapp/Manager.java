package com.example.foodorderapp;

public class Manager {
    private Long id;
    private String name;
    private String password; // Email is gone here too!

    // Empty constructor for Retrofit
    public Manager() {}

    // Clean 2-argument constructor
    public Manager(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getPassword() { return password; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; }
}