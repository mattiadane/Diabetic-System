package com.dashapp.diabeticsystem.models;

public class FattoreRischio {
    private final String nome;


    public FattoreRischio(String nome) {
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
