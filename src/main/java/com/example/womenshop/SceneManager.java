package com.example.womenshop;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {

    private final Stage primaryStage;
    private final Map<String, FXMLLoader> loaders = new HashMap<>();

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Charge un FXML et le stocke pour réutilisation.
     * @param name clé pour identifier la scène
     * @param fxmlPath chemin du fichier FXML
     * @throws IOException
     */
    public void loadScene(String name, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.load();  // charge le FXML et initialise le controller
        loaders.put(name, loader);
    }

    /**
     * Affiche une scène déjà chargée
     * @param name clé de la scène
     */
    public void show(String name) {
        FXMLLoader loader = loaders.get(name);
        if (loader == null) {
            throw new RuntimeException("Scene " + name + " not loaded!");
        }

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Récupère le controller pour injection DI
     */
    public <T> T getController(String name, Class<T> clazz) {
        FXMLLoader loader = loaders.get(name);
        if (loader == null) throw new RuntimeException("Scene " + name + " not loaded!");
        return loader.getController();
    }
}
