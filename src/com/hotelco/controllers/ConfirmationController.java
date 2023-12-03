package com.hotelco.controllers;

import java.time.format.DateTimeFormatter;

import com.hotelco.constants.Constants;
import com.hotelco.entities.Reservation;
import com.hotelco.entities.User;
import com.hotelco.utilities.EmailGenerator;
import com.hotelco.utilities.FXMLPaths;
import com.hotelco.utilities.Instances;
import com.hotelco.utilities.ReservationCalculator;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * The ThankYouController class is the associated controller class of the 'ThankYouGUI' view. 
 * It handles connection between the GUI and internal data.
 * 
 * @author      Bilin Pattasseril
 */
public class ConfirmationController extends BaseController {
    @FXML
    private Button button;

    @FXML
    private Text keyMessage;

    @FXML
    private Text message;

    /**
     * Text that will display a Reservation objects reservation ID.
     */
    @FXML
    private Text reservationNumber;

    /**
     * Text that will display a Reservation objects check-in date. 
     */
    @FXML
    private Text checkIn;

    /**
     * Text that will display a Reservation objects check-out date.
     */
    @FXML
    private Text checkOut;

    /**
     * Text that will display the room type of a Reservation.
     */
    @FXML
    private Text room;
    
    /**
     * Text that will display the Users first name.
     */
    @FXML
    private Text thankYou;
    
    /**
     * Text that will display the total cost of the Reservation.
     */
    @FXML
    private Text total;    

    private boolean isCheckIn;

    @FXML
    private void initialize() {
        //add non JavaFX related code here
        Platform.runLater(() -> {
            //add JavaFX related code here
        });
    }

    /**
     * This method is called by pressing the 'BACK TO HOME' button.
     * It exits the 'ThankYouGUI' and enters the 'HomeGUI'.
     * @param event The 'mouse released' event that is triggered by pressing the 'BACK TO HOME' button.
     */
    @FXML
    private void switchToHomeScene(MouseEvent event) {
        if(isCheckIn){
            Instances.getDashboardController().switchAnchor(FXMLPaths.CHECK_IN);
        }else{
             Instances.getDashboardController().switchAnchor(FXMLPaths.CHECK_OUT);
        }
       
    }

    public void setIsCheckin(boolean value ){
        isCheckIn=value;
    }

    /**
     * This method will print the details of the current reservation onto the screen.
     * @param reservation The current reservation to print details for.
     */
    void writeInfo(User user) {
        if(isCheckIn){
            thankYou.setText("Welcome " + user.getFirstName());
            message.setText("Your check-in is complete. Enjoy your stay at Hotel CO. ");
            keyMessage.setText("Please pick up your key card from below");
            button.setText("BACK TO CHECK IN");
        }else{
            thankYou.setText("Farewell " + user.getFirstName());
            message.setText("Your check-out is complete. Thank you for staying at Hotel CO.");
            keyMessage.setText("Please drop off your key card below.");
            button.setText("BACK TO CHECK OUT");
        }
        
    }

}
