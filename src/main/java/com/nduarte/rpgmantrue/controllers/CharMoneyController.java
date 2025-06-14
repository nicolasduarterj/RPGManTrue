/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nduarte.rpgmantrue.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import com.nduarte.rpgmantrue.models.SelectedCharacter;
import java.sql.SQLException;
import javafx.scene.control.Alert;

/**
 *
 * @author nduarte
 */
public class CharMoneyController extends CharManagementController {
    
    @FXML
    private TextField pecasCobreInput;
    
    @FXML
    private TextField pecasPrataInput;
    
    @FXML
    private TextField pecasOuroInput;
    
    @FXML
    private TextField pecasPlatinaInput;
    
    @FXML
    public void initialize() {
        pecasCobreInput.setText(String.valueOf(SelectedCharacter.get().getPecasCobre()));
        pecasPrataInput.setText(String.valueOf(SelectedCharacter.get().getPecasPrata()));
        pecasOuroInput.setText(String.valueOf(SelectedCharacter.get().getPecasOuro()));
        pecasPlatinaInput.setText(String.valueOf(SelectedCharacter.get().getPecasPlatina()));
    }
    
    @FXML
    public void saveMoneyStats() {
        try {
            SelectedCharacter.get().setPecasCobre(Integer.parseInt(pecasCobreInput.getText()));
            SelectedCharacter.get().setPecasPrata(Integer.parseInt(pecasPrataInput.getText()));
            SelectedCharacter.get().setPecasOuro(Integer.parseInt(pecasOuroInput.getText()));
            SelectedCharacter.get().setPecasPlatina(Integer.parseInt(pecasPlatinaInput.getText()));
            
            SelectedCharacter.get().saveBasicStats();
            Alert okAlert = new Alert(Alert.AlertType.INFORMATION);
            okAlert.setHeaderText("Salvo!");
            okAlert.setContentText("Informações monetárias salvas.");
            okAlert.showAndWait();
        } catch (NumberFormatException e) {
            Alert errAlert = new Alert(Alert.AlertType.ERROR);
            errAlert.setHeaderText("Erro ao salvar!");
            errAlert.setContentText("Todos os valores devem ser números inteiros.");
            errAlert.showAndWait();
        } catch (SQLException e) {
            Alert errAlert = new Alert(Alert.AlertType.ERROR);
            errAlert.setHeaderText("Erro ao salvar!");
            errAlert.setContentText("Ocorreu um erro desconhecido: " + e.getMessage());
            errAlert.showAndWait();
        }
    }
}
