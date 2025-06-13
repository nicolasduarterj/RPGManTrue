/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.nduarte.rpgmantrue.controllers;

import com.nduarte.rpgmantrue.models.Personagem;
import com.nduarte.rpgmantrue.models.SelectedCharacter;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
/**
 * FXML Controller class
 *
 * @author nduarte
 */
public class CharMainController extends CharManagementController {

    @FXML
    private TextField nomeInput;
    
    @FXML
    private ComboBox classeInput;
    
    @FXML
    private TextField nivelInput;
    
    @FXML
    private TextField hpMaxInput;
    
    @FXML
    private TextField hpInput;
    
    @FXML
    private TextField hpTempInput;

    @FXML
    public void initialize() {
        nomeInput.setText(SelectedCharacter.get().getNome());
        classeInput.getItems().removeAll();
        classeInput.getItems().addAll(Personagem.classesValidas);
        classeInput.getSelectionModel().select(SelectedCharacter.get().getClasse());
        nivelInput.setText(String.valueOf(SelectedCharacter.get().getNivel()));
        hpMaxInput.setText(String.valueOf(SelectedCharacter.get().getHPMax()));
        hpInput.setText(String.valueOf(SelectedCharacter.get().getHP()));
        hpTempInput.setText(String.valueOf(SelectedCharacter.get().getHPTemp()));
        
    }
    
    @FXML
    public void saveStats() {
        try {
            SelectedCharacter.get().setName(nomeInput.getText());
            SelectedCharacter.get().setClasse((String) classeInput.getValue());
            SelectedCharacter.get().setNivel(Integer.parseInt(nivelInput.getText()));
            SelectedCharacter.get().setMaxHp(Integer.parseInt(hpMaxInput.getText()));
            SelectedCharacter.get().setHp(Integer.parseInt(hpInput.getText()));
            SelectedCharacter.get().setTempHp(Integer.parseInt(hpTempInput.getText()));
            
            SelectedCharacter.get().saveBasicStats();
            
            Alert okAlert = new Alert(Alert.AlertType.INFORMATION);
            okAlert.setHeaderText("Salvo!");
            okAlert.setContentText("Valores salvos com sucesso!");
            okAlert.showAndWait();
        } catch (IllegalArgumentException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Valor inválido!");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        } catch (SQLException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Erro ao salvar!");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }
    }
    
}
