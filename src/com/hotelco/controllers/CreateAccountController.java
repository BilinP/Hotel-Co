package com.hotelco.controllers;

import com.hotelco.connections.UserControl;
import com.hotelco.utilities.FXMLPaths;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class CreateAccountController extends BaseController {

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField email;

    @FXML
    private Text notification;

    @FXML
    private TextField phoneNumber;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmPassword;

    
    @FXML
    void initialize() {
        //add code not related to JavaFX here
        Platform.runLater(() -> {
            //add JavaFX related code here
        });
    }


    @FXML
    void createAccount(MouseEvent event) {
        LoginController loginController = (LoginController) switchScene(FXMLPaths.LOGIN, event);

        //FIXME: input validation here
        //if (condition)
        {
            UserControl.CreateUser(firstName.getText(), lastName.getText(),
                    email.getText(), phoneNumber.getText(), password.getText());
            loginController.setNotification("Account successfully created!");
        }
    }

    @FXML
    void switchToLoginScene(MouseEvent event) {
        switchScene(FXMLPaths.LOGIN, event);
    }

}
