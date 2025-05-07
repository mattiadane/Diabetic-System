package com.dashapp.diabeticsystem.models;

import com.dashapp.diabeticsystem.Main;


public class Login {
    private final String username;

    public Login( String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return username;
    }


    public static Login autenticate(String username, String password) {
         return  Main.getDbManager().selectQuery("Select * FROM login WHERE username  = ? AND password_hash = ?",
                 rs -> {
                    if(rs.next()) {
                        return new Login(rs.getString("username"));
                    }
                     return null;
                 }
                 ,username,password);

    }

}
