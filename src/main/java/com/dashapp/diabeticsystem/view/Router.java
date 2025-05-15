package com.dashapp.diabeticsystem.view;

import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.controllers.MainController;
import com.dashapp.diabeticsystem.enums.ROLE;
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
     * Carica una vista e la imposta come contenuto centrale del layout principale dell'applicazione.
     * Questo metodo viene chiamato *dopo* che la dashboard principale è stata caricata.
     * @param fxml Il nome del file FXML da caricare (es. dashboardAdmin.fxml)
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
     * Naviga alla dashboard appropriata in base al ruolo dell'utente autenticato.
     * Questo metodo viene chiamato dal LoginController dopo un'autenticazione riuscita.
     * Caricherà il *layout principale della dashboard* (es. main.fxml) e poi caricherà
     * al suo interno il contenuto specifico in base al ruolo.
     */

    public static void changeScene () {
        try {

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
        }
    }

    public static void navigatetoDashboard(){
        ROLE role = authenticatedUser.getRole();
        switch (role) {
            case ADMIN -> loadView("dashboardAdmin.fxml");
            case PAZIENTE -> loadView("dashboardPaziente.fxml");
            case DIABETOLOGO -> loadView("dashboardDiabetologo.fxml");
            default -> System.err.println("Unknown role: " + role);
        }
    }
    public static void navigateToSettings(){
        ROLE role = authenticatedUser.getRole();
        switch (role) {
            case PAZIENTE -> loadView("impostazioniPaziente.fxml");
            case DIABETOLOGO -> loadView("impostazioniDiabetologo.fxml");
            default -> System.err.println("Unknown role: " + role);
        }
    }


}