package com.dashapp.diabeticsystem.controllers.admin;


import com.dashapp.diabeticsystem.DAO.implementations.DiabetologoDaoImpl;
import com.dashapp.diabeticsystem.DAO.implementations.LoginDaoImpl;
import com.dashapp.diabeticsystem.models.Diabetologo;
import com.dashapp.diabeticsystem.models.Login;
import com.dashapp.diabeticsystem.utility.CredentialsGenerator;
import com.dashapp.diabeticsystem.utility.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

public class DashboardAdminController  {


    private final DiabetologoDaoImpl diabetologoDao = new DiabetologoDaoImpl();
    private final LoginDaoImpl loginDao = new LoginDaoImpl();

    @FXML BorderPane borderpane;

    @FXML
    private ToggleGroup gruppoSesso;

    @FXML private TextField textNome;
    @FXML private TextField textCognome;
    @FXML private TextField textCf;
    @FXML private TextField textEmail;


    /**
     * Funzione che permette di creare un nuovo diabetologo dai dati forniti dal form dell'Admin
     * 
     */
    public void createNewDiabetologo(){



        if( !Utility.isEmailValid(textEmail.getText()) || !Utility.checkObj(gruppoSesso.getSelectedToggle())
            || !Utility.checkOnlyLetters(textNome.getText()) || !Utility.checkOnlyLetters(textCognome.getText()) || !Utility.isCodiceFiscaleValid(textCf.getText())
        ){
            Utility.createAlert(Alert.AlertType.ERROR, "Controlla i dati inseriti");
            return;
        }

        String sesso =  (String) ((RadioButton)gruppoSesso.getSelectedToggle()).getUserData();

        boolean success = false;

        int id_diabetologo = diabetologoDao.insertDibetologist(
                new Diabetologo(
                        textNome.getText(),textCognome.getText(),textEmail.getText(),textCf.getText(),sesso
                )
        );

        if(id_diabetologo > 0){
            CredentialsGenerator c = new CredentialsGenerator(0,id_diabetologo,textNome.getText(),textCognome.getText());
            success = loginDao.insertLogin(
                    new Login(c.createUsername(),c.generatePassword(),null,id_diabetologo)
            );

        }

        if(!success) {
            Utility.createAlert(Alert.AlertType.ERROR, "Non è stato possibile creare un nuovo diabetologo");
            return;
        }
        Utility.createAlert(Alert.AlertType.INFORMATION, "Nuovo diabetologo creato\nIl nuovo diabetologo è stato creato con successo");
        Utility.resetField(borderpane);

    }


}
