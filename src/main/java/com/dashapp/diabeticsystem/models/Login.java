package com.dashapp.diabeticsystem.models;

import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.enums.ROLE;


public class Login {
    private final String username;
    private final String password;
    private final Integer id_paziente;
    private final Integer id_diabetologo;
    private int id_login;



    public Login(int id_login,String username ,String password,Integer id_paziente , Integer id_diabetologo ) {
        this(username,password,id_paziente,id_diabetologo);
        this.id_login = id_login;

    }

    public Login(String username ,String password,Integer id_paziente , Integer id_diabetologo){
        this.username = username;
        this.password = password;
        this.id_paziente = id_paziente;
        this.id_diabetologo = id_diabetologo;
    }



    /**
     * Funzione che permette di ritornare un enum per il tipo di utente che si è loggato nel form di login.
     * @return tipo <code>ROLE</code> per il ruolo dell'utente.
     */
    public ROLE getRole() {
        if(id_paziente != 0 ) return ROLE.PAZIENTE;
        else if(id_diabetologo != 0 ) return ROLE.DIABETOLOGO;

        return ROLE.ADMIN;
    }


    /**
     * Funzione che permette di restituire l'id del diabetologo associata a questa utenza
     * @return id_diabetologo
     */
    public final Integer getId_diabetologo() {
        return id_diabetologo;
    }

    /**
     * Funzione che permette di restituire l'id del paziente associato a questa utenza
     * @return id_paziente
     */
    public Integer getId_paziente() {
        return id_paziente;
    }


    /**
     * Funzione che permette di restituire l'id di questa utenza
     * @return id_login
     */
    public int getId_login() {
        return id_login;
    }


    /**
     * Funzione che permette di restituire l'username di questa utenza
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Funzione che permette di restituire la password  di questa utenza
     * @return password
     */
    public String getPassword() {
        return password;
    }

    @Override
    public final String toString() {
        return id_login + " " + id_paziente + " " + id_diabetologo + " " + username;
    }

}
