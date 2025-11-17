package com.example.womenshop.service;

import com.example.womenshop.model.Category;
import com.example.womenshop.model.Product;
import com.example.womenshop.repository.CategoryRepository;
import com.example.womenshop.repository.ProductRepository;

import java.util.List;

public class CategoryService {
    private final CategoryRepository repo;

    public CategoryService(CategoryRepository repo) {
        this.repo = repo;
    }

    public void registerCategory(Category c) {
        repo.addCategory(c);
    }

    public List<Category> listAllProducts() {
        return repo.getAllCategories();
    }

    public Category findCategoryById(int id) {
        return repo.getCategoryById(id);
    }

    public void updateCategoryDetails(Category c) {
        repo.updateCategory(c);
    }

    public void removeCategory(int id) {
        repo.deleteCategory(id);
    }

    /*public List<Product> filterByCategory(Category category) {
        return repo.getAllProducts().stream()
                .filter(p -> p.getCategory().equals(category.getName()))
                .toList();
    }*/
}
