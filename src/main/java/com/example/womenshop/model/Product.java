package com.example.womenshop.model;

public class Product {
    private int id;
    private String name;
    private String category; // Clothes, Shoes, Accessory
    private double price;
    private double discountPrice;
    private int stock;

    public Product(int id, String name, String category, double price, int stock) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        setDiscountPrice();
    }

    public Product(String name, String category, double price, int stock) {
        this(-1, name, category, price, stock);
    }

    private void setDiscountPrice() {
        switch(category.toLowerCase()) {
            case "clothes": discountPrice = price * 0.7; break;
            case "shoes": discountPrice = price * 0.8; break;
            case "accessory": discountPrice = price * 0.5; break;
            default: discountPrice = price;
        }
    }

    // Getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; setDiscountPrice(); }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; setDiscountPrice(); }
    public double getDiscountPrice() { return discountPrice; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return name + " (" + category + ")";
    }
}
