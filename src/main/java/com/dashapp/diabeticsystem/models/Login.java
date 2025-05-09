package com.dashapp.diabeticsystem.models;

import com.dashapp.diabeticsystem.Main;


public class Login {
    private final String username;
    private final int id_paziente;
    private final int id_diabetologo;
    private final int id_login;



    public Login( String username , int id_login ,int id_paziente , int id_diabetologo ) {
        this.username = username;
        this.id_login = id_login;
        this.id_paziente = id_paziente;
        this.id_diabetologo = id_diabetologo;
    }



    /**
     * Funzione che permette di eseguire l'autenticazione degli utenti una volta eseguito il submit nel form
     * @param username username inserito nel form di login
     * @param password password inserita nel form di login
     * @return l'oggetto Login
     */
    public static Login autenticate(String username, String password) {
         return  Main.getDbManager().selectQuery("Select id_login, id_paziente,id_diabetologo, username FROM login WHERE username  = ? AND password_hash = ?",
                 rs -> {
                    if(rs.next()) {
                        return new Login(rs.getString("username"),rs.getInt("id_login"),rs.getInt("id_paziente"),rs.getInt("id_diabetologo"));
                    }
                     return null;
                 }
                 ,username,password);

    }




    public final int getId_paziente() {
        return id_paziente;
    }
    public final int getId_diabetologo() {
        return id_diabetologo;
    }
    public int getId_login() {
        return id_login;
    }

    @Override
    public String toString() {
        return username;
    }

}
