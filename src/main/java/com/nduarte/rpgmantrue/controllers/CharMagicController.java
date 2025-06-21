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
public class CharMagicController extends CharManagementController {
    
    @FXML
    TextField nivel1Input;
    
    @FXML
    TextField nivel2Input;
    
    @FXML
    TextField nivel3Input;
    
    @FXML
    TextField nivel4Input;
    
    @FXML
    TextField nivel5Input;
    
    @FXML
    TextField nivel6Input;
    
    @FXML
    TextField nivel7Input;
    
    @FXML
    TextField nivel8Input;
    
    @FXML
    TextField nivel9Input;
    
    @FXML
    public void initialize() {
        nivel1Input.setText(String.valueOf(SelectedCharacter.get().getInfoMagica().getUsosRestantesPorNivel(1)));
        nivel2Input.setText(String.valueOf(SelectedCharacter.get().getInfoMagica().getUsosRestantesPorNivel(2)));
        nivel3Input.setText(String.valueOf(SelectedCharacter.get().getInfoMagica().getUsosRestantesPorNivel(3)));
        nivel4Input.setText(String.valueOf(SelectedCharacter.get().getInfoMagica().getUsosRestantesPorNivel(4)));
        nivel5Input.setText(String.valueOf(SelectedCharacter.get().getInfoMagica().getUsosRestantesPorNivel(5)));
        nivel6Input.setText(String.valueOf(SelectedCharacter.get().getInfoMagica().getUsosRestantesPorNivel(6)));
        nivel7Input.setText(String.valueOf(SelectedCharacter.get().getInfoMagica().getUsosRestantesPorNivel(7)));
        nivel8Input.setText(String.valueOf(SelectedCharacter.get().getInfoMagica().getUsosRestantesPorNivel(8)));
        nivel9Input.setText(String.valueOf(SelectedCharacter.get().getInfoMagica().getUsosRestantesPorNivel(9)));
    }
    
    public void saveRemainingStats() {
        try {
            SelectedCharacter.get().getInfoMagica().setUsosRestantesPorNivel(1, Integer.parseInt(nivel1Input.getText()));
            SelectedCharacter.get().getInfoMagica().setUsosRestantesPorNivel(2, Integer.parseInt(nivel2Input.getText()));
            SelectedCharacter.get().getInfoMagica().setUsosRestantesPorNivel(3, Integer.parseInt(nivel3Input.getText()));
            SelectedCharacter.get().getInfoMagica().setUsosRestantesPorNivel(4, Integer.parseInt(nivel4Input.getText()));
            SelectedCharacter.get().getInfoMagica().setUsosRestantesPorNivel(5, Integer.parseInt(nivel5Input.getText()));
            SelectedCharacter.get().getInfoMagica().setUsosRestantesPorNivel(6, Integer.parseInt(nivel6Input.getText()));
            SelectedCharacter.get().getInfoMagica().setUsosRestantesPorNivel(7, Integer.parseInt(nivel7Input.getText()));
            SelectedCharacter.get().getInfoMagica().setUsosRestantesPorNivel(8, Integer.parseInt(nivel8Input.getText()));
            SelectedCharacter.get().getInfoMagica().setUsosRestantesPorNivel(9, Integer.parseInt(nivel9Input.getText()));

            SelectedCharacter.get().getInfoMagica().saveRemainingStats();

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
    
    public void renewStats() {
        SelectedCharacter.get().getInfoMagica().restaurarUsosRestantes();
        SelectedCharacter.get().getInfoMagica().saveRemainingStats();
        
        Alert ok = new Alert(Alert.AlertType.INFORMATION);
        ok.setHeaderText("Restaurado!");
        ok.setContentText("Slots de magia restaurados ao máximo.");
        ok.showAndWait();
        initialize();
    }
}
