package com.dashapp.diabeticsystem.models;


import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.enums.PERIODICITA;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class Paziente extends Persona implements UpdatePersona{
    private int id_paziente = Session.getCurrentUser().getId_paziente() ;
    private LocalDate dataNascita;


    private ObservableList<Terapia> terapie = FXCollections.observableArrayList();


    public Paziente(String nome,String cognome,String email,String codiceFiscale,LocalDate dataNascita) {
        super(nome,cognome,email,codiceFiscale);

        this.dataNascita = dataNascita;
    }

    public Paziente(int id_paziente, String nome,String cognome,String email,String codiceFiscale,LocalDate dataNascita) {
        this(nome, cognome, email, codiceFiscale, dataNascita);
        this.id_paziente = id_paziente;

    }


    public Paziente(){
        Main.getDbManager().selectQuery("SELECT nome,cognome,email FROM paziente WHERE id_paziente = ?",
                rs -> {
                    if (rs.next()){

                        setNome(rs.getString("nome"));
                        setCognome(rs.getString("cognome"));
                        setEmail(rs.getString("email"));
                    }
                    return null;
                },id_paziente);

    }

    @Override
    public boolean updatePersona(Persona p, String password) {
        if(!(p instanceof Paziente)) return false;

        boolean success =  Main.getDbManager().updateQuery("UPDATE paziente SET nome = ? , cognome = ?, email = ? WHERE id_paziente = ?"
                ,p.getNome(),p.getCognome(),p.getEmail(),id_paziente);

        // se la prima query non è andata a buon fine, ritorno subito valore false
        if(!success) return false;

        // ritorno il valore della query di aggiornamento per la password
        return Main.getDbManager().updateQuery("UPDATE login SET password_hash = ? WHERE id_paziente = ?",password,id_paziente);

    }

    public ObservableList<Terapia> loadAllTerapie() {
        if(terapie.isEmpty()){

            Main.getDbManager().selectQuery("SELECT f.nome,t.id_terapia,t.dosaggio_quantità,t.dosaggio_unità,t.quanto,t.periodicità,t.data_inizio_terapia,t.data_fine_terapia,t.descrizione FROM terapia t\n" +
                    "INNER JOIN farmaco f ON t.id_farmaco = f.id_farmaco WHERE id_paziente = ?; ",
                    rs -> {
                        while(rs.next()){
                            Terapia t = new Terapia(

                                    rs.getInt("t.quanto"),PERIODICITA.valueOf(rs.getString("t.periodicità").toUpperCase()),
                                    rs.getDouble("t.dosaggio_quantità"),rs.getString("t.dosaggio_unità"),rs.getDate("t.data_inizio_terapia").toLocalDate()
                                    ,rs.getDate("t.data_fine_terapia").toLocalDate(),rs.getString("t.descrizione")
                            );
                            t.setId_terapia(rs.getInt("t.id_terapia"));
                            t.setFarmaco(t.getFarmacoByName(rs.getString("f.nome")));
                            terapie.add(t);
                        }
                        return null;
                    }
                    ,id_paziente);

        }
        return terapie;
    }

    public void inserisciTerapia(Terapia t){
        terapie.add(t);
    }

    public void rimuoviTerapie(Terapia t) {

        terapie.remove(t);
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


    // TODO: inserire campo per query di effettiva assunzione medicina
    public boolean aggiungiLivelloInsulina(Insulina insulina) {
        return Main.getDbManager().updateQuery("INSERT INTO insulina(id_paziente,valore_glicemia,orario,periodo) VALUES(?,?,?,?)",
                id_paziente,insulina.getLivello_insulina(),insulina.getOrario(),insulina.getPeriodo().toString());
    }
}
