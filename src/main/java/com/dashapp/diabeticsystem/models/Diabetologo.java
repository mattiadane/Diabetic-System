package com.dashapp.diabeticsystem.models;

import com.dashapp.diabeticsystem.Main;

import java.util.HashSet;

public class Diabetologo {

    private final int id_diabetologo = Session.getCurrentUser().getId_diabetologo();
    private  String nome;
    private  String cognome;

    private static final HashSet<Paziente> pazientes = new HashSet<>();


    public Diabetologo() {
        Main.getDbManager().selectQuery("SELECT nome,cognome FROM diabetologo WHERE id_diabetologo = ?",
                rs -> {
                    if (rs.next()){
                        nome = rs.getString("nome");
                        cognome = rs.getString("cognome");
                    }

                    return null;
        },id_diabetologo);
    }


    /**
     * Funzione che permette di inserire all'interno della tabella paziente il nuovo paziente che viene assegnato al dottore
     * @param paziente oggetto di tipo <code>Paziente</code> da inserire nella tabella
     * @return valore di tipo <code>boolean</code> per controllare che l'inserimento è andato a buon fine.
     */
    public boolean inserisciPaziente(Paziente paziente){
        boolean success = false;
        if(paziente == null)
            return success;

        // eseguo l'inserimento del nuovo paziente a database assegnandolo direttamente al diabetologo che ha assegnto il login
        int  last_id =  Main.getDbManager().insertAndGetGeneratedId(
                "INSERT INTO paziente(nome,cognome,email,codice_fiscale,data_nascita,id_diabetologo) VALUES (?,?,?,?,?,?)",
                paziente.getNome(),paziente.getCognome(),paziente.getEmail(),paziente.getCodiceFiscale(),paziente.getDataNascita(), id_diabetologo
                );

        // eseguo l'inserimento del nuovo utente, considerandolo come nuova utenza per il login
        if (last_id >  0){
            paziente.setId_paziente(last_id);
            pazientes.add(paziente);
            success =  Main.getDbManager().updateQuery(
                    "INSERT INTO login(id_paziente,id_diabetologo,username,password_hash) VALUES(?,?,?,?)",
                    paziente.getId_paziente(),null,createUsername(paziente.getNome(),paziente.getCognome()),
                    new PasswordGenerator(paziente.getNome(), paziente.getCognome()).generatePassword()
            );
        }

        return success;
    }


    /**
     * Funzione che permette di restituire tutti i pazienti associati al diabetologo
     * @return oggetto <code>HashSet</code> contenente tutti i paziente associati al diabetologo
     */
   public HashSet<Paziente> getPazientes() {
        return pazientes;
    }

    @Override
    public String toString() {
        return nome + " " + cognome;
    }

    /**
     * Funzione che permette di generare automaticamente lo username dell'utente
     * @return un tipo <code>String</code> per lo username dell'utente
     */
    private String createUsername(String nome , String cognome){
        return (nome + "." + cognome).replaceAll("\\s+", "").toLowerCase();
    }

}
