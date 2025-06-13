/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.nduarte.rpgmantrue.controllers;

import com.nduarte.rpgmantrue.App;
import com.nduarte.rpgmantrue.models.NameIdRecord;
import com.nduarte.rpgmantrue.models.Personagem;
import com.nduarte.rpgmantrue.models.SelectedCharacter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author nduarte
 */
public class CharSelectController {


    @FXML
    ListView charsList;
    
    @FXML
    public void initialize() {
        ArrayList<NameIdRecord> chars = Personagem.getAllChars();
        ObservableList<NameIdRecord> observableChars = FXCollections.observableList((List) chars);
        charsList.setItems(observableChars);
    }    
    
    @FXML
    public void charMain() throws IOException {
        NameIdRecord selectedCharTag = (NameIdRecord) charsList.getSelectionModel().getSelectedItem();
        System.out.println(selectedCharTag);
        
        if (selectedCharTag == null) {
            var noCharSelectedAlert = new Alert(Alert.AlertType.ERROR);
            noCharSelectedAlert.setHeaderText("Você deve selecionar um personagem antes de prosseguir.");
            noCharSelectedAlert.setContentText("Selecione um personagem com o botão esquerdo do mouse.");
            noCharSelectedAlert.showAndWait();
            return;
        }
        
        SelectedCharacter.select(Personagem.fromId(selectedCharTag.id));
        App.setRoot("CharMain");
        System.out.println(SelectedCharacter.get());
    }
    
    @FXML
    public void mainMenu() throws IOException {
        App.setRoot("MainMenu");
    }
}
