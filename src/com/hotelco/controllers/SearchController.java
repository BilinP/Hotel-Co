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

/**
 * The SearchController class is the associated controller class of the 'SearchGUI' view. 
 * It handles connection between the GUI and internal data.
 * 
 * @author      Grigor Azakian
 * @version     %I%, %G%
 */
public class SearchController extends BaseController {

    /**
     * DatePicker that contains the date the user wants to be the first day of their booking.
     */
    @FXML
    private DatePicker startDate;

    /**
     * DatePicker that contains the date the user wants to be the last day of their booking.
     */
    @FXML
    private DatePicker endDate;

    /**
     * Text that contains the number of guests.
     */
    @FXML
    private Text guests;

    /**
     * Text that can be displayed to notify the user of any potential invalid inputs.
     */    
    @FXML
    private Text notification;

    /**
     * Button that allows a user to book a king room.
     * Calls 'createBooking()' upon being pressed.
     */
    @FXML
    private Button king;

    /**
     * Button that allows a user to book a queen room.
     * Calls 'createBooking()' upon being pressed.
     */
    @FXML
    private Button queen;

    /**
     * Button that allows a user to book a suite.
     * Calls 'createBooking()' upon being pressed.
     */    
    @FXML
    private Button suite;

    /**
     * Button that allows a user to book a double.
     * Calls 'createBooking()' upon being pressed.
     */    
    @FXML
    private Button dbl;

    /**
     * This method is called immediately upon controller creation.
     * It assigns data to several buttons so that it will be easier to tell which button was pressed.
     */
    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            king.setUserData("king");
            queen.setUserData("queen");
            suite.setUserData("suite");
            dbl.setUserData("dbl");
        });
    }

    /**
     * This method is called when pressing the 'Check Availability' button.
     * It handles input verification and checks for available rooms with the given parameters.
     * It will enable buttons corresponding to which rooms are available.
     * @param event The 'mouse released' event that is triggered by pressing the 'Check Availability' button.
     * @author Grigor Azakian
     * @author Daniel Schwartz
     */
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

    /**
     * This method is called when pressing the 'Book' button.
     * It will create a booking in the class ReservationSystem for the corresponding user.
     * @param event The 'mouse released' event that is triggered by pressing the 'Book' button.
     * @author Grigor Azakian
     * @author Daniel Schwartz
     */
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

    /**
     * This method is called by pressing the '-' text to the left of the guest number.
     * It decrements the current guest number as long as the current guest number is greater than '1'.
     * @param event The 'mouse released' event that is triggered by pressing the '-' text to the left of the guest number.
     */
    @FXML
    private void decrementGuest(MouseEvent event) {
        if (Integer.parseInt(guests.getText()) > 1) {
            guests.setText(Integer.toString(Integer.parseInt(guests.getText()) - 1));
        }
    }

    /**
     * This method is called by pressing the '+' text to the right of the guest number.
     * It increments the current guest number as long as it is below the maximum amount of allowed guests.
     * @param event The 'mouse released' event that is triggered by pressing the '+' text to the right of the guest number.
     * @author Grigor Azakian
     * @author Daniel Schwartz
     */
    @FXML
    private void incrementGuest(MouseEvent event) {
        if (Integer.parseInt(guests.getText()) < Constants.MAX_CAP) {
            guests.setText(Integer.toString(Integer.parseInt(guests.getText()) + 1));
        }
    }

    /**
     * This method is called by pressing the 'Go Back' text.
     * It exits the 'SearchGUI' and enters the 'MenuGUI'.
     * @param event The 'mouse released' event that is triggered by pressing the 'Go Back' text.
     */
    @FXML
    private void switchToMenuScene(MouseEvent event) {
        switchScene(FXMLPaths.MENU, event);
    }

}