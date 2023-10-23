package com.hotelco.controllers;

import java.util.HashMap;
import java.util.Map;

import com.hotelco.entities.Reservation;
import com.hotelco.entities.ReservationSystem;
import com.hotelco.utilities.FXMLPaths;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
     * VBox that will contain an array of Text of all reservations the logged in user has created.
     */
    @FXML
    private VBox reservationsContainer;

    /**
     * Text that will display the current page number.
     */
    @FXML
    private Text pageNumber;

    private Map<Text, Reservation> map;

    /**
     * This method is called immediately upon controller creation.
     * It calls 'displayOrders()' to immediately display a users reservations.
     */
    @FXML
    private void initialize() {
        map = new HashMap<>();
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
            displayOrders();
        }
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
            displayOrders();
        }
    }

    /**
     * This method is called by pressing the 'Go Back' text.
     * It exits the 'ReservationHistoryGUI' and enters the 'MenuGUI'.
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
        reservationsContainer.getChildren().clear();
        Reservation[] reservations = ReservationSystem.getCurrentUser().getReservations();
        if (reservations.length == 0) {
            Text text = new Text();
            text.setFill(Color.WHITE);
            text.setFont(Font.font("System", 24));
            text.setText("No past reservations!");
            reservationsContainer.getChildren().add(text);
            return;
        }

        int i = Integer.parseInt(pageNumber.getText());
        if (i == 1) {
            i = 0;
        }
        else {
            i = (i - 1) * 5;
        }

        int upperLimit = i + 5;
        for (; i < Math.min(upperLimit, reservations.length); i++) {
            Text text = new Text();
            text.setFill(Color.WHITE);
            text.setFont(Font.font("System", 24));
            text.setText("Reservation # " + reservations[i].getReservationId());
            map.put(text, reservations[i]);
            text.setOnMouseReleased(event -> {
                ReservationLookupController reservationLookupController = (ReservationLookupController) switchScene(FXMLPaths.RESERVATION_LOOKUP, event);
                reservationLookupController.writeReservationInfo(map.get(text));
            });
            reservationsContainer.getChildren().add(text);
        }
    }
}