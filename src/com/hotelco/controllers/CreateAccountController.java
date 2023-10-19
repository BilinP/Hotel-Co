package com.hotelco.controllers;

import com.hotelco.entities.User;
import com.hotelco.utilities.DatabaseUtil;
import com.hotelco.utilities.FXMLPaths;
import com.hotelco.utilities.TextFormatters;

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
        Platform.runLater(() -> {
            phoneNumber.setTextFormatter(TextFormatters.PHONE_NUMBER);
            firstName.setTextFormatter(TextFormatters.FIRST_NAME);
            lastName.setTextFormatter(TextFormatters.LAST_NAME);
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
        if (DatabaseUtil.doesEmailExist(email.getText())) {
            notification.setText("Email is already registered!");
            return;
        }

        LoginController loginController = (LoginController) switchScene(FXMLPaths.LOGIN, event);

        User newUser = new User(firstName.getText(), lastName.getText(),email.getText(), phoneNumber.getText());
        newUser.push(password.getText());
        loginController.setNotification("Account successfully created!");

    }

    @FXML
    private void switchToLoginScene(MouseEvent event) {
        switchScene(FXMLPaths.LOGIN, event);
    }

}
