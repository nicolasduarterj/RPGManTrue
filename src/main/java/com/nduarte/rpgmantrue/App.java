package com.nduarte.rpgmantrue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import com.nduarte.rpgmantrue.database.MainSQLiteConnection;
import com.nduarte.rpgmantrue.database.DatabaseInitializationException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("MainMenu"), 720, 540);
        stage.setScene(scene);
        stage.setTitle("RPGManTrue");
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws DatabaseInitializationException {
        MainSQLiteConnection.initConnection();
        MainSQLiteConnection.initTables();
        launch();
    }

}