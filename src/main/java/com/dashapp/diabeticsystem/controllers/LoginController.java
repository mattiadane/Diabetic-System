package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.models.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;


public class LoginController {
    private Login user ;


    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;



    @FXML
    protected void onClickLogin(ActionEvent event) throws IOException {
        user = Login.autenticate(usernameField.getText(), passwordField.getText());
        usernameField.setText("");
        passwordField.setText("");
        if(user == null){
            System.out.println("Username or password are incorrect");
            return;
        }
        Object controller ;
        if(user.getId_paziente() != 0){
            controller = Main.getStage(new Stage(),"fxml/dashboardPaziente.fxml",null,"Dashboard Paziente");

        }else if (user.getId_diabetologo() != 0){
            controller = Main.getStage(new Stage(),"fxml/dashboardDiabetologo.fxml",null,"Dashboard Diabetologo");

        }
        else{
            controller = Main.getStage(new Stage(),"fxml/dashboardAdmin.fxml",null,"Dashboard Admin");

        }
        ((DashboardController)controller).initData(user);

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}