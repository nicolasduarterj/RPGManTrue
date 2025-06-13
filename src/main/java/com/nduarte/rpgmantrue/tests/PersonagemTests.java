/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nduarte.rpgmantrue.tests;

import com.nduarte.rpgmantrue.database.DatabaseInitializationException;
import com.nduarte.rpgmantrue.database.MainSQLiteConnection;
import com.nduarte.rpgmantrue.models.NameIdRecord;
import com.nduarte.rpgmantrue.models.Personagem;
import java.util.Random;
/**
 *
 * @author nduarte
 */
public class PersonagemTests {
    public static boolean createCharTest() {
        try {
            var a = Personagem.createChar("Iberai", "Warlock", 9, 200);
            System.out.println(a);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause().getMessage());
            return false;
        }
    }
    
    public static void main(String[] args) throws Exception {
        MainSQLiteConnection.initConnection();
        MainSQLiteConnection.initTables();
        System.out.println("Personagens:");
        for (NameIdRecord personagem : Personagem.getAllChars()) {
            System.out.println(personagem);
        }
        
        Personagem per = Personagem.fromId(1);
        System.out.println("Nivel antigo:" + String.valueOf(per.getNivel()));
        Random r = new Random();
        per.setNivel(r.nextInt(20));
        System.out.println("Nivel novo:" + String.valueOf(per.getNivel()));
        per.saveBasicStats();
    }
}
