package com.example.womenshop.controller;

import com.example.womenshop.SceneManager;
import com.example.womenshop.model.Category;
import com.example.womenshop.model.Product;

import com.example.womenshop.service.CategoryService;
import com.example.womenshop.service.ProductService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WomenShopController implements Initializable, SceneAwareController {

    @FXML private ListView<Product> lvProducts;
    @FXML private TextField txtName, txtPrice, txtStock;
    @FXML private ComboBox<Category> cmbCategory;
    @FXML private Button btnSave, btnDelete, btnFilter, btnReset;

    private ProductService productService;
    private CategoryService categoryService;


    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    private void displayProductDetails(Product p) {
        if (p != null) {
            txtName.setText(p.getName());
            cmbCategory.setValue(p.getCategory());
            txtPrice.setText(String.valueOf(p.getPurchasePrice()));
            txtStock.setText(String.valueOf(p.getStock()));
        }
    }

    private void fetchProducts() {
        List<Product> products = productService.listAllProducts();
        if (products != null) {
            lvProducts.setItems(FXCollections.observableArrayList(products));
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Category> categories = FXCollections.observableArrayList("Clothes", "Shoes", "Accessory");
        cmbCategory.setItems(categories);

        fetchProducts();

        lvProducts.getSelectionModel().selectedItemProperty().addListener(
                e -> displayProductDetails(lvProducts.getSelectionModel().getSelectedItem())
        );
    }

    @FXML
    private void onDelete() {
        Product selected = lvProducts.getSelectionModel().getSelectedItem();
        if (selected != null) {
            productService.removeProduct(selected.getId());
            fetchProducts();
        }
    }

    @FXML
    private void onFilter() {
        Product selected = lvProducts.getSelectionModel().getSelectedItem();
        Category category = cmbCategory.getValue();

        if (selected == null && category != null) {

            List<Product> products = productService.listAllProducts();
            if (products != null) {
                List<Product> filtered = productService.filterByCategory(category);

                lvProducts.setItems(FXCollections.observableArrayList(filtered));
            }
        }
    }

    @FXML
    private void onSave() {
        try {
            Product selected = lvProducts.getSelectionModel().getSelectedItem();
            String name = txtName.getText();
            Category category = cmbCategory.getValue();
            double price = Double.parseDouble(txtPrice.getText());
            int stock = Integer.parseInt(txtStock.getText());



            if (selected == null) {
                Product p = new Product(-1, category,name, price, 0, false, stock);
                productService.registerProduct(p);
            } else {
                selected.setName(name);
                selected.setCategory(category);
                selected.setPurchasePrice(price);
                selected.setStock(stock);
                productService.updateProductDetails(selected);
            }
            onReset();
        } catch (NumberFormatException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    @FXML
    private void onReset() {
        lvProducts.getSelectionModel().clearSelection();
        txtName.clear();
        cmbCategory.setValue(null);
        txtPrice.clear();
        txtStock.clear();

        fetchProducts();
    }

    @Override
    public void setSceneManager(SceneManager sceneManager) {

    }

}
