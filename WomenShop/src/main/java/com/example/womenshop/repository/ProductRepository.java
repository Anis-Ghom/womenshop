package com.example.womenshop.repository;

import com.example.womenshop.model.Product;

import java.util.List;

public interface ProductRepository {
    void addProduct(Product p);
    void updateProduct(Product p);
    void deleteProduct(int id);
    List<Product> getAllProducts();
    Product getProductById(int id);
}

