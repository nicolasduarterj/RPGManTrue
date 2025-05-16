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

/**
 * FXML Controller class
 *
 * @author nduarte
 */
public class NovoPersonagemController {

    @FXML
    private TextField nomeInput;
    
    
    @FXML
    private Label nomeInputCounterLabel;
    
    @FXML
    private TextField nivelInput;
    
    @FXML 
    private Label nivelInputValidadeLabel;
    
    
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
                nivelInputValidadeLabel.setTextFill(Paint.valueOf("c40055"));
            }
            else {
                nivelInputValidadeLabel.setText("Vál.");
                nivelInputValidadeLabel.setTextFill(Paint.valueOf("#00c452"));
            }
        } catch (Exception ee) {
                nivelInputValidadeLabel.setText("Inv.");
                nivelInputValidadeLabel.setTextFill(Paint.valueOf("c40055"));
        }
    }
}
