package com.hotelco.controllers;

import java.time.format.DateTimeFormatter;

import com.hotelco.entities.Reservation;
import com.hotelco.utilities.FXMLPaths;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * The ThankYouController class is the associated controller class of the 'ThankYouGUI' view. 
 * It handles connection between the GUI and internal data.
 * 
 * @author      Grigor Azakian
 */
public class ThankYouController extends BaseController {

    /**
     * Text that will display a Reservation objects reservation ID.
     */
    @FXML
    private Text reservationNumber;

    /**
     * Text that will display a Reservation objects check-in date. 
     */
    @FXML
    private Text checkInDate;

    /**
     * Text that will display a Reservation objects check-out date.
     */
    @FXML
    private Text checkOutDate;

    @FXML
    private void initialize() {
        //add non JavaFX related code here
        Platform.runLater(() -> {
            //add JavaFX related code here
        });
    }

    /**
     * This method is called by pressing the 'Return to Main Menu' text.
     * It exits the 'SearchGUI' and enters the 'MenuGUI'.
     * @param event The 'mouse released' event that is triggered by pressing the 'Return to Main Menu' text.
     */
    @FXML
    private void switchToMenuScene(MouseEvent event) {
        switchScene(FXMLPaths.HOME, event);
    }

    /**
     * This method will print the details of the current reservation onto the screen.
     * @param reservation The current reservation to print details for.
     */
    void writeReservationInfo(Reservation reservation) {
        reservationNumber.setText(Integer.toString(reservation.getReservationId()));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        checkInDate.setText(reservation.getStartDate().format(dateTimeFormatter));
        checkOutDate.setText(reservation.getEndDate().format(dateTimeFormatter));
    }

}
