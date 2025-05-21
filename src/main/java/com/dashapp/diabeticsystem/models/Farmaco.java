package com.dashapp.diabeticsystem.models;

public class Farmaco {
    private final int id_farmaco;
    private final String nome;
    private final String descriziome;

    public Farmaco(int id_farmaco, String nome, String descriziome) {
        this.id_farmaco = id_farmaco;
        this.nome = nome;
        this.descriziome = descriziome;
    }

    public int getId_farmaco() {
        return id_farmaco;
    }


    public String toString() {
        return nome;
    }

    public String getDescriziome() {
        return descriziome;
    }
}
