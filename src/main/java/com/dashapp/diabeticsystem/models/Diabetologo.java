package com.dashapp.diabeticsystem.models;

import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.utility.CredentialsGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Diabetologo {

    private int id_diabetologo = Session.getCurrentUser().getId_diabetologo();

    private ObservableList<Paziente> pazienti = FXCollections.observableArrayList();

    private String nome;
    private String cognome;
    private String email;




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

    public Diabetologo(String nome, String cognome, String email){
        if(nome == null || nome.isEmpty() || cognome == null || cognome.isEmpty() || email == null || email.isEmpty())
            throw new IllegalArgumentException("Il nome, il cognome e la email non posso essere null oppure stringhe vuote");

        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }



    /**
     * Funzione che permette di inserire all'interno della tabella paziente il nuovo paziente che viene assegnato al dottore
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
                paziente.getNome(),paziente.getCognome(),paziente.getEmail(),paziente.getCodiceFiscale(),paziente.getDataNascita(), id_diabetologo
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

    public ObservableList<Paziente> getAllPatients() {
       if(pazienti.isEmpty()){
           pazienti = loadAllPatients();
       }
       return pazienti;
    }

    private ObservableList<Paziente> loadAllPatients() {
        pazienti.clear();

        Main.getDbManager().selectQuery("SELECT nome,cognome,codice_fiscale,data_nascita,email FROM paziente WHERE id_diabetologo = ?",
               rs -> {
                    while (rs.next()){
                        pazienti.add(
                                new Paziente(rs.getString("nome"),rs.getString("cognome"),rs.getString("email"),rs.getString("codice_fiscale"),rs.getDate("data_nascita").toLocalDate())
                        );
                    }
                    return null;
               } ,id_diabetologo);
        
        return pazienti;
    }


    @Override
    public String toString() {
        return nome + " " + cognome;
    }

    public String getNome(){
       return this.nome;
    }

    public String getCognome(){
        return this.cognome;
    }

    public String getEmail(){
        return this.email;
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
