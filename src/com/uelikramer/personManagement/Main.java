/*
 * Copyright (c) 2017, Ueli Kramer
 */
package com.uelikramer.personManagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class starts a javafx stage with the fxml in view folder.
 *
 * @author Ueli Kramer
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/PersonManagement.fxml"));
        primaryStage.setTitle("Mitarbeiter Verwaltung");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
