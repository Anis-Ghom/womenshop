package com.example.womenshop.controller;

import com.example.womenshop.SceneManager;
import com.example.womenshop.model.Product;
import com.example.womenshop.model.Transaction;
import com.example.womenshop.service.ProductService;
import com.example.womenshop.util.UIUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class SellProductController implements Initializable, SceneAwareController, TypicalController {

    @FXML private ListView<Product> lvProducts;
    @FXML private TextField txtQuantity;
    @FXML private Label lblTotalAmount;
    @FXML private CheckBox chkApplyDiscount;
    @FXML private Button btnSell, btnCancel;

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
                p.getName() + " - " + p.getSalePrice() + "€ (Stock: " + p.getStock() + ")"
        );
    }

    private void setupListeners() {
        lvProducts.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    selectedProduct = newVal;
                    calculateTotal();
                }
        );

        txtQuantity.textProperty().addListener((obs, oldVal, newVal) -> calculateTotal());
        chkApplyDiscount.selectedProperty().addListener((obs, oldVal, newVal) -> calculateTotal());
    }

    private void calculateTotal() {
        if (selectedProduct != null) {
            try {
                int quantity = Integer.parseInt(txtQuantity.getText());
                double price = selectedProduct.getSalePrice();

                if (chkApplyDiscount.isSelected() && selectedProduct.isDiscounted()) {
                    price = price * (1 - selectedProduct.getCategory().getDiscountRate());
                }

                double total = price * quantity;
                lblTotalAmount.setText(String.format("Total: %.2f€", total));
            } catch (NumberFormatException e) {
                lblTotalAmount.setText("Total: 0.00€");
            }
        }
    }

    @FXML
    private void onSell() {
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

            if (quantity > selectedProduct.getStock()) {
                showAlert("Erreur", "Stock insuffisant");
                return;
            }

            // Mettre à jour le stock
            selectedProduct.setStock(selectedProduct.getStock() - quantity);
            productService.updateProductDetails(selectedProduct);

            showAlert("Succès", "Vente effectuée avec succès !");
            onReset();

        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer une quantité valide");
        }
    }

    @FXML
    private void onReset() {
        lvProducts.getSelectionModel().clearSelection();
        txtQuantity.clear();
        chkApplyDiscount.setSelected(false);
        lblTotalAmount.setText("Total: 0.00€");
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