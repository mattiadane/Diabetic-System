package com.dashapp.diabeticsystem.models;

public class PatologiaPregressa {
    private final String nome;


    public PatologiaPregressa(String nome) {
        this.nome = nome;
    }



    public String getNome() {
        return nome;
    }


    @Override
    public String toString() {
        return nome;
    }
}
