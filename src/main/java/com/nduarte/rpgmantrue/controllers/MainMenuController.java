/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.nduarte.rpgmantrue.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import com.nduarte.rpgmantrue.App;
import com.nduarte.rpgmantrue.models.Personagem;
import java.io.IOException;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author nduarte
 */
public class MainMenuController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    @FXML
    public void createChar() throws IOException {
        App.setRoot("ResponsiveNovoPersonagem");
    }
    
    @FXML
    public void selectChar() throws IOException {
        if (Personagem.getAllChars().isEmpty()) {
            Alert emptyCharListWarning = new Alert(Alert.AlertType.ERROR);
            emptyCharListWarning.setHeaderText("Nenhum personagem encontrado.");
            emptyCharListWarning.setContentText("Crie um personagem antes de utilizar esta função.");
            emptyCharListWarning.showAndWait();
            return;
        }
        App.setRoot("CharSelect");
    }
    
}
