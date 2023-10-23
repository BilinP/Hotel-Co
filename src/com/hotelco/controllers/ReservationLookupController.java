package com.hotelco.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.hotelco.entities.Reservation;
import com.hotelco.utilities.FXMLPaths;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ReservationLookupController extends BaseController {

    /**
     * Text containing the ID of the reservation being viewed.
     */
    @FXML
    private Text reservationID;

    /**
     * Text containing the check-in date of the reservation being viewed.
     */
    @FXML
    private Text checkInDate;

    /**
     * Text containing the check-out date of the reservation being viewed.
     */
    @FXML
    private Text checkOutDate;

    /**
     * Text containing the amount of guests of the reservation being viewed.
     */
    @FXML
    private Text guestNumber;

    /**
     * Text containing the status of the reservation, e.g. whether it is active, canceled, or completed.
     */
    @FXML
    private Text status;

    /**
     * Button named 'Cancel' that calls 'cancel()' when pressed.
     */
    @FXML
    private Button cancel;

    /**
     * VBox that is the parent node to the Button 'cancel'.
     */
    @FXML
    private VBox vBox;

    /**
     * Instance of current Reservation being viewed.
     */
    private Reservation reservation;

    /**
     * This method will cancel the reservation currently being viewed.
     * It is triggered by pressing the 'Cancel' button.
     * @param event The 'mouse released' event that is triggered by pressing the 'Cancel' button.
     */
    @FXML
    private void cancel(MouseEvent event) {
        reservation.setIsCancelled(true);
        switchScene(FXMLPaths.ORDER_LOOKUP, event);
    }

    /**
     * This method is called by pressing the 'Go Back' text.
     * It exits the 'ReservationLookupGUI' and enters the 'ReservationHistoryGUI'.
     * @param event The 'mouse released' event that is triggered by pressing the 'Go Back' text.
     */
    @FXML
    private void switchToMenuScene(MouseEvent event) {
        switchScene(FXMLPaths.ORDER_LOOKUP, event);
    }

    /**
     * This method will print all relevant information of a reservation onto the screen.
     * It will save the parameter locally.
     * @param reservation Instance of the reservation to be printed.
     */
    void writeReservationInfo(Reservation reservation) {
        this.reservation = reservation;
        reservationID.setText(reservationID.getText() + Integer.toString(reservation.getReservationId()));
        if (reservation.getEndDate().isBefore(LocalDate.now())) {
            status.setText("Completed");
            vBox.getChildren().remove(cancel);
        }
        else {
            if (reservation.getIsCancelled()) {
                status.setText("Canceled");
                vBox.getChildren().remove(cancel);
            }
            else {
                status.setText("Active");
            }
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        checkInDate.setText(reservation.getStartDate().format(dateTimeFormatter));
        checkOutDate.setText(reservation.getEndDate().format(dateTimeFormatter));
        guestNumber.setText(Integer.toString(reservation.getGroupSize()));
    }

}
