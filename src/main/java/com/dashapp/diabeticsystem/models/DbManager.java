package com.dashapp.diabeticsystem.models;

import java.sql.*;


public class DbManager {


    private static DbManager dbManager;
    private String DATABASE_URL = "jdbc:mysql://localhost:3306/progetto_ing?useSSL=false";
    private String DATABASE_USERNAME = "admin";
    private String DATABASE_PASSWORD = "admin";


    /**
     * Costruttore utilizzato solo per i test
     */
    public DbManager(String url, String user, String pass, Connection sharedConn){
        this.DATABASE_URL = url;
        this.DATABASE_USERNAME = user;
        this.DATABASE_PASSWORD = pass;
        this.sharedConnection = sharedConn;
    }



    /**
     * Costruttore utilizzato nel software
     */
    private DbManager() {
        try {
            getConnection();

        } catch (SQLException e) {
            System.err.println("Errore di connessione al database");
        }
    }

    public static DbManager connect() {
        if (dbManager == null) {
            dbManager = new DbManager();
        }
        return dbManager;
    }






    /*
        Method that after insert , give me the last id
     */

    public int insertAndGetGeneratedId(String sql, Object... params) {
        int generatedId = -1;
        try {
            Connection conn = getConnection();

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                    }
                }

            }
        } catch (SQLException e) {
            System.err.println("Error executing query" + e.getMessage());


        }
        return generatedId;
    }


    /*
    Method for execute query like insert, update,delete
     */
    public boolean updateQuery(String sql, Object... params) {
        try {
            Connection conn = getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println("Error executing query" + e.getMessage());
            return false;
        }
    }

    /*
    Method for execute select query
     */
    public <T> T selectQuery(String sql, ResultSetProcessor<T> processor, Object... params) {
        try {

            Connection conn = getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = ps.executeQuery()) {
                return processor.process(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error executing query" + e.getMessage());
            return null;
        }
    }

    public interface ResultSetProcessor<T> {
        T process(ResultSet rs) throws SQLException;
    }

    private Connection sharedConnection;

    private Connection getConnection() throws SQLException {
        if (sharedConnection == null || sharedConnection.isClosed()) {
            sharedConnection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        }
        return sharedConnection;
    }

}
