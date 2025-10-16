package com.dashapp.diabeticsystem.models;

public class Farmaco {
    private final int id_farmaco;
    private final String nome;
    private final String descrizione;

    public Farmaco(int id_farmaco, String nome, String descrizione) {
        this.id_farmaco = id_farmaco;
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public int getId_farmaco() {
        return id_farmaco;
    }

    public String getNome() {
        return nome;
    }

    public String toString() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }
}
