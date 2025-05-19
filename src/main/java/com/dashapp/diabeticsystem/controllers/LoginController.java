package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.view.Router;
import com.dashapp.diabeticsystem.models.Login;
import com.dashapp.diabeticsystem.models.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {


    // TextFields
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;


    /**
     * Funzione che permette di avviare il procedimento di Login in base ai dati inseriti
     * dall'utente nel form.
     *
     *
     */
    @FXML
    protected void onClickLogin()  {
        Login user = Login.autenticate(usernameField.getText(), passwordField.getText());
        Session.setCurrentUser(user);

        if(user == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Username o password errati.");
            alert.showAndWait();
            return;
        }


        Router.setAuthenticatedUser(user);


        Router.changeScene("mainView.fxml");

        Router.navigatetoDashboard();

    }
}