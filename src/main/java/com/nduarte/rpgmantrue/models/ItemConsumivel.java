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
public class ItemConsumivel {
    private int id;
    private int donoId;
    private String nome;
    private double quantidade;
    
    //-------------------------[Construtores e factories]---------------------------//
    
    private ItemConsumivel(int id, int donoId, String nome, double quantidade) {
        this.id = id;
        this.donoId = donoId;
        this.nome = nome;
        this.quantidade = quantidade;
    }
    
    public static ItemConsumivel initialize(int donoId, String nome, double quantidade) 
    throws SQLException, IllegalArgumentException {
        if (quantidade < 0) throw new IllegalArgumentException("Quantidade deve ser maior que 0.");
        
        String sqlCreate = "INSERT INTO ItensConsumiveis(Nome, Dono, Quantidade) VALUES (?, ?, ?);";
        PreparedStatement stmtCreate = MainSQLiteConnection.getConn().prepareStatement(sqlCreate);
        
        stmtCreate.setString(1, nome);
        stmtCreate.setInt(2, donoId);
        stmtCreate.setDouble(3, quantidade);
        
        stmtCreate.execute();
        
        String sqlFind = "SELECT * FROM ItensConsumiveis WHERE Nome = ? AND Dono = ?;";
        PreparedStatement stmtFind = MainSQLiteConnection.getConn().prepareStatement(sqlFind);
        
        stmtFind.setString(1, nome);
        stmtFind.setInt(2, donoId);
        
        ResultSet rsFind = stmtFind.executeQuery();
        return new ItemConsumivel(
                rsFind.getInt("Id"),
                rsFind.getInt("Dono"),
                rsFind.getString("Nome"),
                rsFind.getDouble("Quantidade")
        );
    }
    
    public static ItemConsumivel fromId(int id) throws SQLException {
        String sqlFind = "SELECT * FROM ItensConsumiveis WHERE Id = ?;";
        
        PreparedStatement stmtFind = MainSQLiteConnection.getConn().prepareStatement(sqlFind);
        stmtFind.setInt(1, id);
        ResultSet rsFind = stmtFind.executeQuery();
        return new ItemConsumivel(
                rsFind.getInt("Id"),
                rsFind.getInt("Dono"),
                rsFind.getString("Nome"),
                rsFind.getDouble("Quantidade")
        );
    }
    
    public static ArrayList<ItemConsumivel> getAllByDonoId(int donoId) {
        String sqlFind = "SELECT * FROM ItensConsumiveis WHERE Dono = ?;";
        
        try {
            PreparedStatement stmtFind = MainSQLiteConnection.getConn().prepareStatement(sqlFind);
            stmtFind.setInt(1, donoId);
            ResultSet rs = stmtFind.executeQuery();
            ArrayList<ItemConsumivel> found = new ArrayList<>();
            
            while (rs.next()) {
                found.add(new ItemConsumivel(
                        rs.getInt("Id"),
                        rs.getInt("Dono"),
                        rs.getString("Nome"),
                        rs.getDouble("Quantidade")
                ));
            }
            
            return found;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<ItemConsumivel>();
        }
    }
    
    //------------------[Getters e setters]-----------------------------//
    
    public int getId() { return id; }
    public int getDonoId() { return donoId; }
    public String getNome() { return nome; }
    public double getQuantidade() { return quantidade; }
    
    public double spend(double x) throws IllegalArgumentException {
        if (quantidade - x < 0) throw new IllegalArgumentException("Não há quantidade o suficiente.");
        
        quantidade = quantidade - x;
        return quantidade;
    }
    
    public double add(double x) {
        quantidade = quantidade + x;
        return quantidade;
    }
    
    //------------------[Overrides]-------------------------------------//
    
    @Override
    public String toString() {
        return String.format("%s (%.2f disponíveis)", this.nome, this.quantidade);
    }
    
    //-------------------[Salvamento]----------------------------------//
    public void save() {
        String sql = "INSERT OR REPLACE INTO ItensConsumiveis(Id, Dono, Nome, Quantidade) VALUES (?, ?, ?, ?);";
        try {
            PreparedStatement stmt = MainSQLiteConnection.getConn().prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setInt(2, donoId);
            stmt.setString(3, nome);
            stmt.setDouble(4, quantidade);
            
            stmt.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
