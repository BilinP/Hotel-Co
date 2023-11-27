package com.hotelco.OLDFILES;

import com.hotelco.constants.CreditCardType;
import com.hotelco.controllers.BaseController;
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

/**
 * The PaymentController class is the associated controller class of the 'Payment' view. 
 * It handles connection between the GUI and internal data.
 * 
 * @author      Bilin Pattasseril
 */
public class OLDPaymentController extends BaseController{
    /**
     * TextField that contains the cvv of a credit card .
     */
    @FXML
    private TextField CVV;

    /**
     * TextField that contains the credit card number of a credit card .
     */
    @FXML
    private TextField creditCardNum;

    /**
     * Text that contains amount due.
     */
    @FXML
    private Text due;

    /**
     * Datepicker which contains the expiration date of a credit card.
     */
    @FXML
    private DatePicker expDate;
    
    /**
     * Choicebox that contain all credit card types.
     */
    @FXML
    private ChoiceBox<CreditCardType> cardType;

    /**
     * Reservation which contain the current reservation.
     */
    private Reservation reservation;


    /**
     * Sets the amount is due for thatreservation. 
     * @param reservation which used to get the amount due
     */
    public void writePayment(Reservation reservation){
        cardType.getItems().addAll(CreditCardType.values());
        this.reservation=reservation;
        Payment pay= new Payment(reservation);
        due.setText("Totel Due: "+ pay.getAmount().toString());
    }
    /**
     * Verify the card and then assign it to the database
     * @param event The 'mouse released' event that is triggered by pressing the 'Pay' text.
     */
    @FXML
    void payment(MouseEvent event) {
        CreditCard card= new CreditCard(creditCardNum.getText(), CVV.getText(), expDate.getValue(), reservation.getUser());
        if(card.verify()){
        card.assign();
        switchScene(FXMLPaths.THANK_YOU);
        }
    }

    /**
     * This method is called by pressing the 'back' button.
     * It exits the 'Payment Controller' and enters the 'Reserivation History GUI'.
     * @param event The 'mouse released' event that is triggered by pressing the ' Back'  on the top left corner.
     */
    @FXML
    void switchToBooking(MouseEvent event) {
        switchScene(FXMLPaths.RESERVATION);
    }

}
