/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nduarte.rpgmantrue.tests;

import com.nduarte.rpgmantrue.models.Personagem;
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
}
