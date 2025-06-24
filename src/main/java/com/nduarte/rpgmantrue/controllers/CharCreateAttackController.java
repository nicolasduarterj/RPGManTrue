/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nduarte.rpgmantrue.controllers;

import com.nduarte.rpgmantrue.models.Ataque;
import com.nduarte.rpgmantrue.models.AtaqueConsumo;
import com.nduarte.rpgmantrue.models.AtaqueConsumoBaseRecord;
import com.nduarte.rpgmantrue.models.Equipamento;
import com.nduarte.rpgmantrue.models.ItemConsumivel;
import com.nduarte.rpgmantrue.models.SelectedCharacter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 *
 * @author nduarte
 */
public class CharCreateAttackController extends CharManagementController {
    
    @FXML
    private TextField nomeInput;
    
    @FXML
    private TextField danoInput;
    
    @FXML
    private TextField nivelInput;
    
    @FXML
    private ChoiceBox dependEquipBox;
    
    @FXML
    private ListView itensDependList;
    
    public ArrayList<AtaqueConsumoBaseRecord> depends = new ArrayList<>();
    
    @FXML
    public void initialize() {
        ObservableList<Equipamento> observableEquips = FXCollections.observableList(
                (List) Equipamento.getAllByDonoId(SelectedCharacter.get().getId())
        );
        
        dependEquipBox.setItems(observableEquips);
        
        itensDependList.setItems(FXCollections.observableList(depends));
    }
    
    @FXML
    public void addDepend() {
        Dialog<Pair<ItemConsumivel, String>> addItemDialog = new Dialog<>();
        addItemDialog.setTitle("Adicionar consumo de item");
        addItemDialog.setHeaderText("Selecione um item a ser consumido.");
        
        addItemDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        ChoiceBox itensInput = new ChoiceBox();
        itensInput.setItems(FXCollections.observableList((List) ItemConsumivel.getAllByDonoId(SelectedCharacter.get().getId())));
        TextField quantidadeInput = new TextField();
        quantidadeInput.setPromptText("Quantidade");
        
        grid.add(new Label("Item:"), 0, 0);
        grid.add(itensInput, 1, 0);
        grid.add(new Label("Quantidade:"), 0, 1);
        grid.add(quantidadeInput, 1, 1);
        
        addItemDialog.getDialogPane().setContent(grid);
        
        addItemDialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new Pair<>(
                        (ItemConsumivel) itensInput.getSelectionModel().getSelectedItem(), 
                        quantidadeInput.getText()
                );
            }
            
            return null;
        });
        
        Optional<Pair<ItemConsumivel, String>> result = addItemDialog.showAndWait();
        
        try {
            Pair<ItemConsumivel, String> resultPair = result.get();
            double quantidade = Double.parseDouble(resultPair.getValue());
            ItemConsumivel item = resultPair.getKey();
            
            if (item == null) {
                Alert err404 = new Alert(Alert.AlertType.ERROR);
                err404.setHeaderText("Erro!");
                err404.setContentText("Selecione um item para adicionar.");
                err404.showAndWait();
                return;
            }
            
            depends.add(new AtaqueConsumoBaseRecord(item, quantidade));
            Alert ok = new Alert(Alert.AlertType.INFORMATION);
            ok.setHeaderText("Dependência adicionada!");
            ok.setContentText("Dependência adicionada com sucesso!");
            ok.showAndWait();
            initialize();
            return;
        } catch (NoSuchElementException e) {
            System.out.println("Ação cancelada.");
        } catch (NumberFormatException e) {
            Alert err404 = new Alert(Alert.AlertType.ERROR);
            err404.setHeaderText("Erro!");
            err404.setContentText("A quantidade deve ser um número decimal.");
            err404.showAndWait();
        }
        
    }
    
    @FXML
    public void saveAttack() {
        try {
            String nome = nomeInput.getText();
            int nivelMagico = nivelInput.getText().equals("") 
                    ? 0
                    : Integer.parseInt(nivelInput.getText());
            
            Pattern danoRegex = Pattern.compile(
                    "^(?<numero>[0-9]+)?d(?<tipo>[0-9]+)(?<bonus>[+-][0-9]+)?$"
            );
            Matcher matcherDano = danoRegex.matcher(danoInput.getText());
            
            if (!matcherDano.find()) {
                Alert err404 = new Alert(Alert.AlertType.ERROR);
                err404.setHeaderText("Erro na formatação");
                err404.setContentText("O dano deve seguir o formato [NUM]d[TIPO]+/-[BONUS].");
                err404.showAndWait();
                return;
            }
            
            int numeroDado = matcherDano.group("numero") == null
                    ? 1
                    : Integer.parseInt(matcherDano.group("numero"));
            int tipoDado = Integer.parseInt(matcherDano.group("tipo"));
            int bonusDado = Integer.parseInt(matcherDano.group("bonus"));
            
            Ataque criado = Ataque.initialize(
                    nome,
                    SelectedCharacter.get().getId(), 
                    nivelMagico, 
                    (Equipamento) dependEquipBox.getSelectionModel().getSelectedItem(), 
                    tipoDado, 
                    numeroDado, 
                    bonusDado
            );
            
            for (AtaqueConsumoBaseRecord x : depends) {
                AtaqueConsumo.initialize(criado.getId(), x.item, x.quantidade);
            }
            
            SelectedCharacter.get().refreshAtaques();
            
            Alert ok = new Alert(Alert.AlertType.INFORMATION);
            ok.setHeaderText("Ataque salvo!");
            ok.setContentText(String.format("Ataque %s foi criado", criado.toString()));
            ok.showAndWait();
        } catch (NumberFormatException e) {
            Alert notok = new Alert(Alert.AlertType.ERROR);
            notok.setHeaderText("Erro de formatação.");
            notok.setContentText(e.getMessage());
            notok.showAndWait();
        } catch (IllegalArgumentException e) {
            Alert notok = new Alert(Alert.AlertType.ERROR);
            notok.setHeaderText("Erro ao criar ataque!");
            notok.setContentText(e.getMessage());
            notok.showAndWait();
        } catch (SQLException e) {
            Alert notok = new Alert(Alert.AlertType.ERROR);
            notok.setHeaderText("Erro ao criar ataque!");
            notok.setContentText(e.getMessage());
            notok.showAndWait();
        }
    }
}
