/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nduarte.rpgmantrue.models;
import com.nduarte.rpgmantrue.database.MainSQLiteConnection;
import com.nduarte.rpgmantrue.models.exceptions.NotEnoughItemsException;
import com.nduarte.rpgmantrue.models.exceptions.NotEnoughMagicSlotsException;
import com.nduarte.rpgmantrue.models.exceptions.UnequippedDependencyException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author nduarte
 */
public class Ataque {
    private int id;
    private String nome;
    private int donoId;
    private int nivelMagico;
    private Equipamento dependEquip;
    private int tipoDado;
    private int numeroDado;
    private int bonus;
    private ArrayList<AtaqueConsumo> dependItens;
    
    //--------------------[Construtores e factories]----------------------------------------//
    
    private Ataque(int id, String nome, int donoId, int nivelMagico, Equipamento dependEquip,
    int tipoDado, int numeroDado, int bonus) {
        this.id = id;
        this.nome = nome;
        this.donoId = donoId;
        this.nivelMagico = nivelMagico;
        this.dependEquip = dependEquip;
        this.tipoDado = tipoDado;
        this.numeroDado = numeroDado;
        this.bonus = bonus;  
    }
    
    public static Ataque initialize(String nome, int donoId, int nivelMagico, Equipamento dependEquip,
    int tipoDado, int numeroDado, int bonus) 
    throws IllegalArgumentException, SQLException {
        
        if (tipoDado < 1 || numeroDado < 1) throw new IllegalArgumentException("O tipo e número do dado devem ser positivos");
        
        String sqlCreate = "INSERT INTO Ataques(Nome, Dono, NivelMagico, DependEquipId, TipoDado, NumeroDado, Bonus) VALUES "
                + "(?, ?, ?, ?, ?, ?, ?);";
        
        PreparedStatement stmtCreate = MainSQLiteConnection.getConn().prepareStatement(sqlCreate);
        stmtCreate.setString(1, nome);
        stmtCreate.setInt(2, donoId);
        stmtCreate.setInt(3, nivelMagico);
        
        if (dependEquip == null) {
            stmtCreate.setNull(4, java.sql.Types.INTEGER);
        } else {
            stmtCreate.setInt(4, dependEquip.getId());
        }
            
        stmtCreate.setInt(5, tipoDado);
        stmtCreate.setInt(6, numeroDado);
        stmtCreate.setInt(7, bonus);
        
        stmtCreate.execute();
        
        String sqlFind = "SELECT * FROM Ataques WHERE Nome = ? AND Dono = ?;";
        PreparedStatement stmtFind = MainSQLiteConnection.getConn().prepareStatement(sqlFind);
        stmtFind.setString(1, nome);
        stmtFind.setInt(2, donoId);
        
        ResultSet rsFind = stmtFind.executeQuery();
        Equipamento equipAlvo = rsFind.getInt("DependEquipId") == 0
                ? null
                : Equipamento.fromId(rsFind.getInt("DependEquipId"));
        
        Ataque criado = new Ataque(
                rsFind.getInt("Id"),
                rsFind.getString("Nome"),
                rsFind.getInt("Dono"),
                rsFind.getInt("NivelMagico"),
                equipAlvo,
                rsFind.getInt("TipoDado"),
                rsFind.getInt("NumeroDado"),
                rsFind.getInt("Bonus")
        );
        
        return criado;
    }
    
    public static Ataque fromId(int id) {
        try {
            String sqlFind = "SELECT * FROM Ataques WHERE Id = ?;";
            
            PreparedStatement stmtFind = MainSQLiteConnection.getConn().prepareStatement(sqlFind);
            stmtFind.setInt(1, id);
            
            ResultSet rsFind = stmtFind.executeQuery();
            Equipamento equipAlvo = rsFind.getInt("DependEquipId") == 0
                ? null
                : Equipamento.fromId(rsFind.getInt("DependEquipId"));
        
            Ataque criado = new Ataque(
                    rsFind.getInt("Id"),
                    rsFind.getString("Nome"),
                    rsFind.getInt("Dono"),
                    rsFind.getInt("NivelMagico"),
                    equipAlvo,
                    rsFind.getInt("TipoDado"),
                    rsFind.getInt("NumeroDado"),
                    rsFind.getInt("Bonus")
            );
            criado.refreshDepend();
            
            return criado;
        } catch (SQLException e) {
            return null;
        }
    }
    
    public static ArrayList<Ataque> getAllByDonoId(int id) {
        try {
            ArrayList<Ataque> result = new ArrayList<>();
            
            String sql = "SELECT * FROM Ataques WHERE Dono = ?;";
            
            PreparedStatement stmtFind = MainSQLiteConnection.getConn().prepareStatement(sql);
            stmtFind.setInt(1, id);
            ResultSet rsFind = stmtFind.executeQuery();
            
            while (rsFind.next()) {
                Equipamento equipAlvo = rsFind.getInt("DependEquipId") == 0
                    ? null
                    : Equipamento.fromId(rsFind.getInt("DependEquipId"));
                
                Ataque criado = new Ataque(
                    rsFind.getInt("Id"),
                    rsFind.getString("Nome"),
                    rsFind.getInt("Dono"),
                    rsFind.getInt("NivelMagico"),
                    equipAlvo,
                    rsFind.getInt("TipoDado"),
                    rsFind.getInt("NumeroDado"),
                    rsFind.getInt("Bonus")
                );
                
                criado.refreshDepend();
                result.add(criado);
            }
            
            return result;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }
    
    
    //------------------[Getters e setters]---------------------------//
    
    public int getId() { return id; }
    public int getDonoId() { return donoId; }
    public String getNome() { return nome; }
    public int getNivelMagico() { return nivelMagico; }
    public int getNumeroDado() { return numeroDado; }
    public int getTipoDado() { return tipoDado; }
    public int getBonus() { return bonus; }
    public Equipamento getDependEquip() { return dependEquip; }
    public ArrayList<AtaqueConsumo> getDependItens() { return dependItens; }
    
    public void refreshDepend() {
        this.dependItens = AtaqueConsumo.getAllByAtaqueId(this.id);
    }
    
    
    //------------------[Ações]------------------------------------------//
    public String rolar(InfoMagia magica) throws NotEnoughItemsException, UnequippedDependencyException,
            NotEnoughMagicSlotsException {
        if (this.dependEquip != null && !this.dependEquip.getEstaEquipado())
            throw new UnequippedDependencyException("Equipe " + this.dependEquip.getNome() + " para usar esse ataque.");
        
        for (var itemConsumo : this.dependItens) {
            ItemConsumivel itemAlvo = itemConsumo.getItem();
            System.out.printf("Quantidade antes do uso:%f", itemAlvo.getQuantidade());
            try {
                itemAlvo.spend(itemConsumo.getQuantidade());
                System.out.printf("Quantidade depois do uso:%f", itemAlvo.getQuantidade());
            } catch (IllegalArgumentException e) {
                throw new NotEnoughItemsException("Não há " + itemAlvo.getNome() + " o suficiente para esse ataque.");
            }
        }
        
        if (this.nivelMagico != 0) {
            try {
                magica.gastarUsoRestantesPorNivel(this.nivelMagico);
                magica.saveRemainingStats();
            } catch (IllegalArgumentException e) {
                throw new NotEnoughMagicSlotsException("Não há slots de magia o suficiente para esse ataque.");
            }
        }
        
        // Feito aqui para evitar consumo parcial de itens quando não houver o suficiente de um deles
        for (var itemConsumo : this.dependItens) {
            itemConsumo.getItem().save();
        }
        
        Random gerador = new Random();
        ArrayList<String> resultados = new ArrayList<>();
        int acc = 0;
        
        for (int i = 0; i < this.numeroDado; i++) {
            int resultado = gerador.nextInt(this.tipoDado) + 1;
            resultados.add(String.valueOf(resultado));
            acc += resultado;
        }
        
        acc += bonus;
        
        String resultadosStr = String.join(", ", resultados);
        return String.format("%d ([%s]%+d)", acc, resultadosStr, this.bonus);
        
    }
    
    //------------------[Overrides]--------------------------------------//
    @Override
    public String toString() {
        return String.format("%s (%dd%d%+d)", nome, numeroDado, tipoDado, bonus);
    }
}