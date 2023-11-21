package com.hotelco.controllers;

import com.hotelco.constants.Constants;
import com.hotelco.constants.RoomType;
import com.hotelco.utilities.DatabaseUtil;
import com.hotelco.utilities.FXMLPaths;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class RoomChoiceController extends DashboardController {

    @FXML
    private Text suiteFlavor;

    @FXML
    private Button dbl;

    @FXML
    private Text dblRate;

    @FXML
    private Text dblFlavor;

    @FXML
    private Button king;

    @FXML
    private Text kingFlavor;

    @FXML
    private Text kingRate;

    @FXML
    private Button queen;

    @FXML
    private Text queenFlavor;

    @FXML
    private Text queenRate;

    @FXML
    private Button suite;

    @FXML
    private Text suiteRate;

    

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            kingRate.setText("$" + DatabaseUtil.getRate(RoomType.KING).toString());
            queenRate.setText("$" + DatabaseUtil.getRate(RoomType.QUEEN).toString());
            dblRate.setText("$" + DatabaseUtil.getRate(RoomType.DBL).toString());
            suiteRate.setText("$" + DatabaseUtil.getRate(RoomType.SUITE).toString());
            kingFlavor.setText(Constants.KingRoomFlavor);
            queenFlavor.setText(Constants.QueenRoomFlavor);
            dblFlavor.setText(Constants.DblRoomFlavor);
            suiteFlavor.setText(Constants.SuiteRoomFlavor);

        });
    }

    @FXML
    void switchToBooking(MouseEvent event) {
         Button clickedButton = (Button) event.getSource();
         switchAnchor(FXMLPaths.SEARCH);
         String s = clickedButton.getId();
    }

}