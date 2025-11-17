package com.example.womenshop;

import com.example.womenshop.controller.WomenShopController;
import com.example.womenshop.dao.DBManager;
import com.example.womenshop.repository.mysql.MySQLCategoryRepository;
import com.example.womenshop.repository.mysql.MySQLProductRepository;
import com.example.womenshop.service.CategoryService;
import com.example.womenshop.service.ProductService;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager sceneManager = new SceneManager(primaryStage);

        // Charger toutes les scènes
        sceneManager.loadScene("main", "/com/example/womenshop/WomenShop.fxml");
        //sceneManager.loadScene("details", "/com/example/womenshop/ProductDetails.fxml");

        // Injection des dépendances
        WomenShopController controller = sceneManager.getController("main", WomenShopController.class);
        controller.setSceneManager(sceneManager); // si tu veux naviguer depuis le controller | cela évite de passer SceneManager en paramètre d'instanciation
        controller.setProductService(new ProductService(new MySQLProductRepository(new DBManager())));
        controller.setCategoryService(new CategoryService(new MySQLCategoryRepository(new DBManager())));

        sceneManager.show("main");
        primaryStage.setTitle("Women Shop");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
