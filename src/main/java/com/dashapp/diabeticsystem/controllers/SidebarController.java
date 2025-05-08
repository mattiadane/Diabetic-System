package com.dashapp.diabeticsystem.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SidebarController {

    @FXML private Button homeButton;
    @FXML private Button pazientiButton;
    @FXML private Button settingsButton;
    @FXML private Button logoutButton;

    public SidebarController() {}

    /**
     * Funzione che permette di inizializzare i componenti della sidebar
     * definendo il loro comportamento.
     */
    public void initialize() {
        this.homeButton.setOnAction(event -> {
            System.out.println("Home button clicked");
        });

        this.pazientiButton.setOnAction(event -> {
            System.out.println("Pazienti button clicked");
        });

        this.settingsButton.setOnAction(event -> {
            System.out.println("Settings button clicked");
        });

        this.logoutButton.setOnAction(event -> {
            System.out.println("Logout button clicked");
        });
    }

}
