package com.hotelco.controllers;

import java.security.NoSuchAlgorithmException;

import com.hotelco.entities.Password;
import com.hotelco.entities.ReservationSystem;
import com.hotelco.entities.User;
import com.hotelco.utilities.DatabaseUtil;
import com.hotelco.utilities.FXMLPaths;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class LoginController extends BaseController {

    @FXML
    private Text notification;
    
    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private void initialize() {
        // String emailStr = email.getText();
        // if(DatabaseUtil.doesEmailExist(emailStr)){
        //     User temp = new User(emailStr);
        //     Password pass = new Password(temp.getSalt());
        //     boolean verified;
        //     try {
        //         verified = pass.verify(password.getText(),temp.getHashedPassword())
        //     }
        //     catch (NoSuchAlgorithmException e)
        //     {
        //         System.out.println(e);
        //     }
        //     if (verified)
        //     {
        //         ReservationSystem;
        //     }

        // }

        Platform.runLater(() -> {
            //add JavaFX related code here
        });
    }

    @FXML
    private void login(MouseEvent event) {
        if (email.getText().isEmpty() || password.getText().isEmpty()) {
            notification.setText("Please enter username and password");
            return;
        }
        //notification.setText("Invalid Username/Password!");
    }

    @FXML
    private void switchToCreateAccount(MouseEvent event) {
        switchScene(FXMLPaths.CREATE_ACCOUNT, event);
    }

    void setNotification(String s) {
        notification.setText(s);
    }

}