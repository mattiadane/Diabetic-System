package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.View.Router;
import com.dashapp.diabeticsystem.controllers.components.SidebarController;
import com.dashapp.diabeticsystem.models.Login;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class MainController {


    @FXML
    private SidebarController sidebarController; // Questo si collegherà automaticamente all'fx:controller della tua sidebar inclusa


    @FXML
    private VBox sidebar; // Il tipo deve essere VBox, non SidebarController



    @FXML
    private BorderPane mainContainer;

    private Login currentUser; // Per memorizzare l'utente loggato




    public void initialize() {
        Router.setMainController(this);
    }

    public void setContent(Node view) {
        mainContainer.setCenter(view);
    }

    public void setCurrentUser(Login currentUser) {
        this.currentUser = currentUser;
        sidebarController.setUser(currentUser);

    }
}
