package com.hotelco.controllers;

import com.hotelco.constants.CreditCardType;
import com.hotelco.constants.RoomType;
import com.hotelco.entities.CreditCard;
import com.hotelco.entities.Reservation;
import com.hotelco.entities.ReservationSystem;
import com.hotelco.utilities.FXMLPaths;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class PaymentController extends BaseController{

    @FXML
    private TextField CVV;

    @FXML
    private TextField creditCardNum;

    @FXML
    private Text due;

    @FXML
    private DatePicker expDate;
    
    @FXML
    private ChoiceBox<CreditCardType> cardType;

    
    @FXML
    void initialize() {
        cardType.getItems().addAll(CreditCardType.values());
    }

    @FXML
    void payment(MouseEvent event) {
        Reservation reservation=ReservationSystem.getCurrentReservation(); 
        CreditCard card= new CreditCard(creditCardNum.getText(), CVV.getText(), expDate.getValue(), cardType.getValue(), reservation.getUser());
    }

    @FXML
    void switchToBooking(MouseEvent event) {
        switchScene(FXMLPaths.SEARCH, event);
    }

}
