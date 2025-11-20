package com.example.womenshop.controller;

import com.example.womenshop.SceneManager;
import com.example.womenshop.model.Category;
import com.example.womenshop.model.Product;
import com.example.womenshop.service.CategoryService;
import com.example.womenshop.service.ProductService;
import com.example.womenshop.util.UIUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DisplayProductController implements Initializable, SceneAwareController, TypicalController {

    @FXML private ListView<Product> lvProducts;
    @FXML private ComboBox<Category> cmbFilterCategory;
    @FXML private Label lblProductDetails;
    @FXML private Button btnBack;

    private SceneManager sceneManager;
    private ProductService productService;
    private CategoryService categoryService;

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    @Override
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @Override
    public void initData() {
        loadCategories();
        loadProducts();
        setupUI();
        setupListeners();
    }

    private void loadCategories() {
        List<Category> categories = categoryService.listAllCategories();
        cmbFilterCategory.setItems(FXCollections.observableArrayList(categories));
    }

    private void loadProducts() {
        List<Product> products = productService.listAllProducts();
        lvProducts.setItems(FXCollections.observableArrayList(products));
    }

    private void setupUI() {
        UIUtils.setupComboBoxDisplay(cmbFilterCategory, Category::getName);
        UIUtils.setupListViewDisplay(lvProducts, p ->
                p.getName() + " - " + p.getSalePrice() + "€ (Stock: " + p.getStock() + ")"
        );
    }

    private void setupListeners() {
        lvProducts.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> displayProductDetails(newVal)
        );

        cmbFilterCategory.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                filterByCategory(newVal);
            } else {
                loadProducts();
            }
        });
    }

    private void displayProductDetails(Product p) {
        if (p != null) {
            String details = String.format(
                    "Nom: %s\nCatégorie: %s\nPrix d'achat: %.2f€\nPrix de vente: %.2f€\nPromotion: %s\nStock: %d",
                    p.getName(),
                    p.getCategory().getName(),
                    p.getPurchasePrice(),
                    p.getSalePrice(),
                    p.isDiscounted() ? "Oui" : "Non",
                    p.getStock()
            );
            lblProductDetails.setText(details);
        } else {
            lblProductDetails.setText("");
        }
    }

    private void filterByCategory(Category category) {
        List<Product> filtered = productService.filterByCategory(category);
        lvProducts.setItems(FXCollections.observableArrayList(filtered));
    }

    @FXML
    private void onBack() {
        sceneManager.show("Menu");
    }

    @FXML
    private void onResetFilter() {
        cmbFilterCategory.setValue(null);
        loadProducts();
    }
}