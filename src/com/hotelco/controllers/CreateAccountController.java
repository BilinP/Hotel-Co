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
    private void initialize() {
        //add code not related to JavaFX here
        Platform.runLater(() -> {
            //add JavaFX related code here
        });
    }


    @FXML
    private void createAccount(MouseEvent event) {
        TextField[] textFields = {firstName, lastName, email, phoneNumber};
        PasswordField[] passwordFields = {password, confirmPassword};
        for (TextField textField: textFields) {
            if (textField.getText().isEmpty()) {
                notification.setText("Please complete all fields");
                return;
            }
        }
        for (PasswordField passwordField: passwordFields) {
            if (passwordField.getText().isEmpty()) {
                notification.setText("Please complete all fields");
                return;
            }
        }
        if (!password.getText().equals(confirmPassword.getText())) {
            notification.setText("Passwords do not match!");
            return;
        }

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
    private void switchToLoginScene(MouseEvent event) {
        switchScene(FXMLPaths.LOGIN, event);
    }

}
