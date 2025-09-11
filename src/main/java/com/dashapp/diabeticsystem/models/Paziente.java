package com.dashapp.diabeticsystem.models;


import com.dashapp.diabeticsystem.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;

public class Paziente extends Persona {
    private Diabetologo diabetologo;
    private int id_paziente = Session.getCurrentUser().getId_paziente() ;
    private final LocalDate dataNascita;
    private InformazioniPaziente info;

    private ObservableList<Terapia> terapie = FXCollections.observableArrayList();
    private ObservableList<AssunzioneFarmaco> assunzioni = FXCollections.observableArrayList();

    public Paziente(String nome,String cognome,String email,String codiceFiscale,LocalDate dataNascita,String sesso,Diabetologo diabetologo,InformazioniPaziente info) {
        super(nome,cognome,email,codiceFiscale,sesso);
        this.dataNascita = dataNascita;
        this.diabetologo = diabetologo;
        this.info = info;

        assunzioni = loadAllAssunzioni();
    }
    public Paziente(int id_paziente, String nome,String cognome,String email,String codiceFiscale,LocalDate dataNascita,String sesso,Diabetologo diabetologo,InformazioniPaziente info) {
        this(nome, cognome, email, codiceFiscale, dataNascita,sesso,diabetologo,info);
        this.id_paziente = id_paziente;

        assunzioni = loadAllAssunzioni();
    }


    public Paziente(String nome,String cognome,String email,String codiceFiscale,LocalDate dataNascita,String sesso) {
        super(nome,cognome,email,codiceFiscale,sesso);
        this.dataNascita = dataNascita;

        assunzioni = loadAllAssunzioni();
    }

    public Paziente(int id_paziente, String nome,String cognome,String email,String codiceFiscale,LocalDate dataNascita,String sesso) {
        this(nome, cognome, email, codiceFiscale, dataNascita,sesso);
        this.id_paziente = id_paziente;

        assunzioni = loadAllAssunzioni();
    }



    public Diabetologo getDiabetologo() {
        return diabetologo;
    }


    public void setInfo(InformazioniPaziente info) {
        this.info = info;
    }

    public ObservableList<AssunzioneFarmaco> loadAllAssunzioni(){
        /*
        if(assunzioni.isEmpty()){
            Main.getDbManager().selectQuery("SELECT id_farmaco,dosaggio_quantità,dosaggio_unità,sintomi,data_assunzione FROM assunzione_farmaco WHERE id_paziente = ?",
                    rs -> {
                        while(rs.next()){

                            Farmaco f = Terapia.getFarmacoById(rs.getInt("id_farmaco"));

                            AssunzioneFarmaco af = new AssunzioneFarmaco(
                                f,rs.getString("dosaggio_unità"),rs.getDouble("dosaggio_quantità"),rs.getString("sintomi"),rs.getTimestamp("data_assunzione").toLocalDateTime()
                            );
                            assunzioni.add(af);
                        }
                        return null;

                    },this.id_paziente);

        }
        return assunzioni;
        */
         return null;
    }




    /**
     * Funzione per prendere la data di nascita del paziente
     * @return oggetto <code>Date</code> per la data di nascita del paziente.
     */
    public LocalDate getDataNascita() {
        return dataNascita;
    }

    /**
     * Funzione per prendere l'id del paziente
     * @return oggetto <code>int</code> per l'id del paziente.
     */
    public int getId_paziente() {
        return id_paziente;
    }

    /**
     * Funzione per settare l'id del paziente
     * @param id_paziente di da assegnare al paziente
     */
    public void setId_paziente(int id_paziente) {
        this.id_paziente = id_paziente;
    }








  

    public Diabetologo getMyDiabetologo(){
        return Main.getDbManager().selectQuery("SELECT d.id_diabetologo,d.nome,d.cognome,d.email,d.codice_fiscale,d.sesso FROM paziente p " +
                "INNER JOIN diabetologo d ON p.id_diabetologo = d.id_diabetologo WHERE id_paziente = ?",
                rs -> {
                    if(rs.next()){
                        return new Diabetologo(
                                rs.getInt("d.id_diabetologo"),rs.getString("nome"),rs.getString("d.cognome"),rs.getString("d.email"),rs.getString("d.codice_fiscale"),rs.getString("d.sesso")
                                );
                    }
                    return null;
                },id_paziente);

    }

    public InformazioniPaziente getInfo() {
        return info;
    }
}
