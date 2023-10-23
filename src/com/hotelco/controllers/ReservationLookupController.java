package com.hotelco.controllers;

import java.time.format.DateTimeFormatter;

import com.hotelco.entities.Reservation;
import com.hotelco.utilities.FXMLPaths;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
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
        //FIXME: setIsCancelled() has no noticable visual effect on orders.
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
     * @param reservation Instance of the reservation to be printed.
     */
    void writeReservationInfo(Reservation reservation) {
        this.reservation = reservation;
        //FIXME:
        //Currently broken, the reservation ID is null when this is called.
        //reservationID.setText(reservationID.getText() + Integer.toString(reservation.getReservationId()));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        checkInDate.setText(reservation.getStartDate().format(dateTimeFormatter));
        checkOutDate.setText(reservation.getEndDate().format(dateTimeFormatter));
        guestNumber.setText(Integer.toString(reservation.getGroupSize()));
    }

}
