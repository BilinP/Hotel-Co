package com.hotelco.controllers;

import com.hotelco.entities.Reservation;
import com.hotelco.entities.ReservationSystem;
import com.hotelco.utilities.FXMLPaths;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * The ReservationHistoryController class is the associated controller class of the 'ReservationHistoryGUI' view. 
 * It handles connection between the GUI and internal data.
 * 
 * @author      Grigor Azakian
 * @version     %I%, %G%
 */
public class ReservationHistoryController extends BaseController {

    /**
     * Text that will display a reservation at the top of the list.
     */
    @FXML
    private Text firstOrder;

    /**
     * Text that will display a reservation below 'firstOrder'.
     */
    @FXML
    private Text secondOrder;

    /**
     * Text that will display a reservation below 'secondOrder'.
     */
    @FXML
    private Text thirdOrder;

    /**
     * Text that will display a reservation below 'thirdOrder'.
     */
    @FXML
    private Text fourthOrder;

    /**
     * Text that will display a reservation below 'fourthOrder'.
     */
    @FXML
    private Text fifthOrder;

    /**
     * Text that will display the current page number.
     */
    @FXML
    private Text pageNumber;

    /**
     * This method is called immediately upon controller creation.
     * It calls 'displayOrders()' to immediately display a users reservations.
     */
    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            displayOrders();
        });
    }

    /**
     * This method is called by pressing the '-' text to the left of the page number.
     * It decrements the current page number as long as the current page number is greater than '1'.
     * @param event The 'mouse released' event that is triggered by pressing the '-' text to the left of the page number.
     */
    @FXML
    private void decrementPage(MouseEvent event) {
        if (Integer.parseInt(pageNumber.getText()) > 1) {
            pageNumber.setText(Integer.toString(Integer.parseInt(pageNumber.getText()) - 1));
        }
        displayOrders();
    }

    /**
     * This method is called by pressing the '+' text to the right of the page number.
     * It increments the current page number as long as there are available reservations to show.
     * @param event The 'mouse released' event that is triggered by pressing the '+' text to the right of the page number.
     */
    @FXML
    private void incrementPage(MouseEvent event) {
        Reservation[] reservations = ReservationSystem.getCurrentUser().getReservations();
        if (Integer.parseInt(pageNumber.getText()) < Math.ceil(reservations.length/5.0)) {
            pageNumber.setText(Integer.toString(Integer.parseInt(pageNumber.getText()) + 1));
        }
        displayOrders();
    }

    /**
     * This method is called by pressing the 'Go Back' text.
     * It exits the 'OrderLookupGUI' and enters the 'MenuGUI'.
     * @param event The 'mouse released' event that is triggered by pressing the 'Go Back' text.
     */
    @FXML
    private void switchToMenuScene(MouseEvent event) {
        switchScene(FXMLPaths.MENU, event);
    }

    /**
     * This method displays a logged in users reservation history corresponding to the page number.
     */
    private void displayOrders() {
        Integer index, j;
        Reservation[] reservations = ReservationSystem.getCurrentUser().getReservations();
        Text[] texts = {firstOrder, secondOrder, thirdOrder, fourthOrder, fifthOrder};
        for (Integer i = 0; i < texts.length; i++) {
            texts[i].setText("");
        }

        index = Integer.parseInt(pageNumber.getText());
        if (index == 1) {
            index = 0;
        }
        else {
            index = (index - 1) * 5;
        }

        j = 0;
        for (Integer i = index; i < Math.min(index + 5, reservations.length); i++) {
            //FIXME:I put this just for testing but it is ugly. We need to
            //Make column headers and make a cancel button
            texts[j].setText(
                "Reservation # " + reservations[i].getReservationId() + " from " +
                reservations[i].getStartDate().toString() + " - " +
                reservations[i].getEndDate().toString() + " - " +
                ", party of " + reservations[i].getGroupSize());
            j++;
        }
    }


}