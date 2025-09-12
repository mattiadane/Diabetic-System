package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.DAO.implementations.LoginDaoImpl;
import com.dashapp.diabeticsystem.utility.Utility;
import com.dashapp.diabeticsystem.view.Router;
import com.dashapp.diabeticsystem.models.Login;
import com.dashapp.diabeticsystem.models.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    private final LoginDaoImpl  loginDao = new LoginDaoImpl();

    // TextFields
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;


    /**
     * Funzione che permette di avviare il procedimento di Login in base ai dati inseriti
     * dall'utente nel form.
     */
    @FXML
    protected void onClickLogin()  {


        Login user = loginDao.autenticate(usernameField.getText(), passwordField.getText());

        if(user == null){
            Utility.createAlert(Alert.AlertType.ERROR, "Username o password errati.");
            return;
        }

        Session.setCurrentUser(user);
        Router.setAuthenticatedUser(user);
        Router.changeScene("mainView.fxml");
        Router.navigatetoDashboard();

    }
}