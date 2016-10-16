package com.projavafx.fxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLAdoptionForm extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("FXML GridPane Demo");
        Parent root = FXMLLoader.load(getClass().getResource("AdoptionForm.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
