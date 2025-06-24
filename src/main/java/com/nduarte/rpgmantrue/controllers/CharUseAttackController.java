/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nduarte.rpgmantrue.controllers;

import com.nduarte.rpgmantrue.models.Ataque;
import com.nduarte.rpgmantrue.models.SelectedCharacter;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

/**
 *
 * @author nduarte
 */
public class CharUseAttackController extends CharManagementController {
    
    @FXML
    private ListView attackList;
    
    @FXML
    public void initialize() {
        attackList.setItems(FXCollections.observableList(
                (List) SelectedCharacter.get().getAtaques())
        );
    }
    
    @FXML
    public void useAttack() {
        Ataque selected = (Ataque) attackList.getSelectionModel().getSelectedItem();
        
        if (selected == null) {
            Alert err404 = new Alert(Alert.AlertType.ERROR);
            err404.setHeaderText("Erro!");
            err404.setContentText("Selecione um ataque para usar.");
            err404.showAndWait();
            return;
        }
        
        try {
            String result = selected.rolar(SelectedCharacter.get().getInfoMagica());
            Alert ok = new Alert(Alert.AlertType.INFORMATION);
            ok.setHeaderText("Ataque " + selected.getNome() + " rolado.");
            ok.setContentText("Resultado: " + result);
            ok.showAndWait();
        } catch (Exception e) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setHeaderText("Erro!");
            err.setContentText(e.getMessage());
            err.showAndWait();
        }
    }
}
