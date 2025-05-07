package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.models.Login;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    private Login user ;


    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;



    @FXML
    protected void onClickLogin()  {
        user = Login.autenticate(usernameField.getText(), passwordField.getText());
        System.out.println(user != null ? "benvenuto " + user: "password e username sbagliati");
        usernameField.setText("");
        passwordField.setText("");

    }
}