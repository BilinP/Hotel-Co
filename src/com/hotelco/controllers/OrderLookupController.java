package com.hotelco.controllers;

import java.time.format.DateTimeFormatter;

import com.hotelco.entities.Reservation;
import com.hotelco.entities.ReservationSystem;
import com.hotelco.utilities.FXMLPaths;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class OrderLookupController extends BaseController {

    @FXML
    private Text firstOrder;

    @FXML
    private Text secondOrder;

    @FXML
    private Text thirdOrder;

    @FXML
    private Text fourthOrder;

    @FXML
    private Text fifthOrder;

    @FXML
    private Text pageNumber;

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            displayOrders();
        });
    }

    @FXML
    void decrementPage(MouseEvent event) {
        if (Integer.parseInt(pageNumber.getText()) > 1) {
            pageNumber.setText(Integer.toString(Integer.parseInt(pageNumber.getText()) - 1));
        }
        displayOrders();
    }

    @FXML
    void incrementPage(MouseEvent event) {
        Reservation[] reservations = ReservationSystem.getCurrentUser().getReservations();
        if (Integer.parseInt(pageNumber.getText()) < Math.ceil(reservations.length/5.0)) {
            pageNumber.setText(Integer.toString(Integer.parseInt(pageNumber.getText()) + 1));
        }
        displayOrders();
    }

    @FXML
    private void switchToMenuScene(MouseEvent event) {
        switchScene(FXMLPaths.MENU, event);
    }

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
