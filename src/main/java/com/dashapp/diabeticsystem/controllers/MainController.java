package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.view.Router;
import com.dashapp.diabeticsystem.controllers.components.SidebarController;
import com.dashapp.diabeticsystem.models.Login;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class MainController {


    @FXML private SidebarController sidebarController; // Questo si collegherà automaticamente all'fx:controller della tua sidebar inclusa
    @FXML private BorderPane mainContainer;


    /**
     * Funzione che inizializza il controller principale
     */
    public void initialize() {
        Router.setMainController(this);
    }

    /**
     * Funzionc che permette di settare il nodo all'interno del container principale dell'applicazione
     * @param view oggetto <code>Node</code> da inserire nel container principale
     */
    public void setContent(Node view) {
        mainContainer.setCenter(view);
    }

    /**
     * Funzione che permette di settare l'utente corretto che ha eseguito l'ultimo login alla Sidebar per mostrare i link
     * corretti in base al ruolo
     * @param currentUser oggetto <code>Login</code> che rappresenta l'utente loggato
     */
    public void setCurrentUser(Login currentUser) {
        sidebarController.setUser(currentUser);

    }
}
