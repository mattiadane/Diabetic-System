package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.controllers.dashboards.DashboardController;
import com.dashapp.diabeticsystem.models.Login;
import com.dashapp.diabeticsystem.models.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;


public class LoginController {

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
        String path = "",title = "Dashboard ";
        Session.setCurrentUser(Login.autenticate(usernameField.getText(), passwordField.getText()));
        user = Session.getCurrentUser();

        usernameField.setText("");
        passwordField.setText("");
        if(user == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Username o password errati.");
            alert.showAndWait();
            return;
        }

        if(user.getId_paziente() != 0 && user.getId_diabetologo() == 0) {
            path = "fxml/dashboardPaziente.fxml";
            title += "Paziente";
        }else if (user.getId_diabetologo() != 0 && user.getId_paziente() == 0){
            path = "fxml/dashboardDiabetologo.fxml";
            title += "Diabetologo";
        }
        else{
            path = "fxml/dashboardAdmin.fxml";
            title += "Admin";

        }
        Main.getStage(new Stage(),path,title);

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}