package com.dashapp.diabeticsystem.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;


    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button confirmButton;



    @FXML
    protected void onClickLogin() {
        System.out.println("Login Button Clicked");
        System.out.println("First Login Attempt...");
    }
}