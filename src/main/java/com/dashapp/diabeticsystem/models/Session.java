package com.dashapp.diabeticsystem.models;

public class Session {

    private static Login currentUser;

    /**
     * Funzione che permette di istanzare il nuovo utente che ha avvitato la sessione.
     * @param newUser oggetto di tipo <code>Login</code> che ha fatto partire una nuova sessione.
     */
    public static void setCurrentUser(Login newUser){
        currentUser = newUser;
    }

    /**
     * Funzione che permette di prendere dalla classe l'utente che ha eseguito l'accesso per ultimo all'applicazione
     * @return oggetto di tipo <code>Login</code>
     */
    public static Login getCurrentUser(){
        return currentUser;
    }

}
