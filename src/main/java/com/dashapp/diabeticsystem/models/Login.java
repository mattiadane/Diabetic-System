package com.dashapp.diabeticsystem.models;

import com.dashapp.diabeticsystem.Main;


public class Login {
    private final String username;
    private final int id_paziente;
    private final int id_diabetologo;



    public Login( String username , Integer id_paziente , Integer id_diabetologo ) {
        this.username = username;
        this.id_paziente = id_paziente;
        this.id_diabetologo = id_diabetologo;
    }

    @Override
    public String toString() {
        return username;
    }


    public static Login autenticate(String username, String password) {
         return  Main.getDbManager().selectQuery("Select * FROM login WHERE username  = ? AND password_hash = ?",
                 rs -> {
                    if(rs.next()) {
                        return new Login(rs.getString("username"),rs.getInt("id_paziente"),rs.getInt("id_diabetologo"));
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

}
