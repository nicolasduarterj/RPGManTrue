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
public class Equipamento {
    private int id;
    private int donoId;
    private String nome;
    private boolean estaEquipado;
    
    private Equipamento(int id, int donoId, String nome, boolean estaEquipado) {
        this.id = id;
        this.donoId = donoId;
        this.nome = nome;
        this.estaEquipado = estaEquipado;
    }
    
    public static Equipamento initialize(int donoId, String nome) throws SQLException {
        String sqlCreate = "INSERT INTO Equipamentos(Dono, Nome, EstaEquipado) VALUES (?, ?, FALSE);";
        PreparedStatement stmtCreate = MainSQLiteConnection.getConn().prepareStatement(sqlCreate);
        stmtCreate.setInt(1, donoId);
        stmtCreate.setString(2, nome);
        stmtCreate.execute();
        
        String sqlFind = "SELECT * FROM Equipamentos WHERE Dono = ? AND Nome = ?;";
        PreparedStatement stmtFind = MainSQLiteConnection.getConn().prepareStatement(sqlFind);
        stmtFind.setInt(1, donoId);
        stmtFind.setString(2, nome);
        ResultSet rsFind = stmtFind.executeQuery();
        return new Equipamento(
                rsFind.getInt("Id"),
                rsFind.getInt("Dono"),
                rsFind.getString("Nome"),
                rsFind.getBoolean("EstaEquipado")
        );
    }
    
    public static Equipamento fromId(int id) {
        try {
            String sqlFind = "SELECT * FROM Equipamentos WHERE Id = ?;";
            PreparedStatement stmtFind = MainSQLiteConnection.getConn().prepareStatement(sqlFind);
            stmtFind.setInt(1, id);
            ResultSet rsFind = stmtFind.executeQuery();
            return new Equipamento(
                    rsFind.getInt("Id"),
                    rsFind.getInt("Dono"),
                    rsFind.getString("Nome"),
                    rsFind.getBoolean("EstaEquipado")
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public static ArrayList<Equipamento> getAllByDonoId(int donoId) {
        try {
            String sqlFind = "SELECT * FROM Equipamentos WHERE Dono = ?;";
            PreparedStatement stmtFind = MainSQLiteConnection.getConn().prepareStatement(sqlFind);
            stmtFind.setInt(1, donoId);
            ResultSet rsFind = stmtFind.executeQuery();
            
            ArrayList<Equipamento> result = new ArrayList<>();
            while (rsFind.next()) {
                result.add(new Equipamento(
                        rsFind.getInt("Id"),
                        rsFind.getInt("Dono"),
                        rsFind.getString("Nome"),
                        rsFind.getBoolean("EstaEquipado")
                ));
            }
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public String getNome() { return nome; }
    public int getId() { return id; }
    public int getDonoId() { return donoId; }
    public boolean getEstaEquipado() { return estaEquipado; }
    
    public void toggleEstaEquipado() { estaEquipado = !estaEquipado; }
    
    public void save() {
        try {
            String sql = "INSERT OR REPLACE INTO Equipamentos(Id, Dono, Nome, EstaEquipado) VALUES "
                    + "(?, ?, ?, ?);";
            
            PreparedStatement stmt = MainSQLiteConnection.getConn().prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setInt(2, donoId);
            stmt.setString(3, nome);
            stmt.setBoolean(4, estaEquipado);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s)",
                    this.nome,
                    this.estaEquipado ? "Equipado" : "Não equipado"
                );
    }
}
