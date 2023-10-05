package com.hotelco.controllers;

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
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    void initialize() {
        //add code not related to JavaFX here
        Platform.runLater(() -> {
            //add JavaFX related code here
        });
    }

    @FXML
    void login(MouseEvent event) {
        notification.setText("Invalid Username/Password!");
    }

    @FXML
    private void switchToCreateAccount(MouseEvent event) {
        switchScene(FXMLPaths.CREATE_ACCOUNT, event);
    }

    void setNotification(String s) {
        notification.setText(s);
    }

}