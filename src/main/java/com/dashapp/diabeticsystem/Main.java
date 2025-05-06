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

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/login.fxml"));


        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        URL cssUrl = getClass().getResource("css/style.css");

        scene.getStylesheets().add(cssUrl.toExternalForm());


        stage.setTitle("Diabetic System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static DbManager getDbManager() {
        return dbManager;
    }
}
