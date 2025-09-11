package com.dashapp.diabeticsystem.controllers.diabetologo;


import java.time.format.DateTimeFormatter;

import com.dashapp.diabeticsystem.DAO.implementations.InformazionPazienteDaoImpl;
import com.dashapp.diabeticsystem.DAO.implementations.InsulinaDaoImpl;
import com.dashapp.diabeticsystem.DAO.implementations.TerapiaDaoImpl;
import com.dashapp.diabeticsystem.DAO.interfaces.InformazionePazienteDao;
import com.dashapp.diabeticsystem.DAO.interfaces.InsulinaDao;
import com.dashapp.diabeticsystem.DAO.interfaces.TerapiaDao;
import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.models.*;
import com.dashapp.diabeticsystem.utility.Utility;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.chart.LineChart;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class DettagliPazienteController {

    private final InformazionePazienteDao informazionePazienteDao = new InformazionPazienteDaoImpl();
    private final TerapiaDao terapiaDao = new TerapiaDaoImpl();
    private final InsulinaDao insulinaDao = new InsulinaDaoImpl();
    private  Paziente paziente;
    private InformazioniPaziente informazioniPaziente;


    @FXML private TableView<Terapia> tabella_terapie;
    @FXML private TableColumn<Terapia, String> col_nome;
    @FXML private TableColumn<Terapia, String> col_dosaggio;
    @FXML private TableColumn<Terapia, String> col_assunzioni;
    @FXML private TableColumn<Terapia, String> col_periodo;

    @FXML private TableColumn<Terapia, Void> modifica;
    @FXML private TableColumn<Terapia, Void> elimina;

    @FXML private TextField textFattori;
    @FXML private TextField textCommorbita;
    @FXML private TextField textPatologiePreg;
    @FXML private TextField textPatologieAtt;

    @FXML private LineChart<String, Number> chart;
    @FXML private DatePicker settimana;

    public void loadTerapie(Paziente paziente) {
        this.paziente = paziente;
        tabella_terapie.setItems(terapiaDao.getAllTherapyByPatient(paziente));
        this.informazioniPaziente = paziente.getInfo();

    }


    public void initialize(){

        this.col_nome.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFarmaco().getNome()));

        this.col_dosaggio.setCellValueFactory(new PropertyValueFactory<>("dosaggio"));
        this.col_assunzioni.setCellValueFactory(new PropertyValueFactory<>("assunzioni"));
        this.col_periodo.setCellValueFactory(new PropertyValueFactory<>("periodo"));


        chart.setAnimated(false); // Disabilita le animazioni che potrebbero causare problemi
        chart.getXAxis().setTickLabelRotation(45); // Ruota le etichette per migliore leggibilità


        // Imposta le label degli assi
        chart.getXAxis().setLabel("Giorni");
        chart.getYAxis().setLabel("Valori (mg/dL)");


        Callback<TableColumn<Terapia, Void>, TableCell<Terapia, Void>> cellFactoryElimina = new Callback<>() {
            @Override
            public TableCell<Terapia, Void> call(final TableColumn<Terapia, Void> param) {
                return new TableCell<>() {

                    private final Button btn = new Button("Elimina");
                    {
                        btn.getStyleClass().add("btn-elimina");
                        btn.setOnAction(event -> {
                            Optional<ButtonType> result = Utility.createAlert(Alert.AlertType.CONFIRMATION,"Sei sicuro di voler rimuovere la terapia?");
                            if( result.isPresent() && result.get().getText().equals("Si")){
                                boolean success = terapiaDao.removeTherapy(getTableView().getItems().get(getIndex()).getId_terapia());
                                if(!success){
                                    return;
                                }
                                getTableView().getItems().remove(getIndex());

                            }

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }

                };
            }

        };

        Callback<TableColumn<Terapia, Void>, TableCell<Terapia, Void>> cellFactoryModifica = new Callback<>() {
            @Override
            public TableCell<Terapia, Void> call(final TableColumn<Terapia, Void> param) {
                return new TableCell<>() {

                    private final Button btn = new Button("Modifica");
                    {
                        btn.getStyleClass().add("btn-modifica");
                        btn.setOnAction(event ->
                            modificaSchedaTerapia(getTableView().getItems().get(getIndex()))
                        );

                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }

                };
            }

        };
        modifica.setCellFactory(cellFactoryModifica);
        elimina.setCellFactory(cellFactoryElimina);
    }

    public void setTextFields(Paziente p) {
        this.textFattori.setText(p.getInfo().getFattoriRischio());
        this.textCommorbita.setText(p.getInfo().getCommorbita());
        this.textPatologiePreg.setText(p.getInfo().getPatologiePreg());
        this.textPatologieAtt.setText(p.getInfo().getPatologieAtt());

    }

    /**
     * Funzione che permette di aprire la scheda del paziente selezionato.
     * @param terapia terapia da visualizzare
     */
    private void modificaSchedaTerapia(Terapia terapia){
        try{
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/modificaTerapia.fxml"));

            Parent root = loader.load();

            ModificaTerapiaController modificaTerapiaController = loader.getController();
            modificaTerapiaController.loadData(terapia);

            modificaTerapiaController.setDettagliPazienteController(this);



            Stage schedaTerapia = new Stage();
            Scene newScene = new Scene(root, 600, 700);


            URL cssUrl = Main.class.getResource("css/style.css");
            if(cssUrl != null){
                newScene.getStylesheets().add(cssUrl.toExternalForm());
            }

            schedaTerapia.setTitle("Scheda Terapia di: " + this.paziente.getNome() + " " + this.paziente.getCognome());
            schedaTerapia.setScene(newScene);
            schedaTerapia.initModality(Modality.NONE);
            schedaTerapia.show();
        }catch(IOException err){
            System.out.println("Errore: " + err.getMessage());
        }
    }

    /**
     * Funzione che permette di mostrare i livelli di insulina di una settimana di un determinato paziente
     */
    public void initChart(Paziente paziente,LocalDateTime inizio,LocalDateTime fine){
        chart.getData().clear();

        ObservableList<Insulina> data = insulinaDao.getInsulinaByDateAndByPatients(inizio, fine,paziente);
        if (data.isEmpty()) {
            Utility.createAlert(Alert.AlertType.WARNING, "Non ci sono registrazioni di insulina per questo paziente nella settimana selezionata");
            return;
        }

        // Crea una nuova serie solo se ci sono dati
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Glicemia");


        XYChart.Series<String, Number> seriesSogliaBassa = new XYChart.Series<>();
        seriesSogliaBassa.setName("Soglia Min prima dei pasti"); // Nome per la leggenda

        XYChart.Series<String, Number> seriesSogliaMedia = new XYChart.Series<>();
        seriesSogliaMedia.setName("Soglia Max prima dei pasti ");

        XYChart.Series<String, Number> seriesSogliaAlta = new XYChart.Series<>();
        seriesSogliaAlta.setName("Soglia max dopo due ore i pasti ");


        DateTimeFormatter xAxisFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        data.sort(Comparator.comparing(Insulina::getOrario));

        // per ogni dato presente, vado ad aggiungerlo alla serie da mostrare poi nel grafico
        for (Insulina reg : data) {
            String day = reg.getOrario().format(xAxisFormatter);
            int value = reg.getLivello_insulina();
            series.getData().add(new XYChart.Data<>(day, value));
            seriesSogliaBassa.getData().add(new XYChart.Data<>(day, 80));
            seriesSogliaMedia.getData().add(new XYChart.Data<>(day, 130));
            seriesSogliaAlta.getData().add(new XYChart.Data<>(day, 180));

        }

        chart.getData().add(seriesSogliaBassa);
        chart.getData().add(seriesSogliaMedia);
        chart.getData().add(seriesSogliaAlta);
        chart.getData().add(series);
    }

    public void handleSettimana() {
        LocalDate dayOfWeek = settimana.getValue();

        if(Utility.checkObj(dayOfWeek)){
            LocalDateTime firstDayOfWeek = LocalDateTime.of(dayOfWeek.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)), LocalTime.MIN);

            LocalDateTime lastDayOfWeek = LocalDateTime.of(dayOfWeek.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)), LocalTime.MAX).withNano(0);

            initChart(paziente,firstDayOfWeek,lastDayOfWeek);
        }
    }

    public void refreshTable() {
        tabella_terapie.setItems(terapiaDao.getAllTherapyByPatient(paziente));
    }

    /**
     * Funzione che permette di aggiornare i dati relativi alle informazioni aggiuntive sul paziente
     */
    public void handleUpdateInfo(){
        informazioniPaziente = new InformazioniPaziente(this.textFattori.getText(), this.textCommorbita.getText(), this.textPatologiePreg.getText(),this.textPatologieAtt.getText(),this.informazioniPaziente.getId_informazione());


        if(informazionePazienteDao.updateInformation(informazioniPaziente))
            Utility.createAlert(Alert.AlertType.INFORMATION,"Informazioni aggiornate con successo");
        else Utility.createAlert(Alert.AlertType.ERROR,"Errore durante l'aggiornamento delle informazioni");
    }
}
