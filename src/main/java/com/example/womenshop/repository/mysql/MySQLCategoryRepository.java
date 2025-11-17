package com.example.womenshop.repository.mysql;

import com.example.womenshop.dao.DBManager;
import com.example.womenshop.model.Category;
import com.example.womenshop.repository.CategoryRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLCategoryRepository implements CategoryRepository {

    private final DBManager db;

    public MySQLCategoryRepository(DBManager db) {
        this.db = db;
    }

    @Override
    public void addCategory(Category c) {
        String sql = "INSERT INTO categories (categories_id, products_name, products_purchase_price, products_sale_price, products_discounted, products_stock) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getCategory().getName());
            ps.setString(2, p.getName());
            ps.setDouble(3, p.getPurchasePrice());
            ps.setDouble(4, p.getSalePrice());
            ps.setBoolean(5, p.isDiscounted());
            ps.setInt(6, p.getStock());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCategory(Category c) {

    }

    @Override
    public void deleteCategory(int id) {

    }

    @Override
    public List<Category> getAllCategories() {
        return List.of();
    }

    @Override
    public Category getCategoryById(int id) {
        return null;
    }
}

