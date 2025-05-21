package com.dashapp.diabeticsystem.models;


import com.dashapp.diabeticsystem.Main;
import java.time.LocalDate;

public class Paziente extends Persona implements UpdatePersona{
    private int id_paziente = Session.getCurrentUser().getId_paziente() ;
    private LocalDate dataNascita;

    public Paziente(String nome,String cognome,String email,String codiceFiscale,LocalDate dataNascita) {
        super(nome,cognome,email,codiceFiscale);

        this.dataNascita = dataNascita;
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


    public boolean aggiungiLivelloInsulina(Insulina insulina) {
        return Main.getDbManager().updateQuery("INSERT INTO insulina(id_paziente,valore_glicemia,orario,periodo) VALUES(?,?,?,?)",
                id_paziente,insulina.getLivello_insulina(),insulina.getOrario(),insulina.getPeriodo().toString());
    }
}
