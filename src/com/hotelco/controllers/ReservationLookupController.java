package com.hotelco.controllers;

import com.hotelco.utilities.FXMLPaths;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ReservationLookupController extends BaseController {

    @FXML
    private Text reservationID;

    @FXML
    private Text checkInDate;

    @FXML
    private Text checkOutDate;

    @FXML
    private Text guestNumber;

    @FXML
    void switchToMenuScene(MouseEvent event) {
        switchScene(FXMLPaths.MENU, event);
    }

}
