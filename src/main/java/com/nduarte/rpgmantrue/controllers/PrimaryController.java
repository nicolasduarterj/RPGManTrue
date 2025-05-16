package com.nduarte.rpgmantrue.controllers;

import com.nduarte.rpgmantrue.App;
import java.io.IOException;
import javafx.fxml.FXML;
import com.nduarte.rpgmantrue.tests.PersonagemTests;
import javafx.scene.control.Alert;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        if (PersonagemTests.createCharTest()) {
            var a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Foi");
            a.setContentText("Personagem criado com sucesso");
            a.showAndWait();
        }
        else {
            var a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Não foi");
            a.setContentText("Erro na criação");
            a.showAndWait();
        }
    }
}
