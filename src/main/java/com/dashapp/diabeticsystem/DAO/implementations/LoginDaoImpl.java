package com.dashapp.diabeticsystem.DAO.implementations;

import com.dashapp.diabeticsystem.DAO.interfaces.LoginDao;
import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.models.Login;


public class LoginDaoImpl implements LoginDao {


    /**
     * Funzione che permette di eseguire l'autenticazione degli utenti una volta eseguito il submit nel form
     * @param username username inserito nel form di login
     * @param password password inserita nel form di login
     * @return l'oggetto Login
     */
    public Login autenticate(String username, String password) {
        return  Main.getDbManager().selectQuery("SELECT  id_login,id_paziente,id_diabetologo,password_hash,username FROM login WHERE username  = ? AND password_hash = ?",
                rs -> {
                    if(rs.next()) {
                        return new Login(rs.getInt("id_login"),rs.getString("username"),rs.getString("password_hash"),rs.getInt("id_paziente"),rs.getInt("id_diabetologo"));
                    }
                    return null;
                }

                ,username,password);

    }


    /**
     * Funzione che permette di inserire l'utenza nel database
     * @param login utente che andrà inserito nel database
     * @return <code>true</code> se l'operazione è andata a buon fine <code>false</code> altrimenti
     */
    @Override
    public boolean insertLogin(Login login)  {
        if(login == null) {
            return false;
        }

        if(login.getId_paziente() != null  && login.getId_diabetologo() == null) {
            return Main.getDbManager().updateQuery(
                    "INSERT INTO login(id_paziente,id_diabetologo,username,password_hash) VALUES(?,?,?,?)",
                    login.getId_paziente(),null,login.getUsername(),login.getPassword());

        } else if(login.getId_diabetologo() != null && login.getId_paziente() == null) {
            return Main.getDbManager().updateQuery(
                    "INSERT INTO login(id_paziente,id_diabetologo,username,password_hash) VALUES(?,?,?,?)",
                    null,login.getId_diabetologo(),login.getUsername(),login.getPassword()
                    );
        }
        return false;

    }


    /**
     * Funzione che permette di modificare la password
     * @param login utente dove va modificata la password
     * @param newPassword nuova password da inserire a database
     * @return <code>true</code> se l'operazione va a buon fine <code>false</code> altrimenti
     */
    @Override
    public boolean updateLogin(Login login, String newPassword) {
        return Main.getDbManager().updateQuery("UPDATE login SET password_hash = ? WHERE id_login  = ?",newPassword,login.getId_login());

    }

}
