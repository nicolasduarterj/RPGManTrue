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
import javafx.scene.control.Alert;
import com.nduarte.rpgmantrue.models.Personagem;
import java.sql.SQLException;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author nduarte
 */
public class NovoPersonagemController {


    static final Paint corValida = Paint.valueOf("#00c452");
    static final Paint corInvalida = Paint.valueOf("#c40055");
    
    private String nome;
    private String classe;
    private int nivel;
    private int hpMax;
    
    private boolean nomeValido = false;
    private boolean nivelValido = false;
    private boolean hpMaxValido = false;
    
    @FXML
    private TextField nomeInput;
    
    @FXML
    private Label nomeInputCounterLabel;
    
    @FXML
    private TextField nivelInput;
    
    @FXML 
    private Label nivelInputValidadeLabel;
    
    @FXML
    private ComboBox classeInput;
    
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
        String labelStr = String.format("(%d/50)", len);
        nomeInputCounterLabel.setText(labelStr);
       
        if (Personagem.isValidName(nome)) {
            nomeInputCounterLabel.setTextFill(corValida);
            this.nome = nome;
            this.nomeValido = true;
        }
        else {
            nomeInputCounterLabel.setTextFill(corInvalida);
            this.nome = "";
            this.nomeValido = false;
        }
    }
    
    @FXML
    private void processLevelInput(KeyEvent e) {
        String levelStr = nivelInput.getText();
        try {
            int nivel = Integer.valueOf(levelStr);
            if (!Personagem.isValidLevel(nivel)) {
                nivelInputValidadeLabel.setText("Inv.");
                nivelInputValidadeLabel.setTextFill(corInvalida);
                this.nivelValido = false;
            }
            else {
                nivelInputValidadeLabel.setText("Vál.");
                nivelInputValidadeLabel.setTextFill(corValida);
                this.nivel = nivel;
                this.nivelValido = true;
            }
        } catch (Exception ee) {
                nivelInputValidadeLabel.setText("Inv.");
                nivelInputValidadeLabel.setTextFill(corInvalida);
                this.nivelValido = false;
        }
    }
    
    @FXML
    private void processHp(KeyEvent e) {
        try {
            int hp = Integer.valueOf(hpInput.getText());
            if (!Personagem.isValidHP(hp)) throw new IllegalArgumentException("Hp deve ser maior que 0");
            
            hpInputValidadeLabel.setText("Vál.");
            hpInputValidadeLabel.setTextFill(corValida);
            this.hpMaxValido = true;
            this.hpMax = hp;
        } catch (Exception ee) {
            hpInputValidadeLabel.setText("Inv.");
            hpInputValidadeLabel.setTextFill(corInvalida);
            this.hpMaxValido = false;
        }
    }
    
    @FXML
    private void cancelCreation() throws IOException {
        App.setRoot("MainMenu");
    }
    
    @FXML
    private void createChar() {
        boolean validade = this.nomeValido && this.nivelValido 
                && this.hpMaxValido;
        
        if (!validade) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Falha na criação de personagem.");
            a.setContentText("Algum dos parâmetros está inválido.");
            a.showAndWait();
            return;
        }
        
        this.classe = (String) classeInput.getValue();
        
        try {
            Personagem novoChar = Personagem.createChar(this.nome, 
                    this.classe, this.nivel, this.hpMax);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Personagem criado com sucesso.");
            a.setContentText(this.nome + " foi criado.");
            a.showAndWait();
            App.setRoot("CharSelect");
        } catch (IllegalArgumentException e) {
            if (e.getCause().getMessage().toLowerCase().contains("unique")) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Já existe um personagem com esse nome.");
                a.setContentText("Delete " + this.nome + " antes de criar outro.");
                a.showAndWait();
            }
            System.out.println(e.getCause().getMessage());
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Ocorreu um erro inesperado.");
            a.setContentText(e.getMessage());
            a.showAndWait();
        }
    }
    
    @FXML
    public void initialize() {
        classeInput.getItems().removeAll();
        classeInput.getItems().addAll(Personagem.classesValidas);
        classeInput.getSelectionModel().select("Guerreiro");
    }
}