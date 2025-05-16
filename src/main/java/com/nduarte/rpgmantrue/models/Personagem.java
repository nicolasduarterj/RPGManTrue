/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nduarte.rpgmantrue.models;


import java.sql.SQLException;
import java.sql.PreparedStatement;
import com.nduarte.rpgmantrue.database.MainSQLiteConnection;
import java.sql.ResultSet;

/**
 * 
 * @author nduarte
 */
public class Personagem {
    private int id;
    private String nome;
    private String classe;
    private int nivel;
    private int hp;
    private int hpMax;
    private int hpTemp;
    private int pecasCobre;
    private int pecasPrata;
    private int pecasOuro;
    private int pecasPlatina;
    
    private Personagem(int id, String nome, String classe, int nivel, 
            int hp, int hpMax, int hpTemp, int pecasCobre, int pecasPrata, 
            int pecasOuro, int pecasPlatina) {
        
        this.id = id;
        this.nome = nome;
        this.classe = classe;
        this.nivel = nivel;
        this.hp = hp;
        this.hpMax = hpMax;
        this.hpTemp = hpTemp;
        this.pecasPrata = pecasPrata;
        this.pecasCobre = pecasCobre;
        this.pecasOuro = pecasOuro;
        this.pecasPlatina = pecasPlatina;
    }
    
    public static Personagem createChar(String nome, String classe, int nivel, 
            int hpMax) throws IllegalArgumentException, SQLException {
        
        String createCharSQL = "INSERT INTO Personagens(Nome, Classe, Nivel, "
                + "HPMax, HP, HPTemp, PecasCobre, PecasPrata, PecasOuro, PecasPlatina) "
                + "VALUES (?, ?, ?, ?, ?, 0, 0, 0, 0, 0);";
        
        try {
            PreparedStatement stmt = MainSQLiteConnection.getConn()
                    .prepareStatement(createCharSQL);
            
            stmt.setString(1, nome);
            stmt.setString(2, classe);
            stmt.setInt(3, nivel);
            stmt.setInt(4, hpMax);
            stmt.setInt(5, hpMax);
            
            stmt.execute();
        } catch(SQLException e) {
            throw new IllegalArgumentException("Verifique os parâmetros do personagem", e);
        }
        
        try {
            String findCharSQL = "SELECT * FROM Personagens "
                    + "WHERE Nome = ?;";
            
            PreparedStatement stmtFind = MainSQLiteConnection.getConn()
                    .prepareStatement(findCharSQL);
            
            stmtFind.setString(1, nome);
            
            ResultSet rs = stmtFind.executeQuery();
            
            return new Personagem(
                    rs.getInt("Id"),
                    rs.getString("Nome"),
                    rs.getString("Classe"),
                    rs.getInt("Nivel"),
                    rs.getInt("HP"),
                    rs.getInt("HPMax"),
                    rs.getInt("HPTemp"),
                    rs.getInt("PecasCobre"),
                    rs.getInt("PecasPrata"),
                    rs.getInt("PecasOuro"),
                    rs.getInt("PecasPlatina")
            );
        } catch(SQLException e) {
            throw e;
        }
    }
    
    @Override
    public String toString() {
        return String.format("Id:%d\nNome:%s\nClasse:%s\nNivel:%d\nHP:%d\nHPMax:%d\n",
                this.id,
                this.nome,
                this.classe,
                this.nivel,
                this.hp,
                this.hpMax);
    }
}
