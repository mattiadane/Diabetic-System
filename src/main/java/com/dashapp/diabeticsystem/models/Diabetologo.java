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
        Main.getDbManager().selectQuery("SELECT nome,cognome,email,sesso FROM diabetologo WHERE id_diabetologo = ?",
                rs -> {
                    if (rs.next()){

                        setNome(rs.getString("nome"));
                        setCognome(rs.getString("cognome"));
                        setEmail(rs.getString("email"));
                        setSesso(rs.getString("sesso"));
                    }
                    return null;
        },id_diabetologo);
        pazienti = loadAllPatients();
    }

    public Diabetologo(String nome, String cognome, String email,String codice_fiscale,String sesso){
        super(nome,cognome,email,codice_fiscale,sesso);
    }
    public Diabetologo(int id_diabetologo, String  nome, String cognome, String email,String codice_fiscale,String sesso){
        this(nome,cognome,email,codice_fiscale,sesso);
        this.id_diabetologo = id_diabetologo;

    }




    /**
     * Funzione che permette di aggiornare i dati del diabetolo a database.
     * @param p nuova persona per aggiornare i dati correnti.
     * @return <code>true</code> se la query va a buon fine, <code>false</code> altrimenti.
     */
    public boolean updatePersona(Persona p) {
        if(!(p instanceof Diabetologo)) return false;

        return Main.getDbManager().updateQuery("UPDATE diabetologo SET nome = ? , cognome = ?, email = ? WHERE id_diabetologo = ?",
                p.getNome(),p.getCognome(),p.getEmail(),id_diabetologo);

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

    /**
     * Funzione che permette di inserire all'interno della tabella 'paziente' il nuovo paziente che viene assegnato al dottore
     * @param paziente oggetto di tipo <code>Paziente</code> da inserire nella tabella
     * @return valore di tipo <code>boolean</code> per controllare che l'inserimento è andato a buon fine.
     */
    public boolean inserisciPaziente(Paziente paziente, InformazioniPaziente info){
        boolean success = false;
        if(paziente == null)
            return false;


        // eseguo l'inserimento del nuovo paziente a database assegnandolo direttamente al diabetologo che ha assegnto il login
        int  last_id =  Main.getDbManager().insertAndGetGeneratedId(
                "INSERT INTO paziente(nome,cognome,email,codice_fiscale,data_nascita,id_diabetologo) VALUES (?,?,?,?,?,?)",
                paziente.getNome(),paziente.getCognome(),paziente.getEmail(),paziente.getCodice_fiscale(),paziente.getDataNascita(), id_diabetologo
                );

        // eseguo l'inserimento del nuovo utente, considerandolo come nuova utenza per il login
        if (last_id >  0){
            paziente.setId_paziente(last_id);
            pazienti.add(paziente);
            success = Main.getDbManager().updateQuery(
                    "INSERT INTO login(id_paziente,id_diabetologo,username,password_hash) VALUES(?,?,?,?)",
                    paziente.getId_paziente(),null,new CredentialsGenerator(last_id,0,paziente.getNome(),paziente.getCognome()).createUsername(),
                    new CredentialsGenerator(last_id,0,paziente.getNome(), paziente.getCognome()).generatePassword()
            );
            if(success){
                success = Main.getDbManager().updateQuery(
                        "INSERT INTO informazione_paziente(id_paziente, fattori_rischio, commorbità, patologie_pregresse, patologie_in_concomitanza) VALUES (?, ?, ?, ?, ?)",
                        paziente.getId_paziente(), info.getFattoriRischio(), info.getCommorbita(), info.getPatologiePreg(), info.getPatologieAtt()
                );
            }


        }

        return success;
    }



    /**
     * Funzione che permette di caricare tutti i pazienti associati al diabetologo
     * @return oggetto <code>ObservableList</code> con tutti i pazienti associati
     */
    public ObservableList<Paziente> loadAllPatients() {
        pazienti.clear();
        Main.getDbManager().selectQuery("SELECT id_paziente,nome,cognome,codice_fiscale,data_nascita,email,sesso FROM paziente WHERE id_diabetologo = ?",
               rs -> {
                    while (rs.next()){

                        pazienti.add(
                                new Paziente(rs.getInt("id_paziente"),rs.getString("nome"),rs.getString("cognome"),rs.getString("email"),rs.getString("codice_fiscale"),rs.getDate("data_nascita").toLocalDate(),rs.getString("sesso"))
                        );
                    }
                    return null;
               } ,id_diabetologo);
        
        return pazienti;
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
     * Funzione che permette di cercare un paziente all'interno della lista dei pazienti associati al diabetologo.
     * @param search oggetto ti tipo <code>String</code> che contiene il codice fiscale del paziente da cercare.
     * @return oggetto di tipo <code>ObservableList</code> con i pazienti che hanno il codice fiscale che inizia con il parametro passato.
     */
    public ObservableList<Paziente> getPazientiResearch(String search){
        ObservableList<Paziente> pazientiResearch = FXCollections.observableArrayList();
        Main.getDbManager().selectQuery("SELECT id_paziente,nome,cognome,codice_fiscale,data_nascita,email,sesso FROM paziente WHERE codice_fiscale LIKE ? ",
                rs -> {
                    while (rs.next()){
                        pazientiResearch.add(
                          new Paziente(rs.getInt("id_paziente"),rs.getString("nome"),rs.getString("cognome"),rs.getString("email"),rs.getString("codice_fiscale"),rs.getDate("data_nascita").toLocalDate(),rs.getString("sesso"))

                        );
                    }
                    return null;
                },search + "%");
        return pazientiResearch;

    }
    public boolean rimuoviPaziente(Paziente paziente){
        boolean success =  Main.getDbManager().updateQuery("DELETE FROM paziente WHERE id_paziente = ?",paziente.getId_paziente());

        if(success)
            pazienti.remove(paziente);

        return success;
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
