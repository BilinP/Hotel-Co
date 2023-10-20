package com.hotelco.controllers;

import java.time.LocalDate;

import com.hotelco.entities.*;
import com.hotelco.utilities.Constants;
import com.hotelco.utilities.FXMLPaths;
import com.hotelco.utilities.RoomType;

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
        Integer numGuests = Integer.parseInt(guests.getText());
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

        if(numGuests > 0 && numGuests <= Constants.DBL_CAP) {    
            dbl.setDisable(!ReservationSystem.checkAvailability(start, end, RoomType.DBL));
        }
        if(numGuests <= Constants.QUEEN_CAP) {  
            queen.setDisable(!ReservationSystem.checkAvailability(start, end, RoomType.QUEEN));
        }
        if(numGuests <= Constants.KING_CAP) {
            king.setDisable(!ReservationSystem.checkAvailability(start, end, RoomType.KING));
        }
        if(numGuests <= Constants.SUITE_CAP) {
            suite.setDisable(!ReservationSystem.checkAvailability(start, end, RoomType.SUITE));
        }
    }

    @FXML
    private void createBooking(MouseEvent event) {
        Button pressedButton = (Button) event.getSource();
        //Use this String to figure out which button the user pressed
        String roomType = (String) pressedButton.getUserData();
        //For next sprint, implement payment right about here
        Room room = new Room(
            ReservationSystem.findEmptyRoom(
                startDate.getValue(), endDate.getValue(), RoomType.valueOf(roomType.toUpperCase())));
        Reservation reservation = new Reservation(
            room, startDate.getValue(), endDate.getValue(),
            ReservationSystem.getCurrentUser(), Integer.parseInt(guests.getText()));
        ReservationSystem.setCurrentReservation(reservation);
        ReservationSystem.book();
        
    }

    @FXML
    private void decrementGuest(MouseEvent event) {
        if (Integer.parseInt(guests.getText()) > 1) {
            guests.setText(Integer.toString(Integer.parseInt(guests.getText()) - 1));
        }
    }

    @FXML
    private void incrementGuest(MouseEvent event) {
        if (Integer.parseInt(guests.getText()) < Constants.MAX_CAP) {
            guests.setText(Integer.toString(Integer.parseInt(guests.getText()) + 1));
        }
    }

    @FXML
    private void switchToMenuScene(MouseEvent event) {
        switchScene(FXMLPaths.MENU, event);
    }

}