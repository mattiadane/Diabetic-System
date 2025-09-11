package com.dashapp.diabeticsystem.DAO.interfaces;

import com.dashapp.diabeticsystem.models.Login;
import java.sql.SQLException;

public interface LoginDao {


    /**
     * Funzione che permette di eseguire l'autenticazione degli utenti una volta eseguito il submit nel form
     * @param username username inserito nel form di login
     * @param password password inserita nel form di login
     * @return l'oggetto Login
     */
    Login autenticate(String username, String password);



    /**
     * Funzione che permette di inserire una nuova utenza nella tabella login del database
     * @param login utente che andrà inserito nel database
     * @return <code>true</code> se la query va a buon fine <code>false</code> altrimenti
     */
    boolean insertLogin(Login login) throws SQLException;

    /**
     * Funzione che permette di aggiornare la password degli utenti
     * @param newPassword nuova password da inserire a database
     * @param login utente dove va modificata la password
     * @return <code>true</code> se la query va a buon fine, <code>false</code> altrimenti.
     */
    boolean updateLogin(Login login,String newPassword);

}
