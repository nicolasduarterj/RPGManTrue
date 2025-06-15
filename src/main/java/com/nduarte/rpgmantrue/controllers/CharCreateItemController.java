/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nduarte.rpgmantrue.controllers;

import com.nduarte.rpgmantrue.models.ItemConsumivel;
import com.nduarte.rpgmantrue.models.SelectedCharacter;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 *
 * @author nduarte
 */
public class CharCreateItemController extends CharManagementController {
    
    @FXML
    private TextField nomeInput;
    
    @FXML
    private TextField quantidadeInput;
    
    @FXML
    private void saveItem() {
        try {
            String nome = nomeInput.getText();
            double quantidade = Double.parseDouble(quantidadeInput.getText());
            
            ItemConsumivel.initialize(
                    SelectedCharacter.get().getId(), 
                    nome, 
                    quantidade);
            
            SelectedCharacter.get().refreshItens();
            Alert ok = new Alert(Alert.AlertType.INFORMATION);
            ok.setHeaderText("Salvo!");
            ok.setContentText("Item " + nome + " foi salvo com sucesso.");
            ok.showAndWait();
        } catch (NumberFormatException e) {
            Alert numErr = new Alert(Alert.AlertType.ERROR);
            numErr.setHeaderText("Erro ao salvar!");
            numErr.setContentText("A quantidade deve ser um número.");
            numErr.showAndWait();
        } catch (IllegalArgumentException e) {
            Alert illErr = new Alert(Alert.AlertType.ERROR);
            illErr.setHeaderText("Erro ao salvar!");
            illErr.setContentText(e.getMessage());
            illErr.showAndWait();
        } catch (SQLException e) {
            Alert sqlErr = new Alert(Alert.AlertType.ERROR);
            sqlErr.setHeaderText("Erro ao salvar!");
            sqlErr.setContentText("Este personagem já possui um item com esse nome.");
            sqlErr.showAndWait();
        }
    }
}
