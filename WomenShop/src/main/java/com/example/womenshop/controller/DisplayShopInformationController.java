package com.example.womenshop.controller;

import com.example.womenshop.SceneManager;
import com.example.womenshop.model.Product;
import com.example.womenshop.service.ProductService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DisplayShopInformationController implements Initializable, SceneAwareController, TypicalController {

    @FXML private Label lblTotalProducts;
    @FXML private Label lblTotalStock;
    @FXML private Label lblTotalValue;
    @FXML private Label lblLowStockProducts;
    @FXML private Label lblMostExpensiveProduct;
    @FXML private Label lblCheapestProduct;
    @FXML private Button btnBack;

    private SceneManager sceneManager;
    private ProductService productService;

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    @Override
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @Override
    public void initData() {
        calculateStatistics();
    }

    private void calculateStatistics() {
        List<Product> products = productService.listAllProducts();

        if (products == null || products.isEmpty()) {
            setDefaultValues();
            return;
        }

        // Nombre total de produits
        int totalProducts = products.size();
        lblTotalProducts.setText(String.valueOf(totalProducts));

        // Stock total
        int totalStock = products.stream()
                .mapToInt(Product::getStock)
                .sum();
        lblTotalStock.setText(String.valueOf(totalStock));

        // Valeur totale du stock (basée sur le prix d'achat)
        double totalValue = products.stream()
                .mapToDouble(p -> p.getPurchasePrice() * p.getStock())
                .sum();
        lblTotalValue.setText(String.format("%.2f€", totalValue));

        // Produits avec stock faible (< 5)
        long lowStockCount = products.stream()
                .filter(p -> p.getStock() < 5)
                .count();
        lblLowStockProducts.setText(String.valueOf(lowStockCount));

        // Produit le plus cher
        Product mostExpensive = products.stream()
                .max((p1, p2) -> Double.compare(p1.getSalePrice(), p2.getSalePrice()))
                .orElse(null);
        if (mostExpensive != null) {
            lblMostExpensiveProduct.setText(
                    mostExpensive.getName() + " (" + mostExpensive.getSalePrice() + "€)"
            );
        }

        // Produit le moins cher
        Product cheapest = products.stream()
                .min((p1, p2) -> Double.compare(p1.getSalePrice(), p2.getSalePrice()))
                .orElse(null);
        if (cheapest != null) {
            lblCheapestProduct.setText(
                    cheapest.getName() + " (" + cheapest.getSalePrice() + "€)"
            );
        }
    }

    private void setDefaultValues() {
        lblTotalProducts.setText("0");
        lblTotalStock.setText("0");
        lblTotalValue.setText("0.00€");
        lblLowStockProducts.setText("0");
        lblMostExpensiveProduct.setText("Aucun");
        lblCheapestProduct.setText("Aucun");
    }

    @FXML
    private void onBack() {
        sceneManager.show("Menu");
    }

    @FXML
    private void onRefresh() {
        calculateStatistics();
    }
}