package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.controllers.dashboards.DashboardController;
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


    /**
     * Funzione che permette di avviare il procedimento di Login in base ai dati inseriti
     * dall'utente nel form.
     * @param event evento di click sul bottone di login
     * @throws IOException errore di input/output
     */
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
            controller = Main.getStage(new Stage(),"fxml/dashboards/dashboardPaziente.fxml","Dashboard Paziente");

        }else if (user.getId_diabetologo() != 0 && user.getId_paziente() == 0){
            controller = Main.getStage(new Stage(),"fxml/dashboards/dashboardDiabetologo.fxml","Dashboard Diabetologo");

        }
        else{
            controller = Main.getStage(new Stage(),"fxml/dashboards/dashboardAdmin.fxml","Dashboard Admin");

        }
        ((DashboardController)controller).initData(user);

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}