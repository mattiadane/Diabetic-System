package com.dashapp.diabeticsystem.models;

import com.dashapp.diabeticsystem.Main;

import java.util.HashSet;

public class Diabetologo {

    private final int id_diabetologo = Session.getCurrentUser().getId_diabetologo();
    private static final HashSet<Paziente> pazientes = new HashSet<>();

    public Diabetologo() {
        Main.getDbManager().selectQuery("SELECT nome,cognome,email,codice_fiscale,data_nascita",
               rs -> {
                    while (rs.next()) {
                        Paziente paziente = new Paziente(rs.getString("nome"),
                                rs.getString("cognome"),rs.getString("email"),rs.getString("codice_fiscale"),rs.getDate("data_nascita"));
                        pazientes.add(paziente);
                    }
                   return null;
               });
    }


    /**
     * Funzione che permette di inserire all'interno della tabella paziente il nuovo paziente che viene assegnato al dottore
     * @param paziente oggetto di tipo <code>Paziente</code> da inserire nella tabella
     * @return valore di tipo <code>boolean</code> per controllare che l'inserimento è andato a buon fine.
     */
    public boolean inserisciPaziente(Paziente paziente){
        if(paziente == null)
            return false;

        boolean success =  Main.getDbManager().updateQuery("INSERT INTO paziente(nome,cognome,email,codice_fiscale,data_nascita,id_diabetologo) VALUES (?,?,?,?,?,?)",
                paziente.getNome(),paziente.getCognome(),paziente.getEmail(),paziente.getCodiceFiscale(),paziente.getDataNascita(), id_diabetologo
                );

        if (success){
            pazientes.add(paziente);
        }

        return success;
    }


    public int getId_diabetologo(){
        return id_diabetologo;
    }

    public HashSet<Paziente> getPazientes() {
        return pazientes;
    }
}
