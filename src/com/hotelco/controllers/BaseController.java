package com.hotelco.controllers;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;

public class BaseController {
    protected BaseController switchScene(String fxmlLocation, Event event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlLocation));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        stage.setScene(scene);
        stage.show();
        return loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
