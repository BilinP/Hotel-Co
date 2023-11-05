package com.hotelco.controllers;

import com.hotelco.entities.ReservationSystem;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;

/**
 * The NEWMenuController class is the associated controller class of the 'NewMenu' view. 
 * It handles connection between the GUI and internal data.
 * 
 * @author      Bilin Pattasseril
 * @version    
 */
public class NEWMenuController extends BaseController {

     /**
     * Text that greets a user by the name of the account that is currently logged in.
     */
    @FXML
    private Text welcomeText;
    @FXML
    private AnchorPane anchor;
    @FXML
    private ImageView image;

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

   
}
