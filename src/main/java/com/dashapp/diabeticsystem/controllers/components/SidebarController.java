package com.dashapp.diabeticsystem.controllers.components;

import com.dashapp.diabeticsystem.enums.ROLE;
import com.dashapp.diabeticsystem.view.Router;
import com.dashapp.diabeticsystem.models.Login;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class SidebarController {


    @FXML private VBox vbox;

    @FXML private  Button chatPaziente;
    @FXML private  Button chatDiabetologo;
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

    public void handleChatDiabetologo() {
        Router.navigateToChat();
    }

    public void handleChatPaziente() {
        Router.navigateToChat();
    }

    /**
     * Funzione che permette di settare i bottoni (tutti nascosti inizialmente) della sidebar in modo che,
     * una volta loggato l'utente, sia possibile mostrare solo quelli necessari
     */
    private void initializeSidebar() {

        for(Node n : vbox.getChildren() ) {
            if(n instanceof Button b){
                setButton(b,false);
            }

        }





        // Il pulsante di logout è visibile solo se un utente è loggato
        logoutButton.setVisible(user != null);
        logoutButton.setManaged(user != null);

        if (user != null) {
            ROLE role = user.getRole();
            switch (role) {
                case ADMIN:
                    // Se vuoi che l'admin abbia una dashboard, rendi visibile dashboardButton
                    setButton(homeButton,true);
                    setButton(diabetologiButton,true);

                    break;
                case DIABETOLOGO:
                    // Il Diabetologo vede Home, Pazienti, Impostazioni, Logout
                    setButton(homeButton,true);
                    setButton(pazientiButton,true);
                    setButton(terapieButton,true);
                    setButton(chatDiabetologo,true);
                    setButton(settingsButton,true);

                    break;
                case PAZIENTE:
                    // Il Paziente vede Home, Terapie, Insulina, Impostazioni, Logout

                    setButton(homeButton,true);
                    setButton(insulinaButton,true);
                    setButton(settingsButton,true);
                    setButton(chatPaziente,true);
                    setButton(assunzioniButton,true);

                    break;
                default:
                    break;
            }
        }
    }

    private void setButton(Button b,boolean visible) {
        b.setVisible(visible);
        b.setManaged(visible);

    }



}
