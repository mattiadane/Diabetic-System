package com.dashapp.diabeticsystem.models;


import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.enums.PERIODICITA;
import com.dashapp.diabeticsystem.enums.PERIODO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class Paziente extends Persona implements UpdatePersona{
    private int id_paziente = Session.getCurrentUser().getId_paziente() ;
    private LocalDate dataNascita;
    private final ObservableList<Terapia> terapie = FXCollections.observableArrayList();
    private InformazioniPaziente info;


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
    public boolean updatePersona(Persona p) {
        if(!(p instanceof Paziente)) return false;

        return Main.getDbManager().updateQuery("UPDATE paziente SET nome = ? , cognome = ?, email = ? WHERE id_paziente = ?"
                ,p.getNome(),p.getCognome(),p.getEmail(),id_paziente);
    }


    public boolean updatePassword(String password) {
        return Main.getDbManager().updateQuery("UPDATE login SET password_hash = ? WHERE id_paziente  = ?",password,id_paziente);
    }

    public void updateTerapia(Terapia t){
        terapie.removeIf(tt -> tt.getId_terapia() == t.getId_terapia());
        terapie.add(t);
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
     * Funzione che restituisce tutte le terapie caricate nel paziente. Se non sono già caricate, tento di caricarle dal database.
     * @return <code>ObservableList</code> con le terapie del paziente, <code>null</code> altrimenti.
     */
    public ObservableList<Terapia> getAllTerapie() {
        if(terapie.isEmpty()) return loadAllTerapie();
        return this.terapie;
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

    /**
     * Funzione che permette di eseguire una chiamata a database per fare in modo di prendersi tutti i livelli di insulina da lui registrati
     * @param limit limite per il numero di registrazioni da prendere. <code>null</code> per non avere restrizioni, un valore <code>int</code> per mettere un limite
     * @return un oggetto <code>ObservableList<Insulina></code>
     */
    public ObservableList<Insulina> getInsulina(Integer limit,int option) {
        if(limit == null) limit = Integer.MAX_VALUE;

        ObservableList<Insulina> list = FXCollections.observableArrayList();

        String baseQuery = "SELECT * FROM (SELECT * FROM insulina WHERE id_paziente = ? ORDER BY id_glicemia DESC LIMIT " + limit  + " ) AS sub";

        if(option != 0){
            baseQuery += " WHERE Date(orario) = " + "'" + LocalDate.now() + "'";
        }



        baseQuery += " ORDER BY orario ASC";

        Main.getDbManager().selectQuery(baseQuery,
                rs -> {
                    while (rs.next()) {
                        list.add(
                                new Insulina(rs.getInt("valore_glicemia"), PERIODO.fromDescrizione(rs.getString("periodo")),rs.getTimestamp("orario").toLocalDateTime())
                        );
                    }
                    return null;
                },
                this.id_paziente
        );

        return list;
    }

    public ObservableList<Insulina> getInsulinaByDate(LocalDateTime inizio, LocalDateTime fine) {
        ObservableList<Insulina> list = FXCollections.observableArrayList();

        Main.getDbManager().selectQuery("SELECT * FROM insulina WHERE id_paziente = ? AND orario BETWEEN ? AND ?",
                rs -> {
                    while (rs.next()) {
                        list.add(
                                new Insulina(rs.getInt("valore_glicemia"), PERIODO.fromDescrizione(rs.getString("periodo")),rs.getTimestamp("orario").toLocalDateTime())
                        );
                    }
                    return null;
                },
                this.id_paziente,inizio,fine
        );

        return list;
    }

    public int countInsulinaGiornaliero(){
        return getInsulina(null,1).size();
    }

    /**
     * Recupera le informazioni del paziente. Se le informazioni non sono già caricate,
     * recupera i dati dal database e inizializza l'oggetto contenente le informazioni del paziente.
     *
     * @return un'istanza di {@code InformazioniPaziente} contenente le informazioni del paziente.
     */
    public InformazioniPaziente getInfo(){
        if(info != null) return this.info;

        this.info = new InformazioniPaziente();
        Main.getDbManager().selectQuery("SELECT fattori_rischio, commorbità, patologie_pregresse, patologie_in_concomitanza FROM informazione_paziente WHERE id_paziente = ?;",
                rs -> {
                    if(rs.next()){
                        this.info.setFattoriRischio(rs.getString("fattori_rischio"));
                        this.info.setCommorbita(rs.getString("commorbità"));
                        this.info.setPatologiePreg(rs.getString("patologie_pregresse"));
                        this.info.setPatologieAtt(rs.getString("patologie_in_concomitanza"));
                    }
                    return null;
                },
                this.id_paziente);

        return this.info;
    }

}
