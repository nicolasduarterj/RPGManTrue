/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nduarte.rpgmantrue.controllers;

import com.nduarte.rpgmantrue.models.Equipamento;
import com.nduarte.rpgmantrue.models.SelectedCharacter;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

/**
 *
 * @author nduarte
 */
public class CharEquipEquipmentController extends CharManagementController {
    
    @FXML
    private ListView equipList;
    
    @FXML
    public void initialize() {
        ObservableList<Equipamento> observableEquips = FXCollections.observableList(
                (List) SelectedCharacter.get().getEquipamentos()
        );
        
        equipList.setItems(observableEquips);
    }
    
    @FXML
    public void toggleEquip() {
        Equipamento selected = (Equipamento) equipList.getSelectionModel().getSelectedItem();
        
        if (selected == null) {
            Alert err404 = new Alert(Alert.AlertType.ERROR);
            err404.setHeaderText("Erro!");
            err404.setContentText("Você deve selecionar um equipamento para equipar / desequipar.");
            err404.showAndWait();
            return;
        }
        
        selected.toggleEstaEquipado();
        selected.save();
        
        Alert ok = new Alert(Alert.AlertType.INFORMATION);
        ok.setHeaderText("Salvo!");
        ok.setContentText(selected.getNome() 
                + " foi "
                + (selected.getEstaEquipado() ? "equipado" : "desequipado")
                + " com sucesso."
                );
        ok.showAndWait();
        initialize();
    }
}
