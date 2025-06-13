/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nduarte.rpgmantrue.models;

/**
 *
 * @author nduarte
 */
public abstract class SelectedCharacter {
    public static Personagem selected;
    
    public static void select(Personagem x) {
        selected = x;
    }
    
    public static Personagem get() {
        return selected;
    }
}
