/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nduarte.rpgmantrue.models;

/**
 *
 * @author nduarte
 */
public class AtaqueConsumoBaseRecord {
    public ItemConsumivel item;
    public double quantidade;
    
    public AtaqueConsumoBaseRecord(ItemConsumivel item, double quantidade) {
        this.item = item;
        this.quantidade = quantidade;
    }
    
    @Override
    public String toString() {
        return String.format("%s (%.2f)", item.getNome(), quantidade);
    }
}
