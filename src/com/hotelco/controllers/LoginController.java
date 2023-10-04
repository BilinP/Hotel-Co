package com.hotelco.controllers;

import com.hotelco.utilities.FXMLPaths;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController extends BaseController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    void login(MouseEvent event) {
        
    }

    @FXML
    private void switchToCreateAccount(MouseEvent event) {
        switchScene(FXMLPaths.CREATE_ACCOUNT, event);
    }

}
