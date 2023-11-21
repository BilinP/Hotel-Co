package com.hotelco.controllers;

import com.hotelco.entities.ReservationSystem;
import com.hotelco.entities.User;
import com.hotelco.utilities.TextFormatters;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


/**
 * The ProfileController class is the associated controller class of the 'Profile' view. 
 * It handles connection between the GUI and internal data.
 * 
 * @author      Bilin Pattasseril
 * @version     %I%, %G%
 */

public class ProfileController extends BaseController {
    
    /**
     * TextField that contains the email of the user.
     */
    @FXML
    private TextField email;

    /**
     * TextField that contains the first name of the user.
     */
    @FXML
    private TextField first;
    /**
     * TextField that contains the last name of the user.
     */
    @FXML
    private TextField last;

    /**
     * TextField that contains the phone number of the user.
     */
    @FXML
    private TextField number;

    /**
     * User that contains the current user.
     */

    private User user;


    /**
     * This method is called immediately upon controller creation.
     * It updates the the current user and then updates the textfields 'email',
     * 'first','last','number' to what is stored in the database.It as well sets 
     * a formatter for the 'number', 'first', and 'email'.
     */
    @FXML
    private void initialize() {     
        TextFormatters textFormatters = new TextFormatters();
        Platform.runLater(() -> {
            user = ReservationSystem.getCurrentUser();
            number.setTextFormatter(textFormatters.PHONE_NUMBER);
            first.setTextFormatter(textFormatters.FIRST_NAME);
            last.setTextFormatter(textFormatters.LAST_NAME);
            email.setText(user.getEmail());
            first.setText(user.getFirstName());
            last.setText(user.getLastName());
            number.setText(user.getPhone());
        });
    }

     /**
     * This method will setDisable() to false on all  text fields 'email','first',
     * 'last',and 'number' making it so  it is editable to the user. 
     * As well it makes the mouse cursor on the 'first' text field through the focusTraversable().
     * @parm event is the event that triggers this function which is the button 'edit profile'.
     */
    @FXML
    private void editProfileContent(MouseEvent event) {
        email.setDisable(false);
        last.setDisable(false);
        first.setDisable(false);
        number.setDisable(false);
        first.setFocusTraversable(true);
    }
     /**
     * This method will first setDisable to true on all the text fields, 
     * first','last','number', and 'email' to true it so these fields aren't t 
     * editable and secondly it will save the content to the database.  
     *  @parm event is the event that triggers this function which is the button 'save profile'.
     */

    @FXML
    private void saveProfileContent(MouseEvent event) {
        email.setDisable(true);
        last.setDisable(true);
        first.setDisable(true);
        number.setDisable(true);
        first.setFocusTraversable(false);
        User user = ReservationSystem.getCurrentUser();
        user.setEmail(email.getText());
        user.setFirstName(first.getText());
        user.setLastName(last.getText());
        user.setPhone(number.getText());
        user.fetch();
    }

}
