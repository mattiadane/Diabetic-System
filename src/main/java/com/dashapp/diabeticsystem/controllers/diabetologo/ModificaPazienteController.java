package com.dashapp.diabeticsystem.controllers.diabetologo;

import com.dashapp.diabeticsystem.models.Diabetologo;
import com.dashapp.diabeticsystem.models.Paziente;
import com.dashapp.diabeticsystem.utility.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ModificaPazienteController {


    @FXML
    private ToggleGroup gruppoSesso;
    @FXML
    private RadioButton maschio;
    @FXML
    private RadioButton femmina;


    // componenti per il form per aggiungere un paziente
    @FXML private TextField textNome;
    @FXML private TextField textCognome;
    @FXML private TextField textEmail;
    @FXML private TextField textCodiceFiscale;
    @FXML private DatePicker dataNascitaPicker;

    private Paziente paziente;

    private final Diabetologo diabetologo = new Diabetologo();

    private ListaPazientiController listaPazientiController ;


    public void setPaziente(Paziente paziente) {
        this.paziente = paziente;
        textNome.setText(paziente.getNome());
        textCognome.setText(paziente.getCognome());
        textEmail.setText(paziente.getEmail());
        textCodiceFiscale.setText(paziente.getCodice_fiscale());
        dataNascitaPicker.setValue(paziente.getDataNascita());
        if("M".equals(paziente.getSesso())){
            maschio.setSelected(true);
        } else {
            femmina.setSelected(true);
        }
    }



    public void handleModificaPaziente(){
        if(!Utility.isEmailValid(this.textEmail.getText()) || !Utility.isCodiceFiscaleValid(this.textCodiceFiscale.getText()) || !Utility.checkObj(gruppoSesso.getSelectedToggle())
                || !Utility.checkDateNascita(this.dataNascitaPicker.getValue())
                || !Utility.checkOnlyLetters(this.textNome.getText()) || !Utility.checkOnlyLetters(this.textCognome.getText())
        ){
            Utility.createAlert(Alert.AlertType.ERROR, "Uno o più dati inseriti non sono validi");
            return;
        }

        String sesso =  (String) ((RadioButton)gruppoSesso.getSelectedToggle()).getUserData();

        this.paziente = new Paziente(this.paziente.getId_paziente(),textNome.getText(),textCognome.getText(),textEmail.getText(),textCodiceFiscale.getText(),dataNascitaPicker.getValue(),sesso);

        boolean success = diabetologo.modificaPaziente(this.paziente);

        if(!success){
            Utility.createAlert(Alert.AlertType.ERROR, "Errore durante la modifica dei dati del paziente");
            return;
        }

        if(listaPazientiController!=null)
            listaPazientiController.aggiornaTabella();

        Utility.createAlert(Alert.AlertType.INFORMATION,"Dati del paziente modificati correttamente");
        setPaziente(this.paziente);



    }

    public void setController(ListaPazientiController listaPazientiController) {
        this.listaPazientiController = listaPazientiController;
    }
}
