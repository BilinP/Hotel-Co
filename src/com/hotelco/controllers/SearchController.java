package com.hotelco.controllers;

import java.time.LocalDate;
import com.hotelco.RoomType;

import com.hotelco.entities.ReservationSystem;
import com.hotelco.utilities.FXMLPaths;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class SearchController extends BaseController {

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private Text guests;

    @FXML
    private Text notification;

    @FXML
    private Button king;

    @FXML
    private Button queen;

    @FXML
    private Button suite;

    @FXML
    private Button dbl;

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            king.setUserData("king");
            queen.setUserData("queen");
            suite.setUserData("suite");
            dbl.setUserData("dbl");
        });
    }

    @FXML
    private void checkAvailability(MouseEvent event) {
        king.setDisable(true);
        queen.setDisable(true);
        suite.setDisable(true);
        dbl.setDisable(true);
        DatePicker[] datePickers = {startDate, endDate};
        for (DatePicker datePicker: datePickers) {
            if (datePicker.getValue() == null) {
                notification.setText("Please enter a date");
                return;
            }
        }
        LocalDate start = startDate.getValue();
        LocalDate end = endDate.getValue();
        if (end.isBefore(start)) {
            notification.setText("Invalid dates!");
            return;
        }
        notification.setText("");
        king.setDisable(!ReservationSystem.checkAvailability(start, end, RoomType.KING));
        queen.setDisable(!ReservationSystem.checkAvailability(start, end, RoomType.QUEEN));
        dbl.setDisable(!ReservationSystem.checkAvailability(start, end, RoomType.DBL));
        suite.setDisable(!ReservationSystem.checkAvailability(start, end, RoomType.SUITE));
    }

    @FXML
    private void createBooking(MouseEvent event) {
        Button pressedButton = (Button) event.getSource();
        //Use this String to figure out which button the user pressed
        String roomType = (String) pressedButton.getUserData();
    }

    @FXML
    private void decrementGuest(MouseEvent event) {
        if (Integer.parseInt(guests.getText()) > 1) {
            guests.setText(Integer.toString(Integer.parseInt(guests.getText()) - 1));
        }
    }

    @FXML
    private void incrementGuest(MouseEvent event) {
        if (Integer.parseInt(guests.getText()) < 12) {
            guests.setText(Integer.toString(Integer.parseInt(guests.getText()) + 1));
        }
    }

    @FXML
    private void switchToMenuScene(MouseEvent event) {
        switchScene(FXMLPaths.MENU, event);
    }

}