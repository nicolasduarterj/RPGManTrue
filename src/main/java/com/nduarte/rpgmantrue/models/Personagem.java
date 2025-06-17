/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nduarte.rpgmantrue.models;


import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import com.nduarte.rpgmantrue.database.MainSQLiteConnection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

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
    private InfoMagia infoMagica;
    private ArrayList<ItemConsumivel> inventario;
    private ArrayList<Equipamento> equipamentos;
    
    public static final String[] classesArr = {"Bárbaro", "Bardo", "Bruxo", "Clérigo", 
        "Druida", "Feiticeiro", "Guerreiro", "Ladino", "Mago", "Monge", 
        "Paladino", "Patrulheiro"};
    public static final HashSet<String> classesValidas = new HashSet<>(Arrays.asList(classesArr));
    
    //----------------[Construtores e factories]-----------------------------//
    
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
        
        Personagem charCriado;
        
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
            
            charCriado = new Personagem(
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
            
            charCriado.setInfoMagica(InfoMagia.initialize(charCriado.getId()));
            return charCriado;
        } catch(SQLException e) {
            throw e;
        }
    }
    
    public static Personagem fromId(int id) {
        String sql = "SELECT * FROM Personagens WHERE Id = ?;";
        
        try {
            PreparedStatement stmtFind = MainSQLiteConnection.getConn()
                    .prepareStatement(sql);
            
            stmtFind.setInt(1, id);
            
            ResultSet rs = stmtFind.executeQuery();
            
            Personagem charCriado = new Personagem(
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
            
            charCriado.setInfoMagica(InfoMagia.fromId(id));
            charCriado.refreshItens();
            charCriado.refreshEquipamentos();
            return charCriado;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    //--------------------[Getters e setters]---------------------------------------//
    
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getClasse() { return classe; }
    public int getNivel() { return nivel; }
    public int getHP() { return hp; }
    public int getHPMax() { return hpMax; }
    public int getHPTemp() { return hpTemp; }
    public int getPecasCobre() { return pecasCobre; }
    public int getPecasPrata() { return pecasPrata; }
    public int getPecasOuro() { return pecasOuro; }
    public int getPecasPlatina() { return pecasPlatina; }
    public InfoMagia getInfoMagica() { return infoMagica; }
    public ArrayList<ItemConsumivel> getInventario() { return inventario; }
    public ArrayList<Equipamento> getEquipamentos() { return equipamentos; }
    
    public void setName(String newName) throws IllegalArgumentException {
        if (!isValidName(newName)) {
            throw new IllegalArgumentException("O nome \"" + newName + "\" é invalido.");
        }
        
        this.nome = newName;
    }
    
    public void setClasse(String newClasse) throws IllegalArgumentException {
        if (!isValidClass(newClasse)) {
            throw new IllegalArgumentException("A classe " + newClasse + " é inválida.");
        }
        
        this.classe = newClasse;
    }
    
    public void setNivel(int newNivel) {
        if (!isValidLevel(newNivel)) {
            throw new IllegalArgumentException("O nível " + String.valueOf(newNivel) + " é inválido.");
        }
        
        this.nivel = newNivel;
    }
    
    public void setHp(int newHp) {
        if (!isValidHP(newHp)) {
            throw new IllegalArgumentException("O Hp " + String.valueOf(newHp) + " é inválido.");
        }
        
        this.hp = newHp;
    }
    
    public void setTempHp(int newHp) {
        if (!isValidHP(newHp)) {
            throw new IllegalArgumentException("O Hp " + String.valueOf(newHp) + " é inválido.");
        }
        
        this.hpTemp = newHp;
    }
    
    public void setMaxHp(int newHp) {
        if (!isValidHP(newHp)) {
            throw new IllegalArgumentException("O Hp " + String.valueOf(newHp) + " é inválido.");
        }
        
        this.hpMax = newHp;
    }
    
    public void setPecasCobre(int newValor) {
        this.pecasCobre = newValor;
    }
    
    public void setPecasPrata(int newValor) {
        this.pecasPrata = newValor;
    }
    
    public void setPecasOuro(int newValor) {
        this.pecasOuro = newValor;
    }
    
    public void setPecasPlatina(int newValor) {
        this.pecasPlatina = newValor;
    }
    
    public void setInfoMagica(InfoMagia newMagica) {
        this.infoMagica = newMagica;
    }
    
    public void refreshItens() {
        this.inventario = ItemConsumivel.getAllByDonoId(this.id);
    }
    
    public void refreshEquipamentos() {
        this.equipamentos = Equipamento.getAllByDonoId(this.id);
    }
    
    //--------------------[Salvamento]-----------------------------------------------//
    
    public void saveBasicStats() throws SQLException {
        String sql = "INSERT OR REPLACE INTO Personagens("
                + "Id,"
                + "Nome,"
                + "Classe,"
                + "Nivel,"
                + "HPMax,"
                + "HP,"
                + "HPTemp,"
                + "PecasCobre,"
                + "PecasPrata,"
                + "PecasOuro,"
                + "PecasPlatina) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
        
        PreparedStatement stmt = MainSQLiteConnection.getConn().prepareStatement(sql);
        stmt.setInt(1, this.id);
        stmt.setString(2, this.nome);
        stmt.setString(3, this.classe);
        stmt.setInt(4, this.nivel);
        stmt.setInt(5, this.hpMax);
        stmt.setInt(6, this.hp);
        stmt.setInt(7, this.hpTemp);
        stmt.setInt(8, this.pecasCobre);
        stmt.setInt(9, this.pecasPrata);
        stmt.setInt(10, this.pecasOuro);
        stmt.setInt(11, this.pecasPlatina);
            
        stmt.executeUpdate();
    }
    
    //--------------------[Overrides]------------------------------------------------//
    
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
    
    //--------------------[Validação]-------------------------------------------------//
    
    public static boolean isValidName(String x) {
        return x.length() > 0 && x.length() < 51;
    }
    
    public static boolean isValidClass(String x) {
        return classesValidas.contains(x);
    }
    
    public static boolean isValidLevel(int x) {
        return x > 0 && x < 21;
    }
    
    public static boolean isValidHP(int hp) {
        return hp >= 0;
    }
    
    //----------------------[Estáticos plurais]--------------------------------------//
    
    public static ArrayList<NameIdRecord> getAllChars() {
        String sql = "SELECT Nome, Id FROM Personagens;";
        
        try {
            Statement retrieveStmt = MainSQLiteConnection.getConn().createStatement();
            ResultSet rs = retrieveStmt.executeQuery(sql);
            
            ArrayList<NameIdRecord> results = new ArrayList<>();
            int i = 0;
            
            while (rs.next()) {
                results.add(new NameIdRecord(rs.getString("Nome"), rs.getInt("Id")));
                i++;
            }
            
            return results;
        } catch (SQLException e) {
            System.out.println("SQLEXCEPTION " + e.getMessage());
            return new ArrayList<NameIdRecord>();
        }
    }
}
