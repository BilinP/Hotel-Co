package com.hotelco.controllers;

import com.hotelco.constants.RoomType;
import com.hotelco.utilities.DatabaseUtil;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class RoomChoiceController {

    @FXML
    private Text kingRate;

    @FXML
    private Button king;

    @FXML
    private Text queenRate;

    @FXML
    private Button queen;

    @FXML
    private Text dblRate;

    @FXML
    private Button dbl;

    @FXML
    private Text suiteRate;

    @FXML
    private Button suite;

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            kingRate.setText("$" + DatabaseUtil.getRate(RoomType.KING).toString());
            queenRate.setText("$" + DatabaseUtil.getRate(RoomType.QUEEN).toString());
            dblRate.setText("$" + DatabaseUtil.getRate(RoomType.DBL).toString());
            suiteRate.setText("$" + DatabaseUtil.getRate(RoomType.SUITE).toString());
        });
    }

}