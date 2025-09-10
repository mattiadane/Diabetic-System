package com.dashapp.diabeticsystem.models;

public class InformazioniPaziente {
    private int id_informazione;
    private String fattoriRischio;
    private String commorbita;
    private String patologiePreg;
    private String patologieAtt;

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
     * Imposta i fattori di rischio associati al paziente.
     *
     * @param fattoriRischio descrizione dei fattori di rischio; può essere una stringa vuota ma non deve essere null.
     */
    public void setFattoriRischio(String fattoriRischio){
        this.fattoriRischio = fattoriRischio;
    }

    /**
     * Imposta il valore relativo alle comorbidità del paziente.
     *
     * @param commorbita stringa che descrive le comorbidità del paziente; non può essere null,
     *                   ma può essere una stringa vuota.
     */
    public void setCommorbita(String commorbita){
        this.commorbita = commorbita;
    }

    /**
     * Imposta la lista delle patologie pregresse del paziente.
     *
     * @param patologiePreg la stringa che rappresenta le patologie pregresse da impostare;
     *                      non può essere null, ma può essere una stringa vuota.
     */
    public void setPatologiePreg(String patologiePreg){
        this.patologiePreg = patologiePreg;
    }

    /**
     * Imposta il valore del campo che rappresenta le patologie attive del paziente.
     *
     * @param patologieAtt stringa che indica la lista delle patologie attive del paziente.
     *                     Può essere vuota ma non deve essere null.
     */
    public void setPatologieAtt(String patologieAtt){
        this.patologieAtt = patologieAtt;
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
