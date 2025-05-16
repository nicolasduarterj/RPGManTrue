/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nduarte.rpgmantrue.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author nduarte
 */
public abstract class MainSQLiteConnection {
    private static Connection dbConn = null;
    
    public static void initConnection() {
        try {
            dbConn = DriverManager.getConnection("jdbc:sqlite:RPGMan.db");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void initTables() throws DatabaseInitializationException {
        try {
            String sqlPersonagens = "CREATE TABLE IF NOT EXISTS Personagens("
                    + "Id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "Nome VARCHAR(50) UNIQUE,"
                    + "Classe VARCHAR(50) NOT NULL,"
                    + "Nivel INTEGER NOT NULL,"
                    + "HPMax INTEGER NOT NULL,"
                    + "HP INTEGER NOT NULL,"
                    + "HPTemp INTEGER,"
                    + "PecasCobre INTEGER,"
                    + "PecasPrata INTEGER,"
                    + "PecasOuro INTEGER,"
                    + "PecasPlatina INTEGER);";
            
            Statement stmtPersonagens = dbConn.createStatement();
            stmtPersonagens.execute(sqlPersonagens);
        } catch(SQLException e) {
            var ee = new DatabaseInitializationException("Failed to init table Personagens");
            ee.initCause(e);
            throw ee;
        }
        
        try {
            String sqlItensConsumiveis = "CREATE TABLE IF NOT EXISTS ItensConsumiveis("
                    + "Id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "Nome VARCHAR(50) NOT NULL,"
                    + "Dono INTEGER NOT NULL,"
                    + "Quantidade REAL NOT NULL,"
                    + "UNIQUE(Nome,Dono),"
                    + "FOREIGN KEY(Dono) REFERENCES Personagens(Id));";
            
            Statement stmtItensConsumiveis = dbConn.createStatement();
            stmtItensConsumiveis.execute(sqlItensConsumiveis);
        } catch(SQLException e) {
            var ee = new DatabaseInitializationException("Failed to init table ItensConsumiveis");
            ee.initCause(e);
            throw ee;
        }
    }
    
    public static Connection getConn() {
        return dbConn;
    }
}
