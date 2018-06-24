/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cflow.database;

/**
 *
 * @author Claude
 */
public class Entry {
    private int id;
    private String date;
    private String type;
    private double amount;
    private String description;
    
    public Entry(int id, String date, String type, double amount, String description) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.description = description;
    }
    
    public int getID() {
        return this.id;
    }
    
    public String getDate() {
        return this.date;
    }
    
    public String getType() {
        return this.type;
    }
    
    public double getAmount() {
        return this.amount;
    }
    
    public String getDescription() {
        return this.description;
    }
}
