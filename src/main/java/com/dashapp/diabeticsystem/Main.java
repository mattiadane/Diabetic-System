package com.dashapp.diabeticsystem;

import com.dashapp.diabeticsystem.models.DbManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    private static final DbManager dbManager = new DbManager();
    private static Stage primaryStage; // Keep a reference to the primary stage

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage; // Store the primary stage reference

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 350); // Dimensions for your login scene

        URL cssUrl = Main.class.getResource("css/style.css");

        if(cssUrl != null)
            scene.getStylesheets().add(cssUrl.toExternalForm());



        stage.setTitle("Login diabetic system");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static DbManager getDbManager() {
        return dbManager;
    }

    /**
     * Public method to get the primary stage.
     * This is crucial for switching scenes after login.
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}