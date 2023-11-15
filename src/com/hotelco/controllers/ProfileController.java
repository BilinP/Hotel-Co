package com.hotelco.controllers;

import com.hotelco.entities.ReservationSystem;
import com.hotelco.entities.User;
import com.hotelco.utilities.TextFormatters;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class ProfileController extends BaseController {

    @FXML
    private AnchorPane anchor;

    @FXML
    private TextField email;

    @FXML
    private TextField first;

    @FXML
    private TextField last;

    @FXML
    private TextField number;

    boolean content= false;
    User user;

       @FXML
    private void initialize() {     
        TextFormatters textFormatters = new TextFormatters();
        Platform.runLater(() -> {
            user= ReservationSystem.getCurrentUser();
            number.setTextFormatter(textFormatters.PHONE_NUMBER);
            first.setTextFormatter(textFormatters.FIRST_NAME);
            last.setTextFormatter(textFormatters.LAST_NAME);
           System.out.print(user.getEmail()); 
           email.setText(user.getEmail());
            first.setText(user.getFirstName());
            last.setText(user.getLastName());
            number.setText(user.getPhone());
            

        });
    }


    @FXML
    void editProfileContent(MouseEvent event) {
        email.setEditable(true);
        last.setEditable(true);
        first.setEditable(true);
        number.setEditable(true);
        first.setFocusTraversable(true);
        content=true;

    }

    @FXML
    void saveProfileContent(MouseEvent event) {
        if(content){
        email.setEditable(false);
        last.setEditable(false);
        first.setEditable(false);
        number.setEditable(false);
        first.setFocusTraversable(false);
        User user= ReservationSystem.getCurrentUser();
        user.setEmail(email.getText());
        user.setFirstName(first.getText());
        user.setLastName(last.getText());
        user.setPhone(number.getText());
        user.fetch();
        content=false;
        }

    }

}
