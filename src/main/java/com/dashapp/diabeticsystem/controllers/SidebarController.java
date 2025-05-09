package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.models.Login;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import com.dashapp.diabeticsystem.enums.ROLES;

public class SidebarController {

    @FXML private Button homeButton;
    @FXML private Button pazientiButtonD;
    @FXML private Button settingsButtonD;
    @FXML private Button settingsButtonP;
    @FXML private Button terapieButton;
    @FXML private Button logoutButton;

    @FXML private VBox sidebarDiabetologo = new VBox();
    @FXML private VBox sidebarPaziente = new VBox();

    private ROLES role;


    public SidebarController() {
    }

    /**
     * Funzione che permette di inizializzare i componenti della sidebar
     * definendo il loro comportamento.
     */
    public void initialize() {



        this.homeButton.setOnAction(event -> {
            System.out.println("Home button clicked");
        });

        this.pazientiButtonD.setOnAction(event -> {
            System.out.println("Pazienti button clicked");
        });

        this.settingsButtonD.setOnAction(event -> {
            System.out.println("Settings diabetologo button clicked");
        });

        this.settingsButtonP.setOnAction(event -> {
            System.out.println("Settings paziente button clicked");
        });

        this.logoutButton.setOnAction(event -> {
            System.out.println("Logout button clicked");
        });

        this.terapieButton.setOnAction(event -> {
            System.out.println("Terapie button clicked");
        });
    }

    /**
     * Imposta il valore del ruolo dell'utente che ha appena eseguito l'accesso.
     * @param user utente che ha appena eseguito l'accesso.
     */
    public void setUserRole(Login user){
        System.out.println(user);
        if(user.getId_diabetologo() != 0){ this.sidebarDiabetologo.setVisible(true); this.sidebarPaziente.setVisible(false); }
        if(user.getId_paziente() != 0){ this.sidebarPaziente.setVisible(true); this.sidebarDiabetologo.setVisible(false); }


    }

}
