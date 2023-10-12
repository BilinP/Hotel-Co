package com.hotelco.controllers;

import com.hotelco.entities.User;
import com.hotelco.utilities.DatabaseUtil;
import com.hotelco.utilities.FXMLPaths;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
        TextFormatter<String> phoneNumberFormatter = new TextFormatter<>(changed -> {
            if (changed.getControlNewText().length() > 10) {
                return null;
            }
            if (changed.getControlNewText().matches("\\d*")) {
                return changed;
            }
            else {
                return null;
            }
        });
        Platform.runLater(() -> {
            phoneNumber.setTextFormatter(phoneNumberFormatter);
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
        if (!DatabaseUtil.doesEmailExist(email.getText())) {
            notification.setText("Email is already registered!");
            return;
        }

        LoginController loginController = (LoginController) switchScene(FXMLPaths.LOGIN, event);

        //FIXME: input validation here
        //if (condition)
        {
            User newUser = new User(
                firstName.getText(), lastName.getText(),email.getText(), phoneNumber.getText());
            newUser.push(password.getText());
            loginController.setNotification("Account successfully created!");
        }

    }

    @FXML
    private void switchToLoginScene(MouseEvent event) {
        switchScene(FXMLPaths.LOGIN, event);
    }

}
