package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.models.DbManager;
import com.dashapp.diabeticsystem.models.Login;
import com.dashapp.diabeticsystem.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class LoginController {
    private static final Login log = new Login();


    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;



    @FXML
    protected void onHelloButtonClick()  {
        User u = log.getUser(usernameField.getText(), passwordField.getText());
        System.out.println(u != null ? "benvenuto " + u : "password e username sbagliati");
        usernameField.setText("");
        passwordField.setText("");

    }
}