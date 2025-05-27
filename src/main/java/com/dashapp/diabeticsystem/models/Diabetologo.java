package com.dashapp.diabeticsystem.models;

import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.utility.CredentialsGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Diabetologo extends Persona implements UpdatePersona {

    /**
     * Id del diabetologo che ha effettuato il login
     */
    private int id_diabetologo = Session.getCurrentUser().getId_diabetologo();

    /**
     * Lista di tipo <code>ObservableList</code> che contiene i pazienti associati al diabetologo
     */
    private ObservableList<Paziente> pazienti = FXCollections.observableArrayList();

    public Diabetologo() {
        Main.getDbManager().selectQuery("SELECT nome,cognome,email FROM diabetologo WHERE id_diabetologo = ?",
                rs -> {
                    if (rs.next()){

                        setNome(rs.getString("nome"));
                        setCognome(rs.getString("cognome"));
                        setEmail(rs.getString("email"));
                    }
                    return null;
        },id_diabetologo);
        pazienti = loadAllPatients();
    }

    public Diabetologo(String nome, String cognome, String email,String codice_fiscale){
        super(nome,cognome,email,codice_fiscale);
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
     * Funzione che permette di aggiornare la password del diabetologo
     * @param password nuova password da inserire a database
     * @return <code>true</code> se la query va a buon fine, <code>false</code> altrimenti.
     */
    @Override
    public boolean updatePassword(String password) {
        return Main.getDbManager().updateQuery("UPDATE login SET password_hash = ? WHERE id_diabetologo  = ?",password,id_diabetologo);
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
    public boolean inserisciPaziente(Paziente paziente){
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
            success =  Main.getDbManager().updateQuery(
                    "INSERT INTO login(id_paziente,id_diabetologo,username,password_hash) VALUES(?,?,?,?)",
                    paziente.getId_paziente(),null,new CredentialsGenerator(last_id,0,paziente.getNome(),paziente.getCognome()).createUsername(),
                    new CredentialsGenerator(last_id,0,paziente.getNome(), paziente.getCognome()).generatePassword()
            );
        }

        return success;
    }

    /**
     * Funzione che permette di avere tutti i pazienti associati al diabetologo
     * @return oggetto <code>ObservableList</code> con tutti i pazienti associati
     */
    public ObservableList<Paziente> getAllPatients() {
       if(pazienti.isEmpty()){
           pazienti = loadAllPatients();
       }
       return pazienti;
    }



    /**
     * Funzione che permette di caricare tutti i pazienti associati al diabetologo
     * @return oggetto <code>ObservableList</code> con tutti i pazienti associati
     */
    private ObservableList<Paziente> loadAllPatients() {
        pazienti.clear();
        Main.getDbManager().selectQuery("SELECT id_paziente,nome,cognome,codice_fiscale,data_nascita,email FROM paziente WHERE id_diabetologo = ?",
               rs -> {
                    while (rs.next()){

                        pazienti.add(
                                new Paziente(rs.getInt("id_paziente"),rs.getString("nome"),rs.getString("cognome"),rs.getString("email"),rs.getString("codice_fiscale"),rs.getDate("data_nascita").toLocalDate())
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
        Main.getDbManager().selectQuery("SELECT id_paziente,nome,cognome,codice_fiscale,data_nascita,email FROM paziente WHERE codice_fiscale LIKE ? ",
                rs -> {
                    while (rs.next()){
                        pazientiResearch.add(
                          new Paziente(rs.getInt("id_paziente"),rs.getString("nome"),rs.getString("cognome"),rs.getString("email"),rs.getString("codice_fiscale"),rs.getDate("data_nascita").toLocalDate())

                        );
                    }
                    return null;
                },search + "%");
        return pazientiResearch;

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


}
