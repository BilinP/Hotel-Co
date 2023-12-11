package com.hotelco.controllers;

import com.hotelco.utilities.Reports;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;

/**
 * The ProfileController class is the associated controller class of the
 * 'Profile' view. It handles connection between the GUI and internal data.
 * 
 * @author Bilin Pattasseril
 */

public class RevenueController extends BaseController {

    @FXML
    private Text dailyRevenue;

    @FXML
    private Text lifetimeRevenue;

    @FXML
    private BarChart<String, Double> revenueChart;

    @FXML
    private Text weeklyRevenue;

    @FXML
    private Text yearlyRevenue;

    /**
     * This method is called immediately upon controller creation. It updates the
     * the current user and then updates the textfields 'email',
     * 'first','last','number' to what is stored in the database.It as well sets a
     * formatter for the 'number', 'first', and 'email'.
     */
    @FXML
    private void initialize() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        dailyRevenue.setText("$ " + numberFormat.format(Reports.getDailyRevenue()));
        lifetimeRevenue.setText("$ " + numberFormat.format(Reports.getLifetimeRevenue()));
        weeklyRevenue.setText("$ " + numberFormat.format(Reports.getWeeklyRevenue()));
        yearlyRevenue.setText("$ " + numberFormat.format(Reports.getYearlyRevenue()));

        XYChart.Series<String, Double> plot = new XYChart.Series<>();
        String[] monthAbb = { "Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec" };
        for (int i = 5; i >= 0; i--) {
            LocalDate monthDate = LocalDate.now().minusMonths(i);
            String monthString = monthAbb[monthDate.getMonthValue() - 1];
            Double revenueDouble = Reports.getRevenueOfMonth(monthDate.getMonth(), monthDate.getYear()).doubleValue();
            plot.getData().add(new XYChart.Data<>(monthString, revenueDouble));
        }
        revenueChart.getData().add(plot);

        Platform.runLater(() -> {

        });
    }

}
