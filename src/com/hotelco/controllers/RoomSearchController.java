package com.hotelco.controllers;

import java.io.IOException;
import java.text.Format;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.mail.MessagingException;
import javax.swing.text.DateFormatter;

import com.hotelco.constants.Constants;
import com.hotelco.constants.RoomType;
import com.hotelco.entities.*;
import com.hotelco.utilities.FXMLPaths;
import com.hotelco.utilities.SendMail;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
 * @version     %I%, %G%
 */
public class RoomSearchController extends BaseController {

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
     * It sets up the state of all Button and DatePicker variables.
     */
    @FXML
    private void initialize() {

        //This lets a DatePicker tell when its value has changed, and disables all Button variables if it has.
        final ChangeListener<LocalDate> changeListener = new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
                    LocalDate newValue) {
                disableButtons();
            }
        };

        //This sets up the DatePicker to not allow a user to choose a date before the current date.
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        LocalDate currentDate = LocalDate.now();
                        setDisable(empty || item.compareTo(currentDate) < 0);
                    }
                };
            }

        };

        Platform.runLater(() -> {
            king.setUserData("king");
            queen.setUserData("queen");
            suite.setUserData("suite");
            dbl.setUserData("dbl");

            startDate.valueProperty().addListener(changeListener);
            endDate.valueProperty().addListener(changeListener);
            startDate.setDayCellFactory(dayCellFactory);
            endDate.setDayCellFactory(dayCellFactory);
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
        disableButtons();
        Integer numGuests = Integer.parseInt(guests.getText());
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
        if(numGuests > 0 && numGuests <= Constants.CAPACITIES.get(RoomType.DBL)) {
            dbl.setDisable(!ReservationSystem.checkAvailability(start, end, RoomType.DBL));
        }
        if(numGuests <= Constants.CAPACITIES.get(RoomType.QUEEN)) {  
            queen.setDisable(!ReservationSystem.checkAvailability(start, end, RoomType.QUEEN));
        }
        if(numGuests <= Constants.CAPACITIES.get(RoomType.KING)) {
            king.setDisable(!ReservationSystem.checkAvailability(start, end, RoomType.KING));
        }
        if(numGuests <= Constants.CAPACITIES.get(RoomType.SUITE)) {
            suite.setDisable(!ReservationSystem.checkAvailability(start, end, RoomType.SUITE));
        }
    }

    /**
     * This method is called when pressing the 'Book' button.
     * It will create a booking in the class ReservationSystem for the corresponding user.
     * After creating the booking, it will enter 'ThankYouGUI'.
     * @param event The 'mouse released' event that is triggered by pressing the 'Book' button.
     * @author Grigor Azakian
     * @author Daniel Schwartz
     */
    @FXML
    private void createBooking(MouseEvent event) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        String subject = null;
        String message = null;
        Button pressedButton = (Button) event.getSource();
        //Use this String to figure out which button the user pressed
        String roomType = (String) pressedButton.getUserData();

        //For next sprint, implement payment right about here
        Room room = new Room(
            ReservationSystem.findEmptyRoom(
                startDate.getValue(), endDate.getValue(),
                RoomType.parseString(roomType)));
        Reservation reservation = new Reservation(
            room, startDate.getValue(), endDate.getValue(),
            ReservationSystem.getCurrentUser(), Integer.parseInt(guests.getText()));
        ReservationSystem.setCurrentReservation(reservation);

        /*From here down is no longer needed in sprint 2 flow. Something similar
        will appear in the payController*/
        
        ReservationSystem.book();
        subject = "Reservation confirmation";
        message = "Hello " + reservation.getUser().getFirstName() + " " +
            reservation.getUser().getLastName() +
            ", and thank you for booking with Hotel Co.\n\n" +
            "Please find your booking details below:\n\n" +
            "Reservation number: " + reservation.getReservationId() +
            "\nStart Date: "  + reservation.getStartDate().format(dateTimeFormatter) +
            "\nEnd Date: "  + reservation.getEndDate().format(dateTimeFormatter) +
            "\nNumber of guests: "  + reservation.getGroupSize() +
            "\nRoom type: "  + reservation.getRoom().getRoomType().toPrettyString() +
            "\n\nWe hope you thoroughly enjoy your stay with us." +
            "\n\n\t\tSincerely,\n\t\t\tHotel Co.";
            try {
                SendMail.startSend(
                    reservation.getUser().getEmail(), subject, message);
            }
            catch(MessagingException | IOException e){
                System.out.println(e);
            }
        reservation = ReservationSystem.getCurrentReservation();
        ThankYouController thankYouController =
            (ThankYouController) switchScene(FXMLPaths.THANK_YOU, event);
        thankYouController.writeReservationInfo(reservation);
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
            disableButtons();
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
            disableButtons();
        }
    }

    /**
     * This method is called by pressing the 'Go Back' text.
     * It exits the 'SearchGUI' and enters the 'MenuGUI'.
     * @param event The 'mouse released' event that is triggered by pressing the 'Go Back' text.
     */
    @FXML
    private void switchToMenuScene(MouseEvent event) {
        switchScene(FXMLPaths.HOME, event);
    }
    
    /**
     * This method disables all buttons.
     */
    private void disableButtons() {
        Button[] buttons = {king, queen, dbl, suite};
        for (Button button: buttons) {
            button.setDisable(true);
        }
    }
}