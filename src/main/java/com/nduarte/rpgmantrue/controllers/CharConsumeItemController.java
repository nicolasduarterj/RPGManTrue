/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nduarte.rpgmantrue.controllers;

import com.nduarte.rpgmantrue.models.ItemConsumivel;
import com.nduarte.rpgmantrue.models.SelectedCharacter;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 *
 * @author nduarte
 */
public class CharConsumeItemController extends CharManagementController {
   
    @FXML
    private ListView listaItens;
    
    @FXML
    private TextField quantidadeInput;
    
    @FXML
    public void initialize() {
        ObservableList<ItemConsumivel> observableItens = FXCollections.observableList(
                (List) SelectedCharacter.get().getInventario()
        );
        
        listaItens.setItems(observableItens);
    }
    
    @FXML
    public void consumeItem() {
        try {
            double x = Double.parseDouble(quantidadeInput.getText());
            ItemConsumivel selected = (ItemConsumivel) listaItens.getSelectionModel().getSelectedItem();
            
            if (selected == null) {
                Alert err404 = new Alert(Alert.AlertType.ERROR);
                err404.setHeaderText("Erro!");
                err404.setContentText("Você deve selecionar um item na lista.");
                err404.showAndWait();
                return;
            }
            
            selected.spend(x);
            selected.save();
            Alert ok = new Alert(Alert.AlertType.INFORMATION);
            ok.setHeaderText("Item consumido!");
            ok.setContentText("Foram consumidos " + x + " unidades do item.");
            ok.showAndWait();
            initialize();
        } catch (NumberFormatException e) {
            Alert errFor = new Alert(Alert.AlertType.ERROR);
            errFor.setHeaderText("Erro!");
            errFor.setContentText("A quantidade deve ser um decimal válido.");
            errFor.showAndWait();
        } catch (IllegalArgumentException e) {
            Alert numErr = new Alert(Alert.AlertType.ERROR);
            numErr.setHeaderText("Erro!");
            numErr.setContentText(e.getMessage());
            numErr.showAndWait();
        }
    
    }
    public void addItem() {
        try {
            double x = Double.parseDouble(quantidadeInput.getText());
            ItemConsumivel selected = (ItemConsumivel) listaItens.getSelectionModel().getSelectedItem();
            
            if (selected == null) {
                Alert err404 = new Alert(Alert.AlertType.ERROR);
                err404.setHeaderText("Erro!");
                err404.setContentText("Você deve selecionar um item na lista.");
                err404.showAndWait();
                return;
            }
            
            selected.add(x);
            selected.save();
            Alert ok = new Alert(Alert.AlertType.INFORMATION);
            ok.setHeaderText("Item adicionado!");
            ok.setContentText("Foram adicionadas " + x + " unidades do item.");
            ok.showAndWait();
            initialize();
        } catch (NumberFormatException e) {
            Alert errFor = new Alert(Alert.AlertType.ERROR);
            errFor.setHeaderText("Erro!");
            errFor.setContentText("A quantidade deve ser um decimal válido!");
            errFor.showAndWait();
        }
    }
}
