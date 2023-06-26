module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.example.common;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires javafx.media;
    opens com.example.client to javafx.fxml;
    exports com.example.client;
    opens com.example.client.utilities to javafx.fxml;
    exports com.example.client.utilities;
    opens com.example.client.login to javafx.fxml;
    exports com.example.client.login;
    opens com.example.client.collections to javafx.fxml;
    exports com.example.client.collections;
    opens com.example.client.create to javafx.fxml;
    exports com.example.client.create;
    opens com.example.client.music to javafx.media;
    exports com.example.client.music;
    opens com.example.client.visualization to javafx.fxml;
    exports com.example.client.visualization;
    opens com.example.client.worldMap to javafx.web;
    exports com.example.client.worldMap;
}