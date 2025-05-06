package com.dashapp.diabeticsystem.models;

public class User {
    private final String username;

    public User( String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return username;
    }
}
