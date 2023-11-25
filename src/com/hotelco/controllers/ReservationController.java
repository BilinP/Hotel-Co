package com.hotelco.controllers;

import java.time.LocalDate;


import com.hotelco.constants.Constants;
import com.hotelco.constants.RoomType;
import com.hotelco.entities.*;
import com.hotelco.utilities.FXMLPaths;
import com.hotelco.utilities.Instances;
import com.hotelco.utilities.OrderSession;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * The RoomSearchController class is the associated controller class of the 'RoomSearchGUI' view. 
 * It handles connection between the GUI and internal data.
 * 
 * @author      Grigor Azakian
 */
public class ReservationController extends BaseController {

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

    @FXML
    private Text title;

    private RoomType room;

	//titledpane keep
    @FXML
    private Text totalCost;



    /**
     * This method is called immediately upon controller creation.
     * It sets up the state of all DatePicker variables.
     */
    @FXML
    private void initialize() {
        endDate.setDisable(true);
        //This lets a DatePicker tell when its value has changed, and disables all Button variables if it has.
        final ChangeListener<LocalDate> changeListener = new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
                    LocalDate newValue) {
                endDate.setDisable(false);
            }
        };

        //This sets up the DatePicker to not allow a user to choose a date before the current date.
        final Callback<DatePicker, DateCell> startDayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        Boolean availability = ReservationSystem.checkAvailability(item, item, room);
                        LocalDate currentDate = LocalDate.now();
                        setDisable(empty || item.compareTo(currentDate) < 0 || !availability);
                    }
                };
            }

        };

        //This sets up the DatePicker to not allow a user to choose a date before the current date.
        final Callback<DatePicker, DateCell> endDayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        Boolean availability = ReservationSystem.checkAvailability(startDate.getValue(), item, room);
                        setDisable(empty || item.compareTo(startDate.getValue()) < 0 || !availability);
                    }
                };
            }

        };        

        Platform.runLater(() -> {
            startDate.valueProperty().addListener(changeListener);
            startDate.setDayCellFactory(startDayCellFactory);
            endDate.setDayCellFactory(endDayCellFactory);
            if (room != null) {
                title.setText("Booking - " + room.toPrettyString());
            }
        });
    }

    /**
     * Called when pressing the 'Book' button. 
     * Saves all user input to OrderSession.
     * After creating the booking, it will enter 'PaymentGUI'.
     * @param event The 'mouse released' event that is triggered by pressing the 'Book' button.
     */
    @FXML
    private void createBooking(MouseEvent event) {
        OrderSession.setStartDate(startDate.getValue());
        OrderSession.setEndDate(endDate.getValue());
        OrderSession.setGuests(Integer.parseInt(guests.getText()));
        OrderSession.setRoomType(room);
        Instances.getDashboardController().switchAnchor("/com/hotelco/views/NEWPayment.fxml");
        /*
        Room room = new Room(
            ReservationSystem.findEmptyRoom(
                startDate.getValue(), endDate.getValue(),
                this.room));
        Reservation reservation = new Reservation(
            room, startDate.getValue(), endDate.getValue(),
            ReservationSystem.getCurrentUser(), Integer.parseInt(guests.getText()));
        ReservationSystem.setCurrentReservation(reservation);
        */
        //ReservationSystem.book();
        //reservation = ReservationSystem.getCurrentReservation();
        /*
        ThankYouController thankYouController =
            (ThankYouController) switchScene(FXMLPaths.THANK_YOU);
        thankYouController.writeReservationInfo(reservation);
        */
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
    private void switchToRoomChoiceScene(MouseEvent event) {
        Instances.getDashboardController().switchAnchor(FXMLPaths.ROOMS);
    }

    public void setRoomType(RoomType roomType) {
        this.room = roomType;
    }
}