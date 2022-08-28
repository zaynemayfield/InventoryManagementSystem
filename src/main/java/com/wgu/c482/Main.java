package com.wgu.c482;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * <pre>
 * Main class begins the app and loads the MainForm.fxml and sets the title.
 *
 * javadocs are located in javadocs folder inside C482 Project folder
 *
 * CORRECTED LOGICAL ERROR: When I was writing the search function I could not get the search to work using the name.
 * It would find it when searching with the ID, but not by name. I stepped through every part of my code giving out a status on it.
 * I even did it out to console on the inventory class. It walked through perfectly every step until almost the very end when it was comparing
 * the search result with the name of the part. After looking at it closer I realized that I had used equals instead of contains. So only exact matches would show up.
 * Once I realized this it was an easy fix and things worked.
 *
 * FUTURE ENHANCEMENT: I would add the feature to choose a part and display all the products that contain that part. This would give
 * users the ability to quickly see how often the part is used or not used at all.
 * </pre>
 * */
public class Main extends Application {
    /**
     * Starts the app up and sets stage, title, scene.
     * */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        Scene scene = new Scene(root, 900, 630);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}