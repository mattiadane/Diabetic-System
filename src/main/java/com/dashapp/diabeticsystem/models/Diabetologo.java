package com.dashapp.diabeticsystem.models;

import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.utility.CredentialsGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Diabetologo extends Persona {

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
    }

    public Diabetologo(String nome, String cognome, String email,String codice_fiscale){
        super(nome,cognome,email,codice_fiscale);
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
        Main.getDbManager().selectQuery("SELECT nome,cognome,codice_fiscale,data_nascita,email FROM paziente WHERE id_diabetologo = ?",
               rs -> {
                    while (rs.next()){
                        System.out.println(rs.getString("nome"));
                        pazienti.add(
                                new Paziente(rs.getString("nome"),rs.getString("cognome"),rs.getString("email"),rs.getString("codice_fiscale"),rs.getDate("data_nascita").toLocalDate())
                        );
                    }
                    return null;
               } ,id_diabetologo);
        
        return pazienti;
    }

    /**
     * Funzione che permette di eseguire un query a database per aggiornare i dati del diabetologo
     * @param nome nuovo nome del diabetologo
     * @param cognome nuovo cognome del diabetologo
     * @param email nuova email del diabetologo
     * @param password nuova password del diabetologo
     * @return <code>true</code> se la query è andata a buon fine, <code>false</code> altrimenti
     */
    public boolean updateCredentials(String nome, String cognome, String email,String password){
        boolean success =  Main.getDbManager().updateQuery("UPDATE diabetologo SET nome = ? , cognome = ?, email = ? WHERE id_diabetologo = ?"
                ,nome,cognome,email,id_diabetologo);

        // se la prima query non è andata a buon fine, ritorno subito valore false
        if(!success) return false;

        setNome(nome);
        setCognome(cognome);
        setEmail(email);

        // ritorno il valore della query di aggiornamento per la password
        return Main.getDbManager().updateQuery("UPDATE login SET password_hash = ? WHERE id_diabetologo = ?",password,id_diabetologo);
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
