/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nduarte.rpgmantrue.controllers;

import com.nduarte.rpgmantrue.models.Equipamento;
import com.nduarte.rpgmantrue.models.SelectedCharacter;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 *
 * @author nduarte
 */
public class CharCreateEquipController extends CharManagementController {
    
    @FXML
    private TextField nomeInput;
    
    @FXML
    public void createEquip() {
        try {
            Equipamento newEquip = Equipamento.initialize(
                    SelectedCharacter.get().getId(),
                    nomeInput.getText()
            );
            SelectedCharacter.get().refreshEquipamentos();
            
            Alert ok = new Alert(Alert.AlertType.INFORMATION);
            ok.setHeaderText("Salvo!");
            ok.setContentText("Equipamento" + newEquip.getNome() + " criado com sucesso!");
            ok.showAndWait();
        } catch (SQLException e) {
            Alert repErr = new Alert(Alert.AlertType.ERROR);
            repErr.setHeaderText("Erro ao salvar!");
            repErr.setContentText("Já existe um item com esse nome!");
            repErr.showAndWait();
        }
    }
}
