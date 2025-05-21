package com.dashapp.diabeticsystem.models;

import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.enums.PERIODICITA;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class Terapia {


    private int quanto;
    private PERIODICITA periodicita;
    private LocalDate data_inizio;
    private LocalDate data_fine;
    private double dosaggio_quantita;
    private String dosaggio_unita;
    private String descrizione;

    private static final ObservableList<Farmaco> farmaci = FXCollections.observableArrayList();



    public Terapia(int quanto,PERIODICITA periodicita,double dosaggio_quantita,String dosaggio_unita,LocalDate data_inizio,LocalDate data_fine,String descrizione) {
        this.quanto = quanto;
        this.periodicita = periodicita;
        this.dosaggio_quantita = dosaggio_quantita;
        this.dosaggio_unita = dosaggio_unita;
        this.data_inizio = data_inizio;
        this.data_fine = data_fine;
        this.descrizione = descrizione;

    }

    public static ObservableList<Farmaco> getAllDrug() {
        if(farmaci.isEmpty()){
            Main.getDbManager().selectQuery("SELECT * FROM farmaco",
                    rs -> {
                        while (rs.next()) {
                            farmaci.add(new Farmaco(rs.getInt("id_farmaco"),rs.getString("nome"),rs.getString("descrizione")));
                        }
                        return null;
                    });
        }
        return farmaci;
    }

    public Farmaco getFarmacoByName(String name) {
        for (Farmaco farmaco : farmaci) {
            if(farmaco.toString().equals(name)){
                return farmaco;
            }
        }
        return null;
    }




}
