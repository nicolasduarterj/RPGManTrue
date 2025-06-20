/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nduarte.rpgmantrue.models;

import com.nduarte.rpgmantrue.database.MainSQLiteConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author nduarte
 */
public class AtaqueConsumo {
    private int idAtaque;
    private double quantidade;
    private ItemConsumivel item;
    
    private AtaqueConsumo(int idAtaque, ItemConsumivel item, double quantidade) {
        this.idAtaque = idAtaque;
        this.item = item;
        this.quantidade = quantidade;
    }
    
    public static AtaqueConsumo initialize(int idAtaque, ItemConsumivel item, double quantidade) throws SQLException {
        String sql = "INSERT INTO AtaquesConsumos(Id_Ataque, Id_Item, Quantidade) VALUES (?, ?, ?);";
        
        PreparedStatement stmtCreate = MainSQLiteConnection.getConn().prepareStatement(sql);
        stmtCreate.setInt(1, idAtaque);
        stmtCreate.setInt(2, item.getId());
        stmtCreate.setDouble(3, quantidade);
        
        stmtCreate.execute();
        return new AtaqueConsumo(idAtaque, item, quantidade);
    }
    
    public static ArrayList<AtaqueConsumo> getAllByAtaqueId(int idAtaque) {
        try {
            ArrayList<AtaqueConsumo> found = new ArrayList<>();
            
            String sql = "SELECT * FROM AtaquesConsumos WHERE Id_Ataque = ?;";

            PreparedStatement stmtFind = MainSQLiteConnection.getConn().prepareStatement(sql);
            stmtFind.setInt(1, idAtaque);
            ResultSet rsFind = stmtFind.executeQuery();

            while (rsFind.next()) {
                found.add(new AtaqueConsumo(
                        rsFind.getInt("Id_Ataque"),
                        ItemConsumivel.fromId(rsFind.getInt("Id_Item")),
                        rsFind.getDouble("Quantidade")
                ));
            }
            
            return found;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public ItemConsumivel getItem() { return item; }
    public double getQuantidade() { return quantidade; }
}
