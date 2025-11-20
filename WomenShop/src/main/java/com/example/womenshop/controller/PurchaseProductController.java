package com.example.womenshop.controller;

import com.example.womenshop.SceneManager;
import com.example.womenshop.model.Product;
import com.example.womenshop.service.ProductService;
import com.example.womenshop.util.UIUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PurchaseProductController implements Initializable, SceneAwareController, TypicalController {

    @FXML private ListView<Product> lvProducts;
    @FXML private TextField txtQuantity;
    @FXML private Label lblTotalCost;
    @FXML private Button btnPurchase, btnCancel;

    private SceneManager sceneManager;
    private ProductService productService;
    private Product selectedProduct;

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
        loadProducts();
        setupUI();
        setupListeners();
    }

    private void loadProducts() {
        List<Product> products = productService.listAllProducts();
        lvProducts.setItems(FXCollections.observableArrayList(products));
    }

    private void setupUI() {
        UIUtils.setupListViewDisplay(lvProducts, p ->
                p.getName() + " - " + p.getPurchasePrice() + "€ (Stock actuel: " + p.getStock() + ")"
        );
    }

    private void setupListeners() {
        lvProducts.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    selectedProduct = newVal;
                    calculateTotalCost();
                }
        );

        txtQuantity.textProperty().addListener((obs, oldVal, newVal) -> calculateTotalCost());
    }

    private void calculateTotalCost() {
        if (selectedProduct != null) {
            try {
                int quantity = Integer.parseInt(txtQuantity.getText());
                double totalCost = selectedProduct.getPurchasePrice() * quantity;
                lblTotalCost.setText(String.format("Coût total: %.2f€", totalCost));
            } catch (NumberFormatException e) {
                lblTotalCost.setText("Coût total: 0.00€");
            }
        }
    }

    @FXML
    private void onPurchase() {
        if (selectedProduct == null) {
            showAlert("Erreur", "Veuillez sélectionner un produit");
            return;
        }

        try {
            int quantity = Integer.parseInt(txtQuantity.getText());

            if (quantity <= 0) {
                showAlert("Erreur", "La quantité doit être positive");
                return;
            }

            // Mettre à jour le stock
            selectedProduct.setStock(selectedProduct.getStock() + quantity);
            productService.updateProductDetails(selectedProduct);

            showAlert("Succès", "Achat effectué avec succès !");
            onReset();

        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer une quantité valide");
        }
    }

    @FXML
    private void onReset() {
        lvProducts.getSelectionModel().clearSelection();
        txtQuantity.clear();
        lblTotalCost.setText("Coût total: 0.00€");
        selectedProduct = null;
        loadProducts();
    }

    @FXML
    private void onCancel() {
        sceneManager.show("Menu");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}