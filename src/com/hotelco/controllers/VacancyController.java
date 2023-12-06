package com.hotelco.controllers;



import com.hotelco.constants.RoomType;
import com.hotelco.utilities.DatabaseUtil;
import com.hotelco.utilities.Reports;

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
    private Text queenInUse;

    @FXML
    private Text queenVacant;

    @FXML
    private Text suiteInUse;
     @FXML
    private Text suiteVacant;

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
      
        doubleVacant.setText("Vacant: "+ Reports.countAvailableRooms(RoomType.of("DOUBLE")));
        doubleInUse.setText("Occupied: "+ Reports.countOccupiedRooms(RoomType.of("DOUBLE")));

        queenVacant.setText("Vacant: "+ Reports.countAvailableRooms(RoomType.of("QUEEN")));
        queenInUse.setText("Occupied: "+ Reports.countOccupiedRooms(RoomType.of("QUEEN")));

        kingVacant.setText("Vacant: "+ Reports.countAvailableRooms(RoomType.of("KING")));
        kingInUse.setText("Occupied: "+ Reports.countOccupiedRooms(RoomType.of("KING")));

        suiteVacant.setText("Vacant: "+ Reports.countAvailableRooms(RoomType.of("SUITE")));
        suiteInUse.setText("Occupied: "+ Reports.countOccupiedRooms(RoomType.of("SUITE")));

        totalInUse.setText("Occupied: "+ Reports.getOccupancy());
        totalVacant.setText("Vacant: "+ Reports.getVacancy());

        totalCheckIn.setText("Check In: "+ Reports.countTodayCheckIns());
        totalCheckOut.setText("Check Out: "+ Reports.countTodayCheckOuts());

        Platform.runLater(() -> {

        });
    }


}
