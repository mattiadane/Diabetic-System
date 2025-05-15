package com.dashapp.diabeticsystem.View;

import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.controllers.MainController;
import com.dashapp.diabeticsystem.models.Login;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent; // Import Parent
import javafx.scene.Node;
import javafx.scene.Scene; // Import Scene
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;


public class Router {

    private static MainController mainController;
    private static Login authenticatedUser = null;

    public static void setMainController(MainController controller) {
        mainController = controller;
    }

    public static void setAuthenticatedUser(Login login) {
        authenticatedUser = login;

    }

    /**
     * Loads a view and sets it as the center content of the main application layout.
     * This method is called *after* the main dashboard is loaded.
     * @param fxml The name of the FXML file to load (e.g., dashboardAdmin.fxml)
     */
    public static void loadView(String fxml) {
        try {
            if (mainController == null) {
                System.err.println("Error: MainController is null. Cannot load content.");
                return;
            }
            URL fxmlUrl = Main.class.getResource("fxml/" + fxml);
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Node view = loader.load();
            mainController.setContent(view);
        } catch (IOException e) {
            System.err.println("Error loading content view: " + fxml + " - " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Navigates to the appropriate dashboard based on the authenticated user's role.
     * This method is called from the LoginController after successful authentication.
     * It will load the *main dashboard layout* (e.g., main.fxml) and then load the
     * specific role-based dashboard content into it.
     */
    public static void changeScene () {
        try {
            if (authenticatedUser == null) {
                System.err.println("No authenticated user to navigate to dashboard.");
                return;
            }

            // Load the main application layout (e.g., main.fxml)
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/mainView.fxml"));
            Parent root = fxmlLoader.load();

            // Get the controller for the main layout
            MainController newMainController = fxmlLoader.getController();

            newMainController.setCurrentUser(authenticatedUser);
            // Set the main controller in the Router (this will call initialize() on MainController)
            Router.setMainController(newMainController);


            // Switch the scene of the primary stage
            Stage stage = Main.getPrimaryStage();
            if (stage == null) {
                System.err.println("Primary stage is null. Cannot switch scene.");
                return;
            }

            Scene newScene = new Scene(root, 1000, 700); // Adjust size as needed for your main layout
            URL cssUrl = Main.class.getResource("css/style.css");

            newScene.getStylesheets().add(cssUrl.toExternalForm());


            stage.setScene(newScene);
            stage.setTitle("Diabetic System - Dashboard"); // Update title
            stage.show();


            // Now, load the specific role-based dashboard content into the mainContainer
            navigatetoDashboard();


        } catch (IOException e) {
            System.err.println("Error navigating to dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void navigatetoDashboard(){
        String role = authenticatedUser.getRole();
        switch (role) {
            case "admin" -> loadView("dashboardAdmin.fxml");
            case "paziente" -> loadView("dashboardPaziente.fxml");
            case "diabetologo" -> loadView("dashboardDiabetologo.fxml");
            default -> System.err.println("Unknown role: " + role);
        }
    }
    public static void navigateToSettings(){
        String role = authenticatedUser.getRole();
        switch (role) {
            case "paziente" -> loadView("impostazioniPaziente.fxml");
            case "diabetologo" -> loadView("impostazioniDiabetologo.fxml");
            default -> System.err.println("Unknown role: " + role);
        }
    }


}