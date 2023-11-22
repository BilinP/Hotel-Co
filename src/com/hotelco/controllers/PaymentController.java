package com.hotelco.controllers;

import com.hotelco.constants.CreditCardType;

import com.hotelco.entities.CreditCard;
import com.hotelco.entities.Payment;
import com.hotelco.entities.Reservation;

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

    private Reservation reservation;


    public void writePayment(Reservation reservation){
        cardType.getItems().addAll(CreditCardType.values());
        this.reservation=reservation;
        Payment pay= new Payment(reservation);
        due.setText("Totel Due: "+ pay.getAmount().toString());

    }

    @FXML
    void payment(MouseEvent event) {
        CreditCard card= new CreditCard(creditCardNum.getText(), CVV.getText(), expDate.getValue(), cardType.getValue(), reservation.getUser());
        if(card.verify()){
        card.assign();
        switchScene(FXMLPaths.THANK_YOU, event);
        }
    }

    @FXML
    void switchToBooking(MouseEvent event) {
        switchScene(FXMLPaths.SEARCH, event);
    }

}
