package com.dashapp.diabeticsystem.view;

import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.controllers.MainController;
import com.dashapp.diabeticsystem.enums.ROLE;
import com.dashapp.diabeticsystem.models.Login;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

/**
 * La classe Router gestisce la navigazione tra le diverse viste (schermate)
 * dell'applicazione JavaFX. Si occupa di caricare i file FXML, di impostare
 * i controller associati e di gestire la transizione tra le scene o il
 * contenuto all'interno della dashboard principale.
 */
public class Router {

    private static MainController mainController;
    private static Login authenticatedUser = null;

    private static Node chatPazienteView;
    private static Node chatDiabetologoView;

    /**
     * Imposta il controller principale dell'applicazione. Questo controller
     * è tipicamente quello che gestisce il layout principale e il cambio di contenuto.
     *
     * @param controller L'istanza del MainController da impostare.
     */
    public static void setMainController(MainController controller) {
        mainController = controller;
    }

    /**
     * Imposta l'utente autenticato corrente. Questo utente sarà utilizzato
     * per determinare i permessi e le viste disponibili.
     *
     * @param login L'oggetto Login che rappresenta l'utente autenticato.
     */
    public static void setAuthenticatedUser(Login login) {
        authenticatedUser = login;
    }

    /**
     * Carica una vista FXML e la imposta come contenuto centrale del layout
     * principale dell'applicazione (gestito dal MainController).
     * Questo metodo gestisce il caricamento unico delle viste chat
     * per prevenire duplicazioni e riutilizzare lo stato.
     *
     * @param fxml Il nome del file FXML da caricare (es. "dashboardAdmin.fxml", "chatPaziente.fxml").
     */
    public static void loadView(String fxml) {
        try {
            if (mainController == null) {
                System.err.println("Error: MainController is null. Cannot load content.");
                return;
            }

            Node viewToLoad ; // Variabile temporanea per la vista da caricare

            // Logica per gestire il caricamento e il riutilizzo delle viste della chat
            if (fxml.equals("chatPaziente.fxml")) {
                if (chatPazienteView == null) { // Se la vista della chat paziente non è stata ancora caricata
                    URL fxmlUrl = Main.class.getResource("fxml/" + fxml);
                    FXMLLoader loader = new FXMLLoader(fxmlUrl);
                    viewToLoad = loader.load();
                    chatPazienteView = viewToLoad; // Memorizza la vista

                } else {
                    viewToLoad = chatPazienteView; // Riutilizza la vista esistente
                }


            }
            else {
                URL fxmlUrl = Main.class.getResource("fxml/" + fxml);
                FXMLLoader loader = new FXMLLoader(fxmlUrl);
                viewToLoad = loader.load();
            }

            // Imposta il contenuto centrale del MainController con la vista decisa dalla logica precedente.
            if (viewToLoad != null) {
                mainController.setContent(viewToLoad);
            }

        } catch (IOException e) {
            System.err.println("Error loading content view: " + fxml + " - " + e.getMessage());

        }
    }

    /**
     * Cambia l'intera scena dello Stage principale dell'applicazione.
     * Utilizzato per transizioni tra layout principali, come dal login alla dashboard.
     * Dimensioni della scena vengono adattate in base all'FXML caricato.
     *
     * @param fxml Il nome del file FXML della nuova scena da caricare (es. "login.fxml", "mainView.fxml").
     */
    public static void changeScene(String fxml) {
        int x, y;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/" + fxml));
            Parent root = fxmlLoader.load();

            Object newMainController = fxmlLoader.getController();

            // Se la scena caricata è il layout principale (mainView.fxml),
            // imposta il MainController e le dimensioni appropriate.
            if (fxml.equals("mainView.fxml")) {
                MainController mainController = (MainController) newMainController;
                mainController.setCurrentUser(authenticatedUser);
                Router.setMainController(mainController);
                x = 1000;
                y = 700;
            } else {
                // Dimensioni predefinite per altre scene (es. login)
                x = 500;
                y = 350;
            }

            Stage stage = Main.getPrimaryStage();
            if (stage == null) {
                System.err.println("Primary stage is null. Cannot switch scene.");
                return;
            }

            Scene newScene = new Scene(root, x, y);
            URL cssUrl = Main.class.getResource("css/style.css");

            // Applica il foglio di stile CSS se disponibile
            if (cssUrl != null)
                newScene.getStylesheets().add(cssUrl.toExternalForm());

            stage.setScene(newScene);
            stage.setTitle("Diabetic System - Dashboard"); // Aggiorna il titolo dello stage
            stage.show();

        } catch (IOException e) {
            System.err.println("Error navigating to dashboard: " + e.getMessage());
        }
    }

    /**
     * Naviga alla dashboard appropriata in base al ruolo dell'utente autenticato.
     * Questo metodo viene chiamato tipicamente dopo un'autenticazione riuscita.
     */
    public static void navigatetoDashboard() {
        if (authenticatedUser == null) {
            System.err.println("No authenticated user to navigate to dashboard.");
            return;
        }
        ROLE role = authenticatedUser.getRole();
        switch (role) {
            case ADMIN -> loadView("dashboardAdmin.fxml");
            case PAZIENTE -> loadView("dashboardPaziente.fxml");
            case DIABETOLOGO -> loadView("dashboardDiabetologo.fxml");
            default -> System.err.println("Unknown role: " + role);
        }
    }

    /**
     * Naviga alla vista delle impostazioni.
     */
    public static void navigateToSettings() {
        loadView("impostazioni.fxml");
    }

    /**
     * Esegue il logout dell'utente corrente.
     * Azzera l'utente autenticato e ritorna alla scena di login.
     * Resetta anche le viste della chat memorizzate per prevenire l'utilizzo di dati utente precedenti.
     */
    public static void logout() {
        authenticatedUser = null;
        changeScene("login.fxml");

        // Resetta le viste e i controller della chat al logout
        // in modo che vengano ricaricate da zero alla prossima sessione.
        chatPazienteView = null;
        chatDiabetologoView = null;

    }

    /**
     * Naviga alla scena per aggiungere il livello di insulina.
     */
    public static void navigateToInsulina() {
        loadView("aggiungiLivelloInsulina.fxml");
    }

    /**
     * Naviga alla scena per aggiungere terapie.
     */
    public static void navigateToTerapia() {
        loadView("aggiungiTerapie.fxml");
    }

    /**
     * Naviga alla scena della lista dei pazienti.
     */
    public static void navigateToPazienti() {
        loadView("listaPazienti.fxml");
    }

    /**
     * Naviga alla scena della lista dei diabetologi.
     */
    public static void navigateToDiabetologi() {
        loadView("listaDiabetologi.fxml");
    }

    /**
     * Naviga alla scena per aggiungere un'assunzione di farmaco.
     */
    public static void navigateToAddAssunzione() {
        loadView("assunzioneFarmaco.fxml");
    }

    /**
     * Naviga alla scena della chat appropriata in base al ruolo dell'utente autenticato.
     * Se l'utente è un diabetologo, naviga alla chat del diabetologo; se è un paziente, alla chat del paziente.
     */
    public static void navigateToChat() {
        if (authenticatedUser == null) {
            System.err.println("No authenticated user to navigate to chat.");
            return;
        }
        switch (authenticatedUser.getRole()) {
            case DIABETOLOGO -> loadView("chatDiabetologo.fxml");
            case PAZIENTE -> loadView("chatPaziente.fxml");
            default -> System.err.println("Unknown role for chat navigation: " + authenticatedUser.getRole());
        }
    }
}