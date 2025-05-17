/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.nduarte.rpgmantrue.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import java.util.HashSet;
import java.util.Arrays;
import com.nduarte.rpgmantrue.App;
import java.io.IOException;

/**
 * FXML Controller class
 *
 * @author nduarte
 */
public class NovoPersonagemController {

    static final String[] classesArr = {"Bárbaro", "Bardo", "Bruxo", "Clérigo", 
            "Druida", "Feiticeiro", "Guerreiro", "Ladino", "Mago", "Monge", 
            "Paladino", "Patrulheiro"};
    static final HashSet<String> classesValidas = new HashSet<>(Arrays.asList(classesArr));
    static final Paint corValida = Paint.valueOf("#00c452");
    static final Paint corInvalida = Paint.valueOf("#c40055");
    
    @FXML
    private TextField nomeInput;
    
    @FXML
    private Label nomeInputCounterLabel;
    
    @FXML
    private TextField nivelInput;
    
    @FXML 
    private Label nivelInputValidadeLabel;
    
    @FXML
    private TextField classeInput;
    
    @FXML
    private Label classeInputValidadeLabel;
    
    @FXML
    private TextField hpInput;
    
    @FXML
    private Label hpInputValidadeLabel;
    
    @FXML
    private void processNameInput(KeyEvent e) {
        String nome = nomeInput.getText();
        int len = nome.length();
        nomeInputCounterLabel.setTextFill(len < 1 || len > 50 
                ? Paint.valueOf("#c40055") 
                : Paint.valueOf("#00c452"));
        String labelStr = String.format("(%d/50)", len);
        nomeInputCounterLabel.setText(labelStr);
    }
    
    @FXML
    private void processLevelInput(KeyEvent e) {
        String levelStr = nivelInput.getText();
        try {
            int nivel = Integer.valueOf(levelStr);
            if (nivel < 1 || nivel > 20) {
                nivelInputValidadeLabel.setText("Inv.");
                nivelInputValidadeLabel.setTextFill(corInvalida);
            }
            else {
                nivelInputValidadeLabel.setText("Vál.");
                nivelInputValidadeLabel.setTextFill(corValida);
            }
        } catch (Exception ee) {
                nivelInputValidadeLabel.setText("Inv.");
                nivelInputValidadeLabel.setTextFill(corInvalida);
        }
    }
    
    @FXML
    private void processClassInput(KeyEvent e) {
       String classe = classeInput.getText();
       if (classesValidas.contains(classe)) {
                classeInputValidadeLabel.setText("Vál.");
                classeInputValidadeLabel.setTextFill(corValida);
       } else {
                classeInputValidadeLabel.setText("Inv.");
                classeInputValidadeLabel.setTextFill(corInvalida);
       }
    }
    
    @FXML
    private void processHp(KeyEvent e) {
        try {
            int hp = Integer.valueOf(hpInput.getText());
            if (hp < 0) throw new IllegalArgumentException("Hp deve ser maior que 0");
            
            hpInputValidadeLabel.setText("Vál.");
            hpInputValidadeLabel.setTextFill(corValida);
        } catch (Exception ee) {
            hpInputValidadeLabel.setText("Inv.");
            hpInputValidadeLabel.setTextFill(corInvalida);
        }
    }
    
    @FXML
    private void cancelCreation() throws IOException {
        App.setRoot("MainMenu");
    }
}
