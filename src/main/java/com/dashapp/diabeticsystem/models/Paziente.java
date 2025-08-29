package com.dashapp.diabeticsystem.models;


import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.enums.PERIODICITA;
import com.dashapp.diabeticsystem.enums.PERIODO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Paziente extends Persona implements UpdatePersona{
    private int id_paziente = Session.getCurrentUser().getId_paziente() ;
    private LocalDate dataNascita;
    private InformazioniPaziente info;

    private ObservableList<Terapia> terapie = FXCollections.observableArrayList();
    private ObservableList<AssunzioneFarmaco> assunzioni = FXCollections.observableArrayList();

    public Paziente(String nome,String cognome,String email,String codiceFiscale,LocalDate dataNascita,String sesso) {
        super(nome,cognome,email,codiceFiscale,sesso);
        this.dataNascita = dataNascita;
        terapie = loadAllTerapie();
        assunzioni = loadAllAssunzioni();
    }

    public Paziente(int id_paziente, String nome,String cognome,String email,String codiceFiscale,LocalDate dataNascita,String sesso) {
        this(nome, cognome, email, codiceFiscale, dataNascita,sesso);
        this.id_paziente = id_paziente;
        terapie = loadAllTerapie();
        assunzioni = loadAllAssunzioni();
    }


    public Paziente(){
        Main.getDbManager().selectQuery("SELECT nome,cognome,email,sesso FROM paziente WHERE id_paziente = ?",
                rs -> {
                    if (rs.next()){

                        setNome(rs.getString("nome"));
                        setCognome(rs.getString("cognome"));
                        setEmail(rs.getString("email"));
                        setSesso(rs.getString("sesso"));
                    }
                    return null;
                },id_paziente);
        terapie = loadAllTerapie();
        assunzioni = loadAllAssunzioni();

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

    public ObservableList<AssunzioneFarmaco> loadAllAssunzioni(){
        if(assunzioni.isEmpty()){
            Main.getDbManager().selectQuery("SELECT id_farmaco,dosaggio_quantità,dosaggio_unità,sintomi,data_assunzione FROM assunzione_farmaco WHERE id_paziente = ?",
                    rs -> {
                        while(rs.next()){

                            Farmaco f = Terapia.getFarmacoById(rs.getInt("id_farmaco"));

                            AssunzioneFarmaco af = new AssunzioneFarmaco(
                                f,rs.getString("dosaggio_unità"),rs.getDouble("dosaggio_quantità"),rs.getString("sintomi"),rs.getTimestamp("data_assunzione").toLocalDateTime()
                            );
                            assunzioni.add(af);
                        }
                        return null;

                    },this.id_paziente);

        }
        return assunzioni;
    }

    public ObservableList<Farmaco> loadFarmaciByPaziente() {
        ObservableList<Farmaco> farmaci = FXCollections.observableArrayList();
        for(Terapia t : terapie) {
            if(!farmaci.contains(t.getFarmaco())) farmaci.add(t.getFarmaco());
        }
        return farmaci;
    }

    public void inserisciTerapia(Terapia t) { terapie.add(t);}

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

    public boolean inserisciAssunzioneFarmaco (AssunzioneFarmaco assunzioneFarmaco){
        if(assunzioneFarmaco == null) return false;

        boolean success =  Main.getDbManager().updateQuery("INSERT INTO assunzione_farmaco(id_paziente,id_farmaco,dosaggio_quantità,dosaggio_unità,data_assunzione,sintomi) " +
                "VALUES(?,?,?,?,?,?)",id_paziente,assunzioneFarmaco.getFarmaco().getId_farmaco(),assunzioneFarmaco.getDosaggio_quantita(),assunzioneFarmaco.getDosaggio_unita(),assunzioneFarmaco.getData_assunzione(),assunzioneFarmaco.getSintomi());
        if(success){
            assunzioni.add(assunzioneFarmaco);

        }
        return success;
    }

    public Terapia loadTeriapiaByFarmaco(Farmaco f){
        if(f == null) return null;

        return terapie.stream().filter(tf -> tf.getFarmaco().getId_farmaco() == f.getId_farmaco()).findFirst().orElse(null);

    }

    public double sommaDosaggioTerapia(Farmaco f){

        Terapia t = loadTeriapiaByFarmaco(f);

        return t.getQuanto() * t.getDosaggio_quantita();


    }

    public double sommaDosaggioAssunzioneFarmaco(Farmaco f,LocalDateTime date){
        Terapia t = loadTeriapiaByFarmaco(f);

        LocalDateTime inizio = null ,fine = null ;
        String query = "SELECT SUM(dosaggio_quantità) AS sum FROM assunzione_farmaco WHERE id_paziente = ? AND id_farmaco = ? AND data_assunzione BETWEEN ? AND ?";
        if(t.getPeriodicita().toString().equals("giorno")){
            inizio = date.toLocalDate().atStartOfDay();
            fine = date.toLocalDate().atTime(LocalTime.MAX);
        }

        return Main.getDbManager().selectQuery(query,
                rs -> {
                    if(rs.next()){
                        return rs.getDouble("sum");

                    }
                    return null;
                },id_paziente,t.getFarmaco().getId_farmaco(),inizio,fine);

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

    public Diabetologo getMyDiabetologo(){
        return Main.getDbManager().selectQuery("SELECT d.id_diabetologo,d.nome,d.cognome,d.email,d.codice_fiscale,d.sesso FROM paziente p " +
                "INNER JOIN diabetologo d ON p.id_diabetologo = d.id_diabetologo WHERE id_paziente = ?",
                rs -> {
                    if(rs.next()){
                        return new Diabetologo(
                                rs.getInt("d.id_diabetologo"),rs.getString("nome"),rs.getString("d.cognome"),rs.getString("d.email"),rs.getString("d.codice_fiscale"),rs.getString("d.sesso")
                                );
                    }
                    return null;
                },id_paziente);

    }
    public int numberDailyTakingMedicine(LocalDate data){

        int count = 0;

        for (AssunzioneFarmaco as : assunzioni ){
            LocalDate as_date_without_time = LocalDate.from(as.getData_assunzione());
            if(as_date_without_time.isEqual(data)){
                count++;
            }
        }

        return count;
    }

}
