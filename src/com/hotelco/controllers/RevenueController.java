package com.hotelco.controllers;

import com.hotelco.entities.ReservationSystem;
import com.hotelco.entities.User;
import com.hotelco.utilities.TextFormatters;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;


/**
 * The ProfileController class is the associated controller class of the 'Profile' view. 
 * It handles connection between the GUI and internal data.
 * 
 * @author      Bilin Pattasseril
 */

public class RevenueController extends BaseController {
    
    @FXML
    private Text dailyRevenue;

    @FXML
    private Text lifetimeRevenue;

    @FXML
    private BarChart<?, ?> revenueChart;

    @FXML
    private Text weeklyRevenue;

    @FXML
    private Text yearlyRevenue;

    /**
     * This method is called immediately upon controller creation.
     * It updates the the current user and then updates the textfields 'email',
     * 'first','last','number' to what is stored in the database.It as well sets 
     * a formatter for the 'number', 'first', and 'email'.
     */
    @FXML
    private void initialize() {     
        TextFormatters textFormatters = new TextFormatters();
        user = ReservationSystem.getCurrentUser();
        number.setTextFormatter(textFormatters.PHONE_NUMBER);
        first.setTextFormatter(textFormatters.FIRST_NAME);
        first.setFocusTraversable(true);
        last.setTextFormatter(textFormatters.LAST_NAME);
        email.setText(user.getEmail());
        first.setText(user.getFirstName());
        last.setText(user.getLastName());
        number.setText(user.getPhone());        
        Platform.runLater(() -> {

        });
    }

    @FXML
    private void saveProfileContent(MouseEvent event) {
        User user = ReservationSystem.getCurrentUser();
        user.setEmail(email.getText());
        user.setFirstName(first.getText());
        user.setLastName(last.getText());
        user.setPhone(number.getText());
        user.push();
    }

}
