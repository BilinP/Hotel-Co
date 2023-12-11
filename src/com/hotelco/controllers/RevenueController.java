package com.hotelco.controllers;

import com.hotelco.utilities.Reports;


import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;


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
    private BarChart<String, BigDecimal> revenueChart;

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

        XYChart.Series<String, BigDecimal> plot = new XYChart.Series<>();
        String[] monthAbb = { "Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec" };
        for (int m = 5; m >= 0; m--) {
            LocalDate monthDate = LocalDate.now().minusMonths(m);
            String monthString = monthAbb[monthDate.getMonthValue() - 1];
            BigDecimal revenueBDecimal = Reports.getRevenueOfMonth(monthDate.getMonth(), monthDate.getYear());
            plot.getData().add(new XYChart.Data<>(monthString, revenueBDecimal));
        }
        revenueChart.getData().add(plot);
    }

}
