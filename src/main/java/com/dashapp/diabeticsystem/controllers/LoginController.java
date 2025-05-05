package com.dashapp.diabeticsystem.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private Label welcomeText;


    @FXML
    private TextField testo;

    @FXML
    private Button confirmButton;



    @FXML
    protected void onHelloButtonClick() {
        if (testo.getText().isEmpty()) {  welcomeText.setText(""); return; }

        welcomeText.setText(testo.getText());

    }
}