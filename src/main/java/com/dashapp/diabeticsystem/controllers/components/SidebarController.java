package com.dashapp.diabeticsystem.controllers.components;

import com.dashapp.diabeticsystem.enums.ROLE;
import com.dashapp.diabeticsystem.view.Router;
import com.dashapp.diabeticsystem.models.Login;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SidebarController {


    @FXML private Button homeButton;
    @FXML private Button pazientiButton;
    @FXML private Button terapieButton;
    @FXML private Button settingsButton;
    @FXML private Button insulinaButton;
    @FXML private Button logoutButton;
    @FXML private Button diabetologiButton;
    @FXML private Button assunzioniButton;


    private Login user;

    public void setUser(Login user) {
        // Nascondi tutti i pulsanti all'inizio per resettare lo stato
        this.user = user;
        initializeSidebar();

    }

    public void handleHome() {
        Router.navigatetoDashboard();
    }


    public void handlePazienti() {
        Router.navigateToPazienti();
    }

    public void handleTerapie() {
        Router.navigateToTerapia();
    }

    public void handleSettings() {
        Router.navigateToSettings();
    }

    public void handleLogout() {
        Router.logout();
    }

    public void handleInsulina() { Router.navigateToInsulina(); }

    public void handleDiabetologi() { Router.navigateToDiabetologi(); }

    public void handleAssunzioni() { Router.navigateToAddAssunzione(); }

    /**
     * Funzione che permette di settare i bottoni (tutti nascosti inizialmente) della sidebar in modo che,
     * una volta loggato l'utente, sia possibile mostrare solo quelli necessari
     */
    private void initializeSidebar() {
        homeButton.setVisible(false);
        homeButton.setManaged(false); // setManaged(false) fa sì che l'elemento non occupi spazio nel layout
        pazientiButton.setVisible(false);
        pazientiButton.setManaged(false);
        terapieButton.setVisible(false);
        terapieButton.setManaged(false);
        settingsButton.setVisible(false);
        settingsButton.setManaged(false);
        insulinaButton.setVisible(false);
        insulinaButton.setManaged(false);
        diabetologiButton.setVisible(false);
        diabetologiButton.setManaged(false);
        assunzioniButton.setVisible(false);
        assunzioniButton.setManaged(false);



        // Il pulsante di logout è visibile solo se un utente è loggato
        logoutButton.setVisible(user != null);
        logoutButton.setManaged(user != null);

        if (user != null) {
            ROLE role = user.getRole();
            switch (role) {
                case ADMIN:
                    // Se vuoi che l'admin abbia una dashboard, rendi visibile dashboardButton
                    homeButton.setVisible(true);
                    homeButton.setManaged(true);
                    diabetologiButton.setVisible(true);
                    diabetologiButton.setManaged(true);
                    break;
                case DIABETOLOGO:
                    // Il Diabetologo vede Home, Pazienti, Impostazioni, Logout
                    homeButton.setVisible(true);
                    homeButton.setManaged(true);
                    pazientiButton.setVisible(true);
                    pazientiButton.setManaged(true);
                    terapieButton.setVisible(true);
                    terapieButton.setManaged(true);
                    settingsButton.setVisible(true);
                    settingsButton.setManaged(true);
                    break;
                case PAZIENTE:
                    // Il Paziente vede Home, Terapie, Insulina, Impostazioni, Logout
                    homeButton.setVisible(true);
                    homeButton.setManaged(true);
                    insulinaButton.setVisible(true);
                    insulinaButton.setManaged(true);
                    settingsButton.setVisible(true);
                    settingsButton.setManaged(true);
                    assunzioniButton.setVisible(true);
                    assunzioniButton.setManaged(true);
                    break;
                default:
                    break;
            }
        }
    }


}
