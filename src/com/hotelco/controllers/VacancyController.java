package com.hotelco.controllers;



import com.hotelco.utilities.DatabaseUtil;

import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.text.Text;

/**
 * The ProfileController class is the associated controller class of the
 * 'Profile' view. It handles connection between the GUI and internal data.
 * 
 * @author Bilin Pattasseril
 */

public class VacancyController extends BaseController {

   
    @FXML
    private Text doubleInUse;

    @FXML
    private Text doubleVacant;

    @FXML
    private Text kingInUse;

    @FXML
    private Text kingVacant;

    @FXML
    private Text kingVacant1;

    @FXML
    private Text queenInUse;

    @FXML
    private Text queenVacant;

    @FXML
    private Text suiteInUse;

    @FXML
    private Text totalCheckIn;

    @FXML
    private Text totalCheckOut;

    @FXML
    private Text totalInUse;

    @FXML
    private Text totalVacant;

    /**
     * This method is called immediately upon controller creation. It updates the
     * the current user and then updates the textfields 'email',
     * 'first','last','number' to what is stored in the database.It as well sets a
     * formatter for the 'number', 'first', and 'email'.
     */
    @FXML
    private void initialize() {
        doubleInUse.setText("Occupied:"+ DatabaseUtil.);

        Platform.runLater(() -> {

        });
    }

    private set

}
