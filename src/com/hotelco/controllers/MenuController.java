package com.hotelco.controllers;

import com.hotelco.entities.ReservationSystem;
import com.hotelco.utilities.FXMLPaths;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * The MenuController class is the associated controller class of the 'MenuGUI' view. 
 * It handles connection between the GUI and internal data.
 * 
 * @author      Grigor Azakian
 * @version     %I%, %G%
 */
public class MenuController extends BaseController {

    /**
     * Text that greets a user by the name of the account that is currently logged in.
     */
    @FXML
    private Text welcomeText;

    /**
     * This method is called immediately upon controller creation.
     * It updates 'welcomeText' to include the name of the user who is currently logged in.
     */
    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            welcomeText.setText("Welcome, " + ReservationSystem.getCurrentUser().getFirstName() +
                "! What would you like to do today?");
        });
    }

    /**
     * This method is called by pressing the 'Logout' button on the top left corner.
     * It logs out the user that is currently signed in. It will enter the 'LoginGUI' after logging the user out.
     * @param event The 'mouse released' event that is triggered by pressing the 'Logout' text on the top left corner.
     */
    @FXML
    private void logout(MouseEvent event) {
        ReservationSystem.logout();
        switchScene(FXMLPaths.LOGIN, event);
    }

    /**
     * This method is called by pressing the 'Review/Cancel your booking' text.
     * It exits the 'MenuGUI' and enters the 'RoomSearchGUI'.
     * @param event The 'mouse released' event that is triggered by pressing the 'Review/Cancel your booking' text.
     */
    @FXML
    private void switchToLookup(MouseEvent event) {
        switchScene(FXMLPaths.ORDER_LOOKUP, event);
    }

    /**
     * This method is called by pressing the 'Find a room' text.
     * It exits the 'MenuGUI' and enters the 'SearchGUI'.
     * @param event The 'mouse released' event that is triggered by pressing the 'Find a room' text.
     */
    @FXML
    private void switchToSearch(MouseEvent event) {
        switchScene(FXMLPaths.SEARCH, event);
    }

}