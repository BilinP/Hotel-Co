package com.hotelco.controllers;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
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

    /**
     * Text that can be used to notify if a cancelation was successful.
     */
    @FXML
    private Text notification;

    /**
     * Map that assigns a Reservation object to a printed Text object.
     */
    private Map<Text, Reservation> map;

    /**
     * The user's reservations
     */
    private Reservation[] reservations;

    /**
     * This method is called immediately upon controller creation.
     * It calls 'displayOrders()' to immediately display a user's reservations.
     */
    @FXML
    private void initialize() {
        map = new HashMap<>();
        Platform.runLater(() -> {
            reservations = ReservationSystem.getCurrentUser()
                .fetchReservations(false, true, false);
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
        switchScene(FXMLPaths.HOME);
    }

    /**
     * This method displays a logged in users reservation history corresponding to the page number.
     */
    private void displayOrders() {
        reservationsContainer.getChildren().clear();
        if (reservations.length == 0) {
            Text text = new Text();
            text.setFill(Color.WHITE);
            text.setFont(Font.font("System", 24));
            text.setText("No past reservations!");
            reservationsContainer.getChildren().add(text);
            return;
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        Collections.reverse(Arrays.asList(reservations));
        int i = Integer.parseInt(pageNumber.getText()) == 1 ? 0 : (Integer.parseInt(pageNumber.getText()) - 1) * 5;
        int upperLimit = i + 5;

        for (; i < Math.min(upperLimit, reservations.length); i++) {
            Text text = new Text();
            text.setFill(Color.WHITE);
            text.setFont(Font.font("System", 24));
            text.setText("Reservation from " + 
            reservations[i].getStartDate().format(dateTimeFormatter) + " to " +
            reservations[i].getEndDate().format(dateTimeFormatter));
            if(reservations[i].getIsCancelled()){text.setFill(Color.GRAY);}
            map.put(text, reservations[i]);
            text.setOnMouseReleased(event -> {
                ReservationLookupController reservationLookupController =
                (ReservationLookupController) switchScene(FXMLPaths.RESERVATION_LOOKUP);
                reservationLookupController.writeReservationInfo(map.get(text));
            });
            reservationsContainer.getChildren().add(text);
        }
    }

    
    /** 
     * Method used to set the notifcation text
     * @param message the message used for the text.
     */
    void setNotification(String message) {
        notification.setText(message);
    }
}