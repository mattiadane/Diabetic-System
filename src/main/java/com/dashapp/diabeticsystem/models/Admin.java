package com.dashapp.diabeticsystem.models;

import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.utility.CredentialsGenerator;
import com.dashapp.diabeticsystem.utility.Utility;

public class Admin {

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
                "INSERT INTO diabetologo(nome,cognome,email) VALUES (?,?,?)",
                diabetologo.getNome(),diabetologo.getCognome(),diabetologo.getEmail()
        );

        // eseguo l'inserimento del nuovo utente, considerandolo come nuova utenza per il login
        if (last_id >  0){
            diabetologo.setId_diabetologo(last_id);
            success =  Main.getDbManager().updateQuery(
                    "INSERT INTO login(id_paziente,id_diabetologo,username,password_hash) VALUES(?,?,?,?)",
                    null, diabetologo.getId_diabetologo(), new CredentialsGenerator(0, diabetologo.getId_diabetologo(), diabetologo.getNome(), diabetologo.getCognome()).createUsername(),
                    new CredentialsGenerator(0,diabetologo.getId_diabetologo(),diabetologo.getNome(), diabetologo.getCognome()).generatePassword()
            );
        }

        return success;
    }

}
