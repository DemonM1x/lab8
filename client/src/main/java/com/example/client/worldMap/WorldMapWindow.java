package com.example.client.worldMap;

import com.example.client.collections.CollectionsWindow;
import com.example.client.collections.CollectionsWindowController;
import com.example.client.utilities.RequestHandler;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import main.org.example.collection.City;
import main.org.example.utility.Session;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class WorldMapWindow {
    private Stage stage;
    private CollectionsWindowController controller;
    private WebEngine webEngine;
    private Set<City> collection;

    private Map<Integer, String> ownershipMap = new HashMap<>();
    private Map<String, String> userColorMap = new HashMap<>(); // Stores user-color pairs

    public WorldMapWindow(Set<City> collection) {
        this.collection = collection;

        stage = new Stage();
        WebView webView = new WebView();
        webEngine = webView.getEngine();
        URL url = getClass().getResource("map.html");
        if (url == null) {
            throw new RuntimeException("Cannot find file map.html in html directory");
        }
        webEngine.load(url.toString());

        StackPane root = new StackPane();
        root.getChildren().add(webView);
        Scene scene = new Scene(root, 1500, 900);
        stage.setScene(scene);
        stage.show();

        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                loadCities(collection);
            }
        });
    }

    public void loadCities(Set<City> collection) {
        try {
            this.collection = collection;
            loadOwnershipMap();

            Random random = new Random();

            for (City city : collection) {
                double lng = random.nextDouble() * (179.9 - (-179.9)) + (-179.9);
                double lat = random.nextDouble() * (89.9 - (-89.9)) + (-89.9);

                String color = getIconName(city.getId());
                webEngine.executeScript("addCity('" + city.getName() + "', " + lng + ", " + lat + ", '" + ownershipMap.get(city.getId()) + "', '" + color + "');");
                System.out.println(city.getName() + ": lng - " + lng + ", lat - " + lat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private double convertCoordinate(double value, double originalMin, double originalMax, double targetMin, double targetMax) {
        double normalizedValue = (value - originalMin) / (originalMax - originalMin);
        double clampedValue = Math.max(Math.min(normalizedValue, 1.0), 0.0);
        return targetMin + (clampedValue * (targetMax - targetMin));
    }


    private String getIconName(Integer city_id) {
        String user = ownershipMap.get(city_id);
        return userColorMap.getOrDefault(user, null);
    }

    /**
     * This method loads ownershipMap from the server in Map<Integer, String>.
     * (city_id, client_name)
     * And then, generates colors for each user -> ownershipMap
     * <p>
     * Note, that our client is always "green"
     */
    private void loadOwnershipMap() {
        Session session = RequestHandler.getInstance().getSession();
        this.ownershipMap = CollectionsWindowController.getOwnershipMap();

        for (String user : new HashSet<>(ownershipMap.values())) {
            if (user.equals(session.getName())) {
                userColorMap.put(user, "main.png");
            } else {
                String iconName = generateRandomIconName();
                userColorMap.put(user, iconName);
            }
        }
    }

    private String generateRandomIconName() {
        // This depends on how you named your icons.
        // In this example we assume you have icons named 'icon_#.svg', where # is a number between 1 and 100.
        int iconNumber = new Random().nextInt(35) + 1;
        return "icon_" + iconNumber + ".svg";
    }


    public void show() {
        stage.show();
    }
}
