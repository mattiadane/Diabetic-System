package com.dashapp.diabeticsystem.models;

public class InformazioniPaziente {
    private int id_informazione;
    private final String fattoriRischio;
    private final String commorbita;
    private final String patologiePreg;
    private final String patologieAtt;

    /**
     * Costruttore della classe InformazioniPaziente. Inizializza i campi con i valori specificati.
     * Lancia un'eccezione IllegalArgumentException se uno dei parametri è null.
     * @param fattoriRischio descrizione dei fattori di rischio associati al paziente; non può essere null, ma può essere una stringa vuota
     * @param commorbita descrizione delle eventuali comorbidità del paziente; non può essere null, ma può essere una stringa vuota
     * @param patologiePreg lista delle patologie pregresse del paziente; non può essere null, ma può essere una stringa vuota
     * @param patologieAtt lista delle patologie attive del paziente; non può essere null, ma può essere una stringa vuota
     */
    public InformazioniPaziente(String fattoriRischio, String commorbita, String patologiePreg, String patologieAtt) {
        if(fattoriRischio == null || commorbita == null || patologiePreg == null || patologieAtt == null)
            throw new IllegalArgumentException("I valori passati non possono esser null. Almeno devono essere stringhe vuote");

        this.fattoriRischio = fattoriRischio;
        this.commorbita = commorbita;
        this.patologieAtt = patologieAtt;
        this.patologiePreg = patologiePreg;
    }

    public InformazioniPaziente(String fattoriRischio, String commorbita, String patologiePreg, String patologieAtt,int id_informazione) {
        this(fattoriRischio, commorbita, patologiePreg, patologieAtt);
        this.id_informazione = id_informazione;
    }



    /**
     * Crea un oggetto di tipo <code>InformazioniPaziente</code> con tutti i valori impostati come stringhe vuote
     */
    public InformazioniPaziente(){
        this("", "", "", "");
    }


    /**
     * Recupera i fattori di rischio associati al paziente.
     *
     * @return una stringa che rappresenta i fattori di rischio del paziente.
     */
    public String getFattoriRischio(){
        return fattoriRischio;
    }

    /**
     * Recupera la descrizione delle comorbidità associate al paziente.
     *
     * @return una stringa che rappresenta le comorbidità del paziente.
     */
    public String getCommorbita(){
        return commorbita;
    }

    /**
     * Recupera la lista delle patologie pregresse del paziente.
     *
     * @return una stringa che rappresenta le patologie pregresse del paziente.
     */
    public String getPatologiePreg(){
        return patologiePreg;
    }

    /**
     * Recupera la lista delle patologie attive del paziente.
     *
     * @return una stringa che rappresenta le patologie attive del paziente.
     */
    public String getPatologieAtt(){
        return patologieAtt;
    }

    public void setId_informazione(int id_informazione) {
        this.id_informazione = id_informazione;
    }

    public int getId_informazione() {
        return id_informazione;
    }
}
