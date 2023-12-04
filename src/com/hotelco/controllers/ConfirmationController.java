package com.hotelco.controllers;

import com.hotelco.entities.User;
import com.hotelco.utilities.FXMLPaths;
import com.hotelco.utilities.Instances;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * The ThankYouController class is the associated controller class of the
 * 'ThankYouGUI' view. It handles connection between the GUI and internal data.
 * 
 * @author Bilin Pattasseril
 */
public class ConfirmationController extends BaseController {

    /**
     * Button named 'Back TO..' which will calls 'switchToRoomSearch()' when
     * pressed.
     */
    @FXML
    private Button button;

    /**
     * Text that will display the directions for room key for check in and check out
     */
    @FXML
    private Text keyMessage;

    /**
     * Text that will display the message for check in and check out
     */
    @FXML
    private Text message;

    /**
     * Text that will display the Users first name.
     */
    @FXML
    private Text thankYou;

    /**
     * boolean that will hold if the last scene was check in.
     */
    private boolean isCheckIn;

    @FXML
    private void initialize() {
        // add non JavaFX related code here
        Platform.runLater(() -> {
            // add JavaFX related code here
        });
    }

    /**
     * This method is called by pressing the 'Back to ...' button. It exits the
     * 'ConfirmationGUI' and enters the last anchor which could be 'CheckInGUI' or
     * 'CheckOutGUI'.
     * 
     * @param event The 'mouse released' event that is triggered by pressing the
     *              'BACK TO ...' button.
     */
    @FXML
    private void switchToLastAnchor(MouseEvent event) {
        if (isCheckIn) {
            Instances.getDashboardController().switchAnchor(FXMLPaths.CHECK_IN);
        } else {
            Instances.getDashboardController().switchAnchor(FXMLPaths.CHECK_OUT);
        }

    }

    /**
     * This function must be manually called upon switching to ConfirmationGUI. Sets
     * the isCheckIn to a boolean value upon controller creation.
     * 
     * @param value last scene changed from
     */
    public void setIsCheckin(boolean value) {
        isCheckIn = value;
    }

    /**
     * This method will print the details of the current reservation onto the
     * screen.
     * 
     * @param reservation The current reservation to print details for.
     */
    void writeInfo(User user) {
        if (isCheckIn) {
            thankYou.setText("Welcome " + user.getFirstName());
            message.setText("Your check-in is complete. Enjoy your stay at Hotel CO. ");
            keyMessage.setText("Please pick up your key card from below");
            button.setText("BACK TO CHECK-IN");
        } else {
            thankYou.setText("Farewell " + user.getFirstName());
            message.setText("Your check-out is complete. Thank you for staying at Hotel CO.");
            keyMessage.setText("Please drop off your key card below.");
            button.setText("BACK TO CHECK-OUT");
        }

    }

}
