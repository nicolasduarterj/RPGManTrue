/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nduarte.rpgmantrue.models;

import com.nduarte.rpgmantrue.database.MainSQLiteConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class InfoMagia {
    private int donoId;
    private int[] magiasMax;
    private int[] magiasAtuais;
    
    //--------------------[Construtores e factories]--------------------------//
    
    private InfoMagia(int[] maxUsos, int[] currentUsos, int donoId) {
        this.magiasMax = maxUsos;
        this.magiasAtuais = currentUsos;
        this.donoId = donoId;
    }
    
    public static InfoMagia initialize(int charId) throws SQLException {
        int[] maxUsos = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] currentUsos = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        
        String sqlMax = "INSERT INTO MaxUsosMagia("
                + "Id_Personagem,"
                + "Nivel1,"
                + "Nivel2,"
                + "Nivel3,"
                + "Nivel4,"
                + "Nivel5,"
                + "Nivel6,"
                + "Nivel7,"
                + "Nivel8,"
                + "Nivel9) VALUES (?,?,?,?,?,?,?,?,?,?);";
        
        PreparedStatement stmtMax = MainSQLiteConnection.getConn().prepareStatement(sqlMax);
        stmtMax.setInt(1, charId);
        for (int i = 2; i <= 10; i++) {
            stmtMax.setInt(i, 0);
        }

        stmtMax.execute();
        
        String sqlCurrent = "INSERT INTO UsosRestantesMagia("
                + "Id_Personagem,"
                + "Nivel1,"
                + "Nivel2,"
                + "Nivel3,"
                + "Nivel4,"
                + "Nivel5,"
                + "Nivel6,"
                + "Nivel7,"
                + "Nivel8,"
                + "Nivel9) VALUES (?,?,?,?,?,?,?,?,?,?);";
        
        PreparedStatement stmtCurrent = MainSQLiteConnection.getConn().prepareStatement(sqlCurrent);
        stmtCurrent.setInt(1, charId);
        for (int i = 2; i <= 10; i++) {
            stmtCurrent.setInt(i, 0);
        }
        stmtCurrent.execute();
        
        return new InfoMagia(maxUsos, currentUsos, charId);
    }
    
    public static InfoMagia fromId(int charId) throws SQLException {
        int[] maxUsos = new int[9];
        int[] currentUsos = new int[9];
        
        String sqlMax = "SELECT * FROM MaxUsosMagia WHERE Id_Personagem = ?;";
        
        PreparedStatement stmtMax = MainSQLiteConnection.getConn().prepareStatement(sqlMax);
        stmtMax.setInt(1, charId);
        ResultSet rsMax = stmtMax.executeQuery();
        for (int i = 1; i <= 9; i++) {
            maxUsos[i - 1] = rsMax.getInt(String.format("Nivel%d", i));
        }
        
        String sqlCurrent = "SELECT * FROM UsosRestantesMagia WHERE Id_Personagem = ?;";
        PreparedStatement stmtCurrent = MainSQLiteConnection.getConn().prepareStatement(sqlCurrent);
        stmtCurrent.setInt(1, charId);
        ResultSet rsCurrent = stmtCurrent.executeQuery();
        for (int i = 1; i <= 9; i++) {
            currentUsos[i - 1] = rsCurrent.getInt(String.format("Nivel%d", i));
        }
        
        return new InfoMagia(maxUsos, currentUsos, charId);
    }
    

}
