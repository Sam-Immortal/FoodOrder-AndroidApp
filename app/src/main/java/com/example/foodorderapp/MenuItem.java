package com.example.foodorderapp;

public class MenuItem {
    private Long id;
    private String name;
    private String description;
    private double price;

    // --- NEW FIELDS ---
    private String imageUrl;
    private boolean isAvailable;

    // Default constructor (Retrofit/Gson likes having this)
    public MenuItem() {
    }

    // Constructor we used in the Publish Button code
    public MenuItem(String name, String description, double price, String imageUrl, boolean isAvailable) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.isAvailable = isAvailable;
    }

    // --- GETTERS ---
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public boolean isAvailable() { return isAvailable; }

    // --- SETTERS ---
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setAvailable(boolean available) { isAvailable = available; }
}