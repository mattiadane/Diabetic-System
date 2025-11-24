package com.dashapp.diabeticsystem.models;

public class Comorbità {
    private final String nome;


    public Comorbità(String nome) {
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
