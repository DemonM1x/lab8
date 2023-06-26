package com.example.client.collections;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class CollectionsWindow {

    private Stage stage;
    private Scene scene;

    public CollectionsWindow() {
        try {
            stage = new Stage();
            URL fxmlLocation = getClass().getResource("collectionsWindow.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();

            scene = new Scene(root, 1080, 650);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        stage.show();
    }
}
