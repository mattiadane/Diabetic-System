package com.dashapp.diabeticsystem.controllers.dashboards;

import com.dashapp.diabeticsystem.controllers.SettingsController;
import com.dashapp.diabeticsystem.models.Diabetologo;
import com.dashapp.diabeticsystem.models.Paziente;
import com.dashapp.diabeticsystem.utility.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DashboardDiabetologoController {

    private final Diabetologo diabetologo = new Diabetologo();


    // componenti effettivi della dashboard
    @FXML private Pane borderPane;
    @FXML private Label benvenuto ;
    @FXML private TableView<Paziente> tabellaPazienti ;
    @FXML private TableColumn<Paziente,String> nomeColonna ;
    @FXML private TableColumn<Paziente, String> cognomeColonna ;
    @FXML private TableColumn<Paziente, LocalDate> nascitaColonna ;
    @FXML private TableColumn<Paziente,String> cfColonna ;
    @FXML private TableColumn<Paziente,String> mailColonna ;

    // componenti per il form per aggiungere un paziente
    @FXML private TextField textNome;
    @FXML private TextField textCognome;
    @FXML private TextField textEmail;
    @FXML private TextField textCodiceFiscale;
    @FXML private DatePicker dataNascitaPicker;

    private static TableCell<Paziente, LocalDate> call(TableColumn<Paziente, LocalDate> column) {
        return new TableCell<>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty); // Chiama il metodo del genitore
                if (empty || item == null) {
                    setText(null); // Se la cella è vuota o l'item è nullo, non mostrare testo
                } else {
                    setText(formatter.format(item)); // Altrimenti, formatta la data e imposta il testo
                }
            }
        };
    }


    public void initialize() {
        SettingsController.setPersona(diabetologo);
        benvenuto.setText("Bentornato/a " + diabetologo);
        tabellaPazienti.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        nomeColonna.setCellValueFactory(new PropertyValueFactory<>("nome")); // Usa "nome" per chiamare getNome()
        cognomeColonna.setCellValueFactory(new PropertyValueFactory<>("cognome")); // Usa "cognome" per chiamare getCognome()
        mailColonna.setCellValueFactory(new PropertyValueFactory<>("email")); // Usa "email" per chiamare getEmail()
        cfColonna.setCellValueFactory(new PropertyValueFactory<>("codice_fiscale")); // Usa "codiceFiscale" per chiamare getCodiceFiscale()

        // Per la data di nascita, colleghiamo a getDataNascita().
        nascitaColonna.setCellValueFactory(new PropertyValueFactory<>("dataNascita"));

        nascitaColonna.setCellFactory(DashboardDiabetologoController::call);

        tabellaPazienti.setItems(diabetologo.getAllPatients());


    }

    /**
     * Funzione per controllare l'evento della registrazione del nuovo paziente assegnato direttamente dal dottore.
     * 
     */
    public void handleNuovoPaziente(){
        // controllo della validità dei campi inseriti dal dottore
        if(!Utility.isEmailValid(this.textEmail.getText()) || !Utility.isCodiceFiscaleValid(this.textCodiceFiscale.getText())
            || !Utility.checkDateNascita(this.dataNascitaPicker.getValue())
            || !Utility.checkOnlyLetters(this.textNome.getText()) || !Utility.checkOnlyLetters(this.textCognome.getText())
        ){
            Utility.createAlert(Alert.AlertType.ERROR, "Uno o più dati inseriti non sono validi");
            return;
        }

        // eseguo la query di inserimento del nuovo paziente a database
        boolean success = diabetologo.inserisciPaziente(
                new Paziente(
                        textNome.getText(),textCognome.getText(),textEmail.getText(),textCodiceFiscale.getText(), dataNascitaPicker.getValue()
                )
        );

        // controllo dell'esito dell'inserimento del nuovo paziente a database e mostro un Alert dedicato
        if(!success){
            Utility.createAlert(Alert.AlertType.ERROR, "Errore nella creazione del paziente");
            return;
        }

        Utility.createAlert(Alert.AlertType.INFORMATION, "Nuovo paziente aggiunto ");
        Utility.resetField(borderPane);
    }
}
