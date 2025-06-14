/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nduarte.rpgmantrue.controllers;

import com.nduarte.rpgmantrue.models.SelectedCharacter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 *
 * @author nduarte
 */
public class CharMagicMaxController extends CharManagementController {
    
    @FXML
    TextField nivel1Label;
    
    @FXML
    TextField nivel2Label;
    
    @FXML
    TextField nivel3Label;
    
    @FXML
    TextField nivel4Label;
    
    @FXML
    TextField nivel5Label;
    
    @FXML
    TextField nivel6Label;
    
    @FXML
    TextField nivel7Label;
    
    @FXML
    TextField nivel8Label;
    
    @FXML
    TextField nivel9Label;
    
    @FXML
    public void initialize() {
        nivel1Label.setText(String.valueOf(SelectedCharacter.get().getInfoMagica().getMaxUsosPorNivel(1)));
        nivel2Label.setText(String.valueOf(SelectedCharacter.get().getInfoMagica().getMaxUsosPorNivel(2)));
        nivel3Label.setText(String.valueOf(SelectedCharacter.get().getInfoMagica().getMaxUsosPorNivel(3)));
        nivel4Label.setText(String.valueOf(SelectedCharacter.get().getInfoMagica().getMaxUsosPorNivel(4)));
        nivel5Label.setText(String.valueOf(SelectedCharacter.get().getInfoMagica().getMaxUsosPorNivel(5)));
        nivel6Label.setText(String.valueOf(SelectedCharacter.get().getInfoMagica().getMaxUsosPorNivel(6)));
        nivel7Label.setText(String.valueOf(SelectedCharacter.get().getInfoMagica().getMaxUsosPorNivel(7)));
        nivel8Label.setText(String.valueOf(SelectedCharacter.get().getInfoMagica().getMaxUsosPorNivel(8)));
        nivel9Label.setText(String.valueOf(SelectedCharacter.get().getInfoMagica().getMaxUsosPorNivel(9)));
    }
    
    public void saveMaxStats() {
        try {
            SelectedCharacter.get().getInfoMagica().setMaxUsosPorNivel(1, Integer.parseInt(nivel1Label.getText()));
            SelectedCharacter.get().getInfoMagica().setMaxUsosPorNivel(2, Integer.parseInt(nivel2Label.getText()));
            SelectedCharacter.get().getInfoMagica().setMaxUsosPorNivel(3, Integer.parseInt(nivel3Label.getText()));
            SelectedCharacter.get().getInfoMagica().setMaxUsosPorNivel(4, Integer.parseInt(nivel4Label.getText()));
            SelectedCharacter.get().getInfoMagica().setMaxUsosPorNivel(5, Integer.parseInt(nivel5Label.getText()));
            SelectedCharacter.get().getInfoMagica().setMaxUsosPorNivel(6, Integer.parseInt(nivel6Label.getText()));
            SelectedCharacter.get().getInfoMagica().setMaxUsosPorNivel(7, Integer.parseInt(nivel7Label.getText()));
            SelectedCharacter.get().getInfoMagica().setMaxUsosPorNivel(8, Integer.parseInt(nivel8Label.getText()));
            SelectedCharacter.get().getInfoMagica().setMaxUsosPorNivel(9, Integer.parseInt(nivel9Label.getText()));
            
            SelectedCharacter.get().getInfoMagica().saveMaxStats();
            
            Alert ok = new Alert(Alert.AlertType.INFORMATION);
            ok.setHeaderText("Salvo!");
            ok.setContentText("Informações mágicas salvas!");
            ok.showAndWait();
        } catch (Exception e) {
            Alert numErr = new Alert(Alert.AlertType.ERROR);
            numErr.setHeaderText("Erro no salvamento!");
            numErr.setContentText(e.getMessage());
            numErr.showAndWait();
        }       
    }
}
