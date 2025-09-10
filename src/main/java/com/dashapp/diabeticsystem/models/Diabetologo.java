package com.dashapp.diabeticsystem.models;

import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.enums.GRAVITA;
import com.dashapp.diabeticsystem.utility.CredentialsGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Diabetologo extends Persona  {

    /**
     * Id del diabetologo che ha effettuato il login
     */
    private int id_diabetologo = Session.getCurrentUser().getId_diabetologo();

    /**
     * Lista di tipo <code>ObservableList</code> che contiene i pazienti associati al diabetologo
     */
    private ObservableList<Paziente> pazienti = FXCollections.observableArrayList();

    public Diabetologo() {


    }

    public Diabetologo(String nome, String cognome, String email,String codice_fiscale,String sesso){
        super(nome,cognome,email,codice_fiscale,sesso);
    }
    public Diabetologo(int id_diabetologo, String  nome, String cognome, String email,String codice_fiscale,String sesso){
        this(nome,cognome,email,codice_fiscale,sesso);
        this.id_diabetologo = id_diabetologo;
    }




    /**
     * Funzione che permette di modificare i dati di una terapia a database
     * @param terapia nuova terapia con i dati modificati della precedente
     * @return <code>true</code> se la query va a buon fine, <code>false</code> altrimenti,
     */
    public boolean updateTerapia(Terapia terapia){
        if(terapia == null) return false;

        return Main.getDbManager().updateQuery("""
                        UPDATE terapia SET
                        id_farmaco = ?,
                        dosaggio_quantità = ?,
                        dosaggio_unità = ?,
                        quanto = ?,
                        periodicità = ?,
                        data_inizio_terapia = ?,
                        data_fine_terapia = ?,
                        descrizione = ?
                        WHERE (id_terapia = ?)""",
        terapia.getFarmaco().getId_farmaco(), terapia.getDosaggio_quantita(), terapia.getDosaggio_unita(), terapia.getQuanto(), terapia.getPeriodicita().toString(), terapia.getData_inizio(), terapia.getData_fine(), terapia.getDescrizione(), terapia.getId_terapia()
                );
    }


    public Paziente getPazienteByCf(String codice_fiscale){

        for(Paziente paziente : pazienti){
            if(paziente.getCodice_fiscale().equals(codice_fiscale)){
                return paziente;
            }
        }
        return null;
    }




    /**
     * Funzione che permette di inserire una nuova terapia a database
     * @param terapia oggetto di tipo <code>Terapia</code> da inserire a database
     * @param p oggetto di tipo <code>Paziente</code> associato alla terapia
     * @param f oggetto di tipo <code>Farmaco</code> associato alla terapia
     * @return valore di tipo <code>boolean</code> per controllare che l'inserimento è andato a buon fine.
     */
    public boolean insersciTerapia(Terapia terapia,Paziente p,Farmaco f){
        if(terapia == null || p == null || f == null) return false;

        return Main.getDbManager().updateQuery("INSERT INTO terapia(id_paziente,id_diabetologo,id_farmaco,dosaggio_quantità" +
                ",dosaggio_unità,quanto,periodicità,data_inizio_terapia,data_fine_terapia,descrizione) VALUES (?,?,?,?,?,?,?,?,?,?)",
                p.getId_paziente(),id_diabetologo,f.getId_farmaco(),terapia.getDosaggio_quantita(),terapia.getDosaggio_unita(),
                terapia.getQuanto(),terapia.getPeriodicita().toString(),terapia.getData_inizio(),terapia.getData_fine(),terapia.getDescrizione()
                );
    }

    /**
     * Funzione che permette di rimuovere una terapia a database
     * @param t oggetto di tipo <code>Terapia</code> da rimuovere a database
     * @return valore di tipo <code>boolean</code> per controllare che la rimozione è andata a buon fine.
     */
    public boolean rimuoviTerapia(Terapia t){
        if(t == null) return false;

        return Main.getDbManager().updateQuery("DELETE FROM terapia WHERE id_terapia = ?",t.getId_terapia());
    }



    public List<Paziente> notifyPatient(){
        List<Paziente> pazienti = new  ArrayList<>();
        for(Paziente paziente : this.pazienti){
            int count = 0;
            for(Terapia t : paziente.getAllTerapie()){
                if(t.getData_inizio().isBefore(LocalDate.now().minusDays(2)) || t.getData_inizio().equals(LocalDate.now().minusDays(2))){
                    count++;
                }
            }
            if(count > 0){
                boolean check = (paziente.numberDailyTakingMedicine(LocalDate.now()) + paziente.numberDailyTakingMedicine(LocalDate.now().minusDays(1)) + paziente.numberDailyTakingMedicine(LocalDate.now().minusDays(2))) == 0;

                if(check)
                    pazienti.add(paziente);
            }
        }

        return pazienti;
    }


    public Map<Paziente,List<Insulina>> notifyBloodSugar(){
        Map<Paziente,List<Insulina>> pazienti = new HashMap<>();
        for(Paziente paziente : this.pazienti){
            ObservableList<Insulina> dailyBloodSugar = paziente.getInsulinaByDate(LocalDate.now().atStartOfDay(),LocalDate.now().atTime(LocalTime.MAX));
            if(!dailyBloodSugar.isEmpty()){
                List<Insulina> bloodSugar = new ArrayList<>();
                for(Insulina glicemia : dailyBloodSugar){
                    if(glicemia.getGravita() != GRAVITA.NORMALE){
                        bloodSugar.add(glicemia);
                    }
                }
                pazienti.put(paziente,bloodSugar);

            }

        }

        return pazienti;
    }

    public boolean modificaPaziente(Paziente p){
        if(p == null) return false;

        return Main.getDbManager().updateQuery("UPDATE paziente SET nome = ?,cognome = ?,codice_fiscale = ?," +
                "data_nascita = ?,email = ?,sesso = ? WHERE id_paziente = ?",p.getNome(),p.getCognome(),p.getCodice_fiscale(),p.getDataNascita(),p.getEmail(),p.getSesso(),p.getId_paziente());


    }





    /**
     * Funzione per settare l'id del paziente
     * @param id_diabetologo di da assegnare al paziente
     */
    public void setId_diabetologo(int id_diabetologo) {
        this.id_diabetologo = id_diabetologo;
    }

    public int getId_diabetologo() {
        return id_diabetologo;
    }

    public boolean updateInfo(Paziente p, InformazioniPaziente info){
        if(p == null) return false;

        return Main.getDbManager().updateQuery("UPDATE informazione_paziente SET fattori_rischio = ?, commorbità = ?, patologie_pregresse = ?, patologie_in_concomitanza = ? WHERE id_paziente = ?",
                info.getFattoriRischio(),info.getCommorbita(),info.getPatologiePreg(),info.getPatologieAtt(), p.getId_paziente());

    }
}
