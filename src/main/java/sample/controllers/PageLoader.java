package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public interface PageLoader {
    default void loadResource(String resourceName, ActionEvent actionEvent) throws IOException {
        loadResource(resourceName, (Stage)((Node) actionEvent.getSource()).getScene().getWindow());
    }
    default void loadResource(String resourceName, Stage primaryStage) throws IOException {
        primaryStage.setTitle("Commodity circulation");
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource(resourceName)), 1200, 640));
        primaryStage.show();
    }
    }
