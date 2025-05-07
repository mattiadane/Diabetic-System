package com.dashapp.diabeticsystem.models;

import com.dashapp.diabeticsystem.Main;


public class Login {



    public User getUser(String username, String password) {
         return  Main.getDbManager().selectQuery("Select * FROM login WHERE username  = ? AND password_hash = ?",
                 rs -> {
                    if(rs.next()) {
                        return new User(rs.getString("username"));
                    }
                     return null;
                 }
                 ,username,password);

    }

}
