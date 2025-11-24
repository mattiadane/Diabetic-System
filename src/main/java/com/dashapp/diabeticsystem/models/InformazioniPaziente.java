package com.dashapp.diabeticsystem.models;

public class InformazioniPaziente {
    private final Paziente paziente;
    private final FattoreRischio fattoriRischio;
    private final Comorbità commorbita;
    private final PatologiaPregressa patologiePreg;

    /**
     * Costruttore della classe InformazioniPaziente. Inizializza i campi con i valori specificati.
     * Lancia un'eccezione IllegalArgumentException se uno dei parametri è null.
     * @param paziente a cui sono stati associate le informazioni
     * @param fattoriRischio fattore di rischio associato al paziente
     * @param commorbita  comorbidità del paziente
     * @param patologiePreg  patologia pregressa  del paziente;
     */
    public InformazioniPaziente( Paziente paziente ,FattoreRischio fattoriRischio, Comorbità commorbita, PatologiaPregressa patologiePreg) {
        this.paziente = paziente;
        this.fattoriRischio = fattoriRischio;
        this.commorbita = commorbita;
        this.patologiePreg = patologiePreg;
    }


    /**
     * Funzion che ritorno il paziente
     * @return <code>Paziente</code> che verrà restituito
     */
    public Paziente getPaziente() {
        return paziente;
    }

    /**
     * Recupera il fattore di rischio del paziente
     *
     * @return <code>FattoreRischio/code> del paziente
     */
    public FattoreRischio getFattoriRischio(){
        return fattoriRischio;
    }

    /**
     * Recupera la Comorbità del paziente
     *
     * @return <code>Comorbità</code> paziente.
     */
    public Comorbità getCommorbita(){
        return commorbita;
    }

    /**
     * Recupera  patologia pregresse del paziente.
     *
     * @return <code>PatologiaPregressa</code> del paziente
     */
    public PatologiaPregressa getPatologiePreg(){
        return patologiePreg;
    }

}
