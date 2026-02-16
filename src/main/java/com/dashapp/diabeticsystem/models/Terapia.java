package com.dashapp.diabeticsystem.models;

import com.dashapp.diabeticsystem.enums.PERIODICITA;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Terapia {

    private int id_terapia;
    private final int quanto;
    private final PERIODICITA periodicita;
    private final LocalDate data_inizio;
    private final LocalDate data_fine;
    private final double dosaggio_quantita;
    private final String dosaggio_unita;
    private final String descrizione;
    private Farmaco farmaco;
    private Paziente paziente;
    private Diabetologo diabetologo;

    public Terapia(int quanto,PERIODICITA periodicita,double dosaggio_quantita,String dosaggio_unita,LocalDate data_inizio,LocalDate data_fine,String descrizione) {
        this.quanto = quanto;
        this.periodicita = periodicita;
        this.dosaggio_quantita = dosaggio_quantita;
        this.dosaggio_unita = dosaggio_unita;
        this.data_inizio = data_inizio;
        this.data_fine = data_fine;
        this.descrizione = descrizione;

    }

    public Terapia(int quanto,PERIODICITA periodicita,double dosaggio_quantita,String dosaggio_unita,LocalDate data_inizio,LocalDate data_fine,String descrizione,Farmaco farmaco) {
        this(quanto,periodicita,dosaggio_quantita,dosaggio_unita,data_inizio,data_fine,descrizione);
        this.farmaco = farmaco;
    }

    public Terapia(int quanto,PERIODICITA periodicita,double dosaggio_quantita,String dosaggio_unita,LocalDate data_inizio,LocalDate data_fine,String descrizione,Farmaco farmaco,int id_terapia) {
        this(quanto,periodicita,dosaggio_quantita,dosaggio_unita,data_inizio,data_fine,descrizione,farmaco);
        this.id_terapia = id_terapia;

    }





    public Terapia(int quanto,PERIODICITA periodicita,double dosaggio_quantita,String dosaggio_unita,LocalDate data_inizio,LocalDate data_fine,String descrizione,Farmaco farmaco,Diabetologo diabetologo,Paziente paziente) {
        this(quanto,periodicita,dosaggio_quantita,dosaggio_unita,data_inizio,data_fine,descrizione,farmaco);
        this.diabetologo = diabetologo;
        this.paziente = paziente;

    }


    public Paziente getPaziente() {
        return paziente;
    }

    public Farmaco getFarmaco() {return farmaco; }

    public void setFarmaco(Farmaco farmaco) {
        this.farmaco = farmaco;
    }

    public double getDosaggio_quantita() {
        return dosaggio_quantita;
    }

    public String getDosaggio_unita() {
        return dosaggio_unita;
    }

    public String getDosaggio(){
        return dosaggio_quantita + dosaggio_unita;
    }

    public String getAssunzioni(){ return quanto + " al " + periodicita;}

    public String getPeriodo(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


        return  " Da " +  data_inizio.format(formatter) + " a " + data_fine.format(formatter);
    }

    public int getQuanto() {
        return quanto;
    }

    public LocalDate getData_fine() {
        return data_fine;
    }

    public LocalDate getData_inizio() {
        return data_inizio;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public PERIODICITA getPeriodicita() {
        return periodicita;
    }

    public int getId_terapia() {
        return id_terapia;
    }

    public String toStringForList(){
        return  descrizione + " " + getDosaggio() + " " +  getAssunzioni() +  " di " + this.farmaco + getPeriodo();
    }

    public Diabetologo getDiabetologo() {
        return diabetologo;
    }
}
