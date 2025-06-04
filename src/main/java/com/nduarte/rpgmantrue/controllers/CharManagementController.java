/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nduarte.rpgmantrue.controllers;

import javafx.fxml.FXML;
import com.nduarte.rpgmantrue.App;
import java.io.IOException;

/**
 *
 * @author nduarte
 */
public class CharManagementController {
    
    @FXML
    public void switchToMoney() throws IOException {
        App.setRoot("CharMoney");
    }
    
    @FXML
    public void switchToMain() throws IOException {
        App.setRoot("CharMain");
    }
    
    @FXML
    public void switchToMagicMax() throws IOException {
        App.setRoot("CharMagicMax");
    }
    
    @FXML
    public void switchToMagic() throws IOException {
        App.setRoot("CharMagic");
    }
    
    @FXML
    public void switchToCreateAttack() throws IOException {
        App.setRoot("CharCreateAttack");
    }
    
    @FXML
    public void switchToUseAttack() throws IOException {
        App.setRoot("CharUseAttack");
    }
    
    @FXML
    public void switchToCreateEquip() throws IOException {
        App.setRoot("CharCreateEquip");
    }
    
    @FXML
    public void switchToEquipEquip() throws IOException {
        App.setRoot("CharEquipEquipment");
    }
    
    @FXML
    public void switchToCreateItem() throws IOException {
        App.setRoot("CharCreateItem");
    }
    
    @FXML
    public void switchToConsumeItem() throws IOException {
        App.setRoot("CharConsumeItem");
    }
    
}
