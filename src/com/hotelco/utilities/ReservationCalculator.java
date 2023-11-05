package com.hotelco.utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.time.LocalDate;

import javax.swing.text.DateFormatter;

import com.hotelco.entities.Reservation;
/**
 * Calculates the amount a reservation would cost.
 * @author Daniel Schwartz
 */
public class ReservationCalculator {

    public static BigDecimal calcTotal(Reservation reservation){
        LocalDate i;
        BigDecimal sum = new BigDecimal(0);

        for (i = reservation.getStartDate();
        i.isBefore(reservation.getEndDate()); i = i.plusDays(1)){
            sum = sum.add(calcDailyTotal(reservation, i));
        }
        sum = sum.multiply(
            new BigDecimal("1").subtract(
                reservation.getInvoiceDetails().getRateDiscount().getAmount()));
        sum = sum.add(reservation.getInvoiceDetails().getTotalAdustments());
        sum = sum.multiply(TaxRate.getTaxMultiplier());
        sum = sum.setScale(2, RoundingMode.CEILING);
        return sum;
    }
    /**
     * Calculates the cost of a reserved room within a certain day.
     * @param date the single day being considered in the calculation
     * @return the calculated daily rate
     */
    public static BigDecimal calcDailyTotal(Reservation reservation, LocalDate date){
        BigDecimal dailyRate =
            DailyRates.getRoomRate(reservation.getRoom().getRoomType());

        if (date.getDayOfWeek().getValue() == 1 ||
            date.getDayOfWeek().getValue() == 7){
            dailyRate = dailyRate.multiply(new BigDecimal("1.2"));
        }
        return dailyRate;
    }
    
}
