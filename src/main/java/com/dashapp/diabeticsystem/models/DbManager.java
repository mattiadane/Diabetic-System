package com.dashapp.diabeticsystem.models;

import java.sql.*;


public class DbManager {


    private static DbManager dbManager;
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/progetto_ing?useSSL=false";
    private static final String DATABASE_USERNAME = "admin";
    private static final String DATABASE_PASSWORD = "admin";


    private DbManager()  {
        try{
            getConnection();

        } catch (SQLException e) {
            System.err.println("Errore di connessione al database");
        }
    }

    public static DbManager connect(){
        if(dbManager == null){
            dbManager = new DbManager();
        }
        return dbManager;
    }






    /*
        Method that after insert , give me the last id
     */

    public int insertAndGetGeneratedId(String sql, Object... params) {
        int generatedId = -1;
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
    public boolean updateQuery(String sql,Object... params){
        try(Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql))  {

            for(int i=0; i<params.length; i++){
                ps.setObject(i+1, params[i]);
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
    public <T> T selectQuery(String sql, ResultSetProcessor<T> processor, Object... params){
        try(Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql))  {

            for(int i=0; i<params.length; i++){
                ps.setObject(i+1, params[i]);
            }

            try (ResultSet rs = ps.executeQuery()) {
                return processor.process(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error executing query" + e.getMessage());
            return null ;
        }
    }

    public interface ResultSetProcessor<T> {
        T process(ResultSet rs) throws SQLException;
    }

    private static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);
    }
}
