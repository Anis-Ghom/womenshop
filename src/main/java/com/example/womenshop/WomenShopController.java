package com.example.womenshop;

import com.example.womenshop.dao.DBManager;
import com.example.womenshop.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WomenShopController implements Initializable {

    @FXML private ListView<Product> lvProducts;
    @FXML private TextField txtName, txtPrice, txtStock;
    @FXML private ComboBox<String> cmbCategory;
    @FXML private Button btnAdd, btnSave, btnDelete;

    private DBManager manager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manager = new DBManager();

        ObservableList<String> categories = FXCollections.observableArrayList("Clothes", "Shoes", "Accessory");
        cmbCategory.setItems(categories);

        fetchProducts();

        lvProducts.getSelectionModel().selectedItemProperty().addListener(
                e -> displayProductDetails(lvProducts.getSelectionModel().getSelectedItem())
        );
    }

    private void fetchProducts() {
        List<Product> products = manager.loadProducts();
        if (products != null) {
            lvProducts.setItems(FXCollections.observableArrayList(products));
        }
    }

    private void displayProductDetails(Product p) {
        if (p != null) {
            txtName.setText(p.getName());
            cmbCategory.setValue(p.getCategory());
            txtPrice.setText(String.valueOf(p.getPrice()));
            txtStock.setText(String.valueOf(p.getStock()));
        }
    }

    @FXML
    private void onNew() {
        lvProducts.getSelectionModel().clearSelection();
        txtName.clear();
        cmbCategory.setValue(null);
        txtPrice.clear();
        txtStock.clear();
    }

    @FXML
    private void onSave() {
        Product selected = lvProducts.getSelectionModel().getSelectedItem();
        String name = txtName.getText();
        String category = cmbCategory.getValue();
        double price = Double.parseDouble(txtPrice.getText());
        int stock = Integer.parseInt(txtStock.getText());

        if (selected == null) {
            Product p = new Product(name, category, price, stock);
            manager.addProduct(p);
        } else {
            selected.setName(name);
            selected.setCategory(category);
            selected.setPrice(price);
            selected.setStock(stock);
            manager.updateProduct(selected);
        }
        fetchProducts();
    }

    @FXML
    private void onDelete() {
        Product selected = lvProducts.getSelectionModel().getSelectedItem();
        if (selected != null) {
            manager.deleteProduct(selected.getId());
            fetchProducts();
        }
    }
}
