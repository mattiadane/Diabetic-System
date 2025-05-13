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
        getStage(stage,"fxml/login.fxml","Diabetic System");
    }

    public static void main(String[] args) {
        launch();
    }

    public static DbManager getDbManager() {
        return dbManager;
    }

    public static <T>T getStage(Stage stage,String fxml,String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));


        Scene scene = new Scene(fxmlLoader.load(), 500, 750);
        URL cssUrl = Main.class.getResource("css/style.css");

        scene.getStylesheets().add(cssUrl.toExternalForm());

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();

        return fxmlLoader.getController();
    }
}
