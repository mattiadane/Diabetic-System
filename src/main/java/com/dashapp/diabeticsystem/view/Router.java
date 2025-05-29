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

        }
    }

    /**
     * Naviga alla dashboard appropriata in base al ruolo dell'utente autenticato.
     * Questo metodo viene chiamato dal LoginController dopo un'autenticazione riuscita.
     * Caricherà il *layout principale della dashboard* (es. main.fxml) e poi caricherà
     * al suo interno il contenuto specifico in base al ruolo.
     */

    public static void changeScene (String fxml) {
        int x , y;

        try {

            // Load the main application layout (e.g., main.fxml)
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/" + fxml));
            Parent root = fxmlLoader.load();

            // Get the controller for the main layout
            Object newMainController = fxmlLoader.getController();

            if(fxml.equals("mainView.fxml")){
                MainController mainController = (MainController) newMainController;
                mainController.setCurrentUser(authenticatedUser);
                Router.setMainController(mainController);
                x = 1000;
                y = 700;

            } else {
                x = 500;
                y = 350;
            }



            // Switch the scene of the primary stage
            Stage stage = Main.getPrimaryStage();
            if (stage == null) {
                System.err.println("Primary stage is null. Cannot switch scene.");
                return;
            }

            Scene newScene = new Scene(root, x, y); // Adjust size as needed for your main layout
            URL cssUrl = Main.class.getResource("css/style.css");


            if(cssUrl != null)
                newScene.getStylesheets().add(cssUrl.toExternalForm());


            stage.setScene(newScene);
            stage.setTitle("Diabetic System - Dashboard"); // Update title
            stage.show();



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
        loadView("impostazioni.fxml");
    }


    /**
     * Funzione che permette di eseguire il logout dall'account che ha eseguito l'ultimo login.
     */
    public static void logout() {
        authenticatedUser = null;
        changeScene("login.fxml");

    }
    /**
     * Funzione che mostra la scena per aggiungere il livello di insulina.
     */
    public static void navigateToInsulina(){
        loadView("aggiungiLivelloInsulina.fxml");
    }

    public static void navigateToTerapia(){
        loadView("aggiungiTerapie.fxml");
    }

    public static void navigateToPazienti(){
        loadView("listaPazienti.fxml");
    }


    public static void navigateToDiabetologi(){
        loadView("listaDiabetologi.fxml");
    }





}