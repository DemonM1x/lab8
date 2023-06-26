package com.example.client.create;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.org.example.collection.City;

import java.io.IOException;
import java.net.URL;

public class CityManagementWindow {
    private Stage stage;
    private Scene scene;

    private CityManagementWindowController controller;

    public CityManagementWindow(String actionText) {
        try {
            stage = new Stage();
            stage.setResizable(false);
            URL fxmlLocation = getClass().getResource("createWindow.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();

            controller = loader.getController();
            controller.setActionText(actionText);

            scene = new Scene(root, 300, 500);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCity(City city) {
        controller.setEditingCity(city);
    }

    public void show() {
        stage.show();
    }
}
