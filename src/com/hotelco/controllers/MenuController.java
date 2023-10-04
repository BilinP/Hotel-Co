package com.hotelco.controllers;

import com.hotelco.utilities.FXMLPaths;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class MenuController extends BaseController {

    @FXML
    private Text welcomeText;

    @FXML
    void initialize() {
        Platform.runLater(() -> {
            welcomeText.setText("Welcome, " + "[FIRST_NAME]" + "! What would you like to do today?");
        });
    }

    @FXML
    void switchToLookup(MouseEvent event) {
        switchScene(FXMLPaths.ORDER_LOOKUP, event);
    }

    @FXML
    void switchToSearch(MouseEvent event) {
        switchScene(FXMLPaths.SEARCH, event);
    }

}