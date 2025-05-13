package com.dashapp.diabeticsystem.controllers.dashboards;


import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.models.Diabetologo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class DashboardDiabetologoController extends DashboardController implements Initializable {

    private final Diabetologo diabetologo = new Diabetologo();

    // componenti effettivi della dashboard
    @FXML private Label benvenuto ;



    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(diabetologo);
        benvenuto.setText(diabetologo.toString());

    }


    /**
     * Funzione che permette di aprire una finestra per inserire nel database una nuova utenza per il paziente.
     */
    public void handleCreaPaziente() throws IOException {
        Main.getStage(new Stage(), "fxml/views/crea_paziente.fxml", "Crea Paziente");
    }



}
