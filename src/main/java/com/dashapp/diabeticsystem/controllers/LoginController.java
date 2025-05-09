package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.models.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;


public class LoginController {
    @FXML public Label usernameLabel;
    @FXML public Label passwordLabel;
    @FXML public Button confirmButton;
    @FXML private Login user ;

    // TextFields
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;



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
        if(user.getId_paziente() != 0 && user.getId_diabetologo() == 0) {
            controller = Main.getStage(new Stage(),"fxml/dashboards/dashboardPaziente.fxml","css/style.css","Dashboard Paziente");

        }else if (user.getId_diabetologo() != 0 && user.getId_paziente() == 0){
            controller = Main.getStage(new Stage(),"fxml/dashboards/dashboardDiabetologo.fxml","css/style.css","Dashboard Diabetologo");

        }
        else{
            controller = Main.getStage(new Stage(),"fxml/dashboards/dashboardAdmin.fxml","css/style.css","Dashboard Admin");

        }
        ((DashboardController)controller).initData(user);

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}