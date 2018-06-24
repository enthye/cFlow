/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cflow.database;

import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Claude
 */
public class Database {
    
    private final String homeDir = System.getProperty("user.home"); // gets default directory C:/users/<name>
    private final String DB_URL = "jdbc:sqlite:" + homeDir + "/cFlow.db";
    
    public Database() {
        // alterTable(); 
    }
    
    // connects to database (for use in the methods below)
    private Connection connectToDB() {
        Connection conn = null;
        
        try {
            // create new table in DB_URL
            String sql = "CREATE TABLE IF NOT EXISTS cFLOW("
                    + "id           integer     primary key,\n"
                    + "date         text        NOT NULL,\n"
                    + "type         text        NOT NULL,\n"
                    + "amount       double      NOT NULL,\n"
                    + "description  text        NOT NULL"
                    + ");";
            
            conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            
        } catch (SQLException se) {
            System.out.println("Database.connectToDB " + se.getMessage());
        }
        
        return conn;
    }
    
    public void insertEntry(String date, String type, double amount, String description) {
        String sql = "INSERT INTO cFlow(date, type, amount, description)"
                + "VALUES(?,?,?,?);";
        
        try (Connection conn = this.connectToDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // inserts given variables into a row of the db
            pstmt.setString(1, date);
            pstmt.setString(2, type);
            pstmt.setDouble(3, amount);
            pstmt.setString(4, description);
            pstmt.executeUpdate();
        } catch (SQLException se) {
            System.out.println("Databse.insertEntry " + se.getMessage());
        }
    }
    
    // getEntryList converts data entries in DB to a list to be uploaded onto JTable
    public List<Entry> getEntryList() {
        List<Entry> list = new ArrayList<>();
        String sql = "SELECT id, date, type, amount, description FROM cFlow;";
        
        try (Connection conn = this.connectToDB();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();) {
            while (rs.next()) {
                // get each row from db and put into list
                list.add(new Entry(rs.getInt("id"),rs.getString("date"), 
                        rs.getString("type"), rs.getDouble("amount"), rs.getString("description")));
            }
        } catch (SQLException se) {
            System.out.println("Database.getEntryList " + se.getMessage());
        }
        
        return list;
    }
    
    public void deleteEntry(String id) {
        // deletes row with given id
        String sql = "DELETE FROM cFlow WHERE id = " + id;
        
        try (Connection conn = this.connectToDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();
        } catch(SQLException se) {
            System.out.println("Database.deleteEntry" + se.getMessage());
        }
    }
    
    // add column to existing table
    private void alterTable() {
        String sql = "ALTER TABLE cFlow "
                + "ADD description  text;";
        
        try (Connection conn = this.connectToDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();
        } catch(SQLException se) {
            System.out.println("Database.alterTable" + se.getMessage());
        }
    }
    
}
