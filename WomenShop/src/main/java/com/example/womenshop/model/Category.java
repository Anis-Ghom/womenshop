package com.example.womenshop.model;

public class Category {
    private int id;
    private String name;
    private double discountRate;

    // Getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getDiscountRate() { return discountRate; }
    public void setDiscountRate(double discountRate) { this.discountRate = discountRate; }


    public Category(int id, String name, double discountRate) {
        this.id = id;
        this.name = name;
        this.discountRate = discountRate;
    }


    @Override
    public String toString() {
        return "ID : " + id + ", Name : " + name + ", Discount Rate : " + discountRate;
    }
}
