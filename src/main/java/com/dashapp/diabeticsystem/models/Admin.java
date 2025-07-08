package com.dashapp.diabeticsystem.models;

import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.utility.CredentialsGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Admin {


    private ObservableList<Diabetologo> diabetologi = FXCollections.observableArrayList();


    public Admin() {
        diabetologi = getAllDiabetologi();
    }

    private ObservableList<Diabetologo> getAllDiabetologi() {
        if(diabetologi.isEmpty()) {
            Main.getDbManager().selectQuery("SELECT id_diabetologo,nome,cognome,codice_fiscale,email,sesso FROM diabetologo",
                    rs -> {
                        while (rs.next()) {
                            diabetologi.add(
                                    new Diabetologo(rs.getInt("id_diabetologo"),rs.getString("nome"),rs.getString("cognome"),rs.getString("email"),rs.getString("codice_fiscale"),rs.getString("sesso"))
                            );
                        }
                        return null;
                    }

                    );
        }
        return diabetologi;
    }

    /**
     * Funzione che permette di inserire all'interno della tabella paziente il nuovo paziente che viene assegnato al dottore
     * @param diabetologo oggetto di tipo <code>Paziente</code> da inserire nella tabella
     * @return valore di tipo <code>boolean</code> per controllare che l'inserimento è andato a buon fine.
     */
    public boolean inserisciDiabetologo(Diabetologo diabetologo){
        boolean success = false;
        if(diabetologo == null)
            return false;

        // eseguo l'inserimento del nuovo diabetologo a database assegnandolo direttamente al diabetologo che ha assegnto il login
        int  last_id =  Main.getDbManager().insertAndGetGeneratedId(
                "INSERT INTO diabetologo(nome,cognome,codice_fiscale,email,sesso) VALUES (?,?,?,?,?)",
                diabetologo.getNome(),diabetologo.getCognome(),diabetologo.getCodice_fiscale(),diabetologo.getEmail(),diabetologo.getSesso()
        );

        // eseguo l'inserimento del nuovo utente, considerandolo come nuova utenza per il login
        if (last_id >  0){
            diabetologo.setId_diabetologo(last_id);
            success =  Main.getDbManager().updateQuery(
                    "INSERT INTO login(id_paziente,id_diabetologo,username,password_hash) VALUES(?,?,?,?)",
                    null, diabetologo.getId_diabetologo(), new CredentialsGenerator(0, diabetologo.getId_diabetologo(), diabetologo.getNome(), diabetologo.getCognome()).createUsername(),
                    new CredentialsGenerator(0,diabetologo.getId_diabetologo(),diabetologo.getNome(), diabetologo.getCognome()).generatePassword()
            );
            if(success){
                diabetologi.add(diabetologo);
            }
        }

        return success;
    }

    public boolean rimuoviDiabetologo(Diabetologo diabetologo){
        if(diabetologo == null) return false;

        boolean success =  Main.getDbManager().updateQuery("DELETE FROM diabetologo WHERE id_diabetologo = ?",diabetologo.getId_diabetologo());

        if(success)
            diabetologi.remove(diabetologo);

        return success;
    }

    public ObservableList<Diabetologo> getDiabetologi() {
        return diabetologi;
    }

}
