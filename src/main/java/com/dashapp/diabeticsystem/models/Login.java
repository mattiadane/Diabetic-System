package com.dashapp.diabeticsystem.models;

import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.enums.ROLE;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Login {
    private final String username;
    private final int id_paziente;
    private final int id_diabetologo;
    private final int id_login;

    private final ObservableList<Chat> chat = FXCollections.observableArrayList();


    public Login(int id_login,String username ,int id_paziente , int id_diabetologo ) {
        this.id_login = id_login;
        this.username = username;
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
         return  Main.getDbManager().selectQuery("SELECT  id_login,id_paziente,id_diabetologo, username FROM login WHERE username  = ? AND password_hash = ?",
                 rs -> {
                    if(rs.next()) {
                        return new Login(rs.getInt("id_login"),rs.getString("username"),rs.getInt("id_paziente"),rs.getInt("id_diabetologo"));
                    }
                     return null;
                 }

                 ,username,password);

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

    public final int getId_diabetologo() {
        return id_diabetologo;
    }

    public int getId_paziente() {
        return id_paziente;
    }

    public int getId_login() {
        return id_login;
    }

    @Override
    public final String toString() {
        return id_login + " " + id_paziente + " " + id_diabetologo + " " + username;
    }


    public int getid_loginDibaetologo(Diabetologo diabetologo){
        return Main.getDbManager().selectQuery("SELECT id_login FROM login WHERE id_diabetologo = ?",
                rs -> {
                    if(rs.next()) {
                        return rs.getInt("id_login");
                    }
                    return null;
                }
                ,diabetologo.getId_diabetologo());
    }

    public ObservableList<Chat> chatDiabetologoPaziente(int id_mittente,int id_destinatario){
        Main.getDbManager().selectQuery("SELECT id_mittente_login,id_destinatario_login,messaggio,data_invio FROM chat " +
                "WHERE (id_mittente_login = ? AND id_destinatario_login = ?) OR (id_mittente_login = ? AND id_destinatario_login = ?) ORDER BY data_invio ASC ",
                    rs -> {
                        while(rs.next()){
                            chat.add(
                                    new Chat(
                                           rs.getInt("id_mittente_login"),
                                           rs.getInt("id_destinatario_login"),
                                           rs.getString("messaggio"),
                                           rs.getTimestamp("data_invio").toLocalDateTime()
                                    )
                            );

                        }
                        return null;
                    }
                ,id_mittente,id_destinatario,id_destinatario,id_mittente);
        return chat;
    }

    public void inviaMessaggio(Chat chat) {
        Main.getDbManager().updateQuery("INSERT INTO chat(id_mittente_login,id_destinatario_login,messaggio) VALUES(?,?,?)",
                chat.getId_mittente(),chat.getId_destinatario(),chat.getMessaggio());
    }
}
