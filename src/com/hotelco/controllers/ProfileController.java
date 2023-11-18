package com.hotelco.controllers;

import com.hotelco.entities.ReservationSystem;
import com.hotelco.entities.User;
import com.hotelco.utilities.TextFormatters;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ProfileController extends BaseController {

    @FXML
    private TextField email;

    @FXML
    private TextField first;

    @FXML
    private TextField last;

    @FXML
    private TextField number;

    private User user;

       @FXML
    private void initialize() {     
        TextFormatters textFormatters = new TextFormatters();
        Platform.runLater(() -> {
            user = ReservationSystem.getCurrentUser();
            number.setTextFormatter(textFormatters.PHONE_NUMBER);
            first.setTextFormatter(textFormatters.FIRST_NAME);
            last.setTextFormatter(textFormatters.LAST_NAME);
            email.setText(user.getEmail());
            first.setText(user.getFirstName());
            last.setText(user.getLastName());
            number.setText(user.getPhone());
        });
    }


    @FXML
    private void editProfileContent(MouseEvent event) {
        email.setDisable(false);
        last.setDisable(false);
        first.setDisable(false);
        number.setDisable(false);
        first.setFocusTraversable(true);
    }

    @FXML
    private void saveProfileContent(MouseEvent event) {
        email.setDisable(true);
        last.setDisable(true);
        first.setDisable(true);
        number.setDisable(true);
        first.setFocusTraversable(false);
        User user = ReservationSystem.getCurrentUser();
        user.setEmail(email.getText());
        user.setFirstName(first.getText());
        user.setLastName(last.getText());
        user.setPhone(number.getText());
        user.fetch();
    }

}
