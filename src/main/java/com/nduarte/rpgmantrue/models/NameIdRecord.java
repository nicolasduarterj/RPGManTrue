/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nduarte.rpgmantrue.models;

/**
 *
 * @author nduarte
 */
public class NameIdRecord {
    public String name;
    public int id;
    
    public NameIdRecord(String name, int id) {
        this.name = name;
        this.id = id;
    }
    
    @Override
    public String toString() {
        return String.format("%s (id %d)", name, id);
    }
}
