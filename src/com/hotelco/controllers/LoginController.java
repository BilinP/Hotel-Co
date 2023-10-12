package com.hotelco.controllers;

import com.hotelco.utilities.DatabaseUtil;
import com.hotelco.utilities.FXMLPaths;
import com.hotelco.utilities.Verifier;

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
            //add non JavaFX related code here

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
        String emailStr = email.getText();
        if(DatabaseUtil.doesEmailExist(emailStr)){
            if (Verifier.verify(emailStr, password.getText()))
            {
                System.out.println("Password verified");
                //FIXME: go to landing page after login @GAzaCSUN
            }
            else{
                notification.setText("Invalid Username/Password!");
            }
        }
    }

    @FXML
    private void switchToCreateAccount(MouseEvent event) {
        switchScene(FXMLPaths.CREATE_ACCOUNT, event);
    }

    void setNotification(String s) {
        notification.setText(s);
    }

}