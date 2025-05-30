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
        
        try {
            String sqlEquipamentos = "CREATE TABLE IF NOT EXISTS Equipamentos("
                    + "Id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "Nome VARCHAR(50) NOT NULL,"
                    + "Dono INTEGER NOT NULL,"
                    + "EstaEquipado BOOLEAN NOT NULL,"
                    + "FOREIGN KEY(Dono) REFERENCES Personagens(Id));";
            
            Statement stmtEquipamentos = dbConn.createStatement();
            stmtEquipamentos.execute(sqlEquipamentos);
        } catch(SQLException e) {
            var ee = new DatabaseInitializationException("Failed to init table ItensConsumiveis");
            ee.initCause(e);
            throw ee;
        }
        
        try {
            String sqlMaxUsosMagia = "CREATE TABLE IF NOT EXISTS MaxUsosMagia("
                    + "Id_Personagem INTEGER PRIMARY KEY,"
                    + "Nivel1 INTEGER,"
                    + "Nivel2 INTEGER,"
                    + "Nivel3 INTEGER,"
                    + "Nivel4 INTEGER,"
                    + "Nivel5 INTEGER,"
                    + "Nivel6 INTEGER,"
                    + "Nivel7 INTEGER,"
                    + "Nivel8 INTEGER,"
                    + "Nivel9 INTEGER,"
                    + "FOREIGN KEY(Id_Personagem) REFERENCES Personagens(Id));";
            
            Statement stmtMaxUsosMagia = dbConn.createStatement();
            stmtMaxUsosMagia.execute(sqlMaxUsosMagia);
        } catch(SQLException e) {
            var ee = new DatabaseInitializationException("Failed to init MaxUsosMagia");
            ee.initCause(e);
            throw ee;
        }
        
        try {
            String sqlUsosRestantesMagia = "CREATE TABLE IF NOT EXISTS UsosRestantesMagia("
                    + "Id_Personagem INTEGER PRIMARY KEY,"
                    + "Nivel1 INTEGER,"
                    + "Nivel2 INTEGER,"
                    + "Nivel3 INTEGER,"
                    + "Nivel4 INTEGER,"
                    + "Nivel5 INTEGER,"
                    + "Nivel6 INTEGER,"
                    + "Nivel7 INTEGER,"
                    + "Nivel8 INTEGER,"
                    + "Nivel9 INTEGER,"
                    + "FOREIGN KEY(Id_Personagem) REFERENCES Personagens(Id));";
            
            Statement stmtUsosRestantes = dbConn.createStatement();
            stmtUsosRestantes.execute(sqlUsosRestantesMagia);
        } catch(SQLException e) {
            var ee = new DatabaseInitializationException("Failed to init UsosRestantes");
            ee.initCause(e);
            throw ee;
        }
        
        try {
            String sqlAtaques = "CREATE TABLE IF NOT EXISTS Ataques("
                    + "Id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "Nome VARCHAR(50) NOT NULL,"
                    + "Dono INTEGER NOT NULL,"
                    + "NivelMagico INTEGER,"
                    + "DependEquipId INTEGER,"
                    + "TipoDado INTEGER NOT NULL,"
                    + "NumeroDado INTEGER NOT NULL,"
                    + "Bonus INTEGER DEFAULT 0,"
                    + "FOREIGN KEY(Dono) REFERENCES Personagens(Id),"
                    + "FOREIGN KEY(DependEquipId) REFERENCES Equipamentos(Id),"
                    + "UNIQUE(Dono,Nome));";
            
            Statement stmtAtaques = dbConn.createStatement();
            stmtAtaques.execute(sqlAtaques);
        } catch(SQLException e) {
            var ee = new DatabaseInitializationException("Failed to init Ataques");
            ee.initCause(e);
            throw ee;
        }
        
        try {
            String sqlConsumos = "CREATE TABLE IF NOT EXISTS AtaquesConsumos("
                    + "Id_Ataque INTEGER NOT NULL,"
                    + "Id_Item INTEGER NOT NULL,"
                    + "Quantidade REAL NOT NULL,"
                    + "PRIMARY KEY(Id_Ataque,Id_Item),"
                    + "FOREIGN KEY(Id_Ataque) REFERENCES Ataques(Id),"
                    + "FOREIGN KEY(Id_Item) REFERENCES ItensConsumiveis(Id));";
            
            Statement stmtConsumos = dbConn.createStatement();
            stmtConsumos.execute(sqlConsumos);
        } catch (SQLException e) {
            var ee = new DatabaseInitializationException("Failed to init AtaquesConsumos");
            ee.initCause(e);
            throw ee;
        }
    }
    
    public static Connection getConn() {
        return dbConn;
    }
}
