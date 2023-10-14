package com.hotelco.controllers;

import com.hotelco.entities.ReservationSystem;
import com.hotelco.utilities.FXMLPaths;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class MenuController extends BaseController {

    @FXML
    private Text welcomeText;

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            welcomeText.setText("Welcome, " + ReservationSystem.getCurrentUser().getFirstName() +
                "! What would you like to do today?");
        });
    }

    @FXML
    private void logout(MouseEvent event) {
        //ReservationSystem.logout();
        switchScene(FXMLPaths.LOGIN, event);
    }

    @FXML
    private void switchToLookup(MouseEvent event) {
        switchScene(FXMLPaths.ORDER_LOOKUP, event);
    }

    @FXML
    private void switchToSearch(MouseEvent event) {
        switchScene(FXMLPaths.SEARCH, event);
    }

}