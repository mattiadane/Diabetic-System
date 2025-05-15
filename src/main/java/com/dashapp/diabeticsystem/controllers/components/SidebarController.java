package com.dashapp.diabeticsystem.controllers.components;

import com.dashapp.diabeticsystem.View.Router;
import com.dashapp.diabeticsystem.models.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


public class SidebarController {
    @FXML
    private VBox sidebarRoot;
    @FXML private Button homeButton;
    @FXML private Button pazientiButton;
    @FXML private Button terapieButton;
    @FXML private Button settingsButton;
    @FXML private Button logoutButton;

    private Login user;

    public void setUser(Login user) {
        // Nascondi tutti i pulsanti all'inizio per resettare lo stato
        this.user = user;
        initializeSidebar();

    }

    public void handleHome(ActionEvent event) {
        Router.navigatetoDashboard();
    }


    public void handlePazienti(ActionEvent event) {

    }

    public void handleTerapie(ActionEvent event) {
    }

    public void handleSettings(ActionEvent event) {
        Router.navigateToSettings();
    }

    public void handleLogout(ActionEvent event) {

    }

    private void initializeSidebar() {
        homeButton.setVisible(false);
        homeButton.setManaged(false); // setManaged(false) fa sì che l'elemento non occupi spazio nel layout
        pazientiButton.setVisible(false);
        pazientiButton.setManaged(false);
        terapieButton.setVisible(false);
        terapieButton.setManaged(false);
        settingsButton.setVisible(false);
        settingsButton.setManaged(false);



        // Il pulsante di logout è visibile solo se un utente è loggato
        logoutButton.setVisible(user != null);
        logoutButton.setManaged(user != null);

        if (user != null) {
            String role = user.getRole();
            System.out.println("Sidebar: Aggiornamento per ruolo: " + role);

            switch (role) {
                case "admin":
                    // Se vuoi che l'admin abbia una dashboard, rendi visibile dashboardButton
                    homeButton.setVisible(true);
                    homeButton.setManaged(true);
                    break;
                case "diabetologo":
                    // Il Diabetologo vede Home, Pazienti, Impostazioni, Logout
                    homeButton.setVisible(true);
                    homeButton.setManaged(true);
                    pazientiButton.setVisible(true);
                    pazientiButton.setManaged(true);
                    settingsButton.setVisible(true);
                    settingsButton.setManaged(true);
                    break;
                case "paziente":
                    // Il Paziente vede Home, Terapie, Impostazioni, Logout
                    homeButton.setVisible(true);
                    homeButton.setManaged(true);
                    terapieButton.setVisible(true);
                    terapieButton.setManaged(true);
                    settingsButton.setVisible(true);
                    settingsButton.setManaged(true);
                    break;
                default:
                    System.err.println("Sidebar: Ruolo utente non riconosciuto: " + role);
                    // Per ruoli non riconosciuti, tutti i pulsanti rimangono nascosti
                    break;
            }
        } else {
            // Se nessun utente è loggato, tutti i pulsanti (incluso logout) sono nascosti
            System.out.println("Sidebar: Nessun utente loggato, tutti i pulsanti nascosti.");
        }
    }


}
