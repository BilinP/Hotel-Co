package com.hotelco.utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import com.hotelco.constants.Constants;
import com.hotelco.constants.RoomType;
import com.hotelco.entities.Reservation;
/**
 * Calculates the amount a reservation would cost.
 * @author Daniel Schwartz
 */
public class ReservationCalculator {
    /**
     * Calculates the total price of a reservation.
     * @param reservation the reservation to be considered
     * @return the total price of the supplied reservation
     */

    public static BigDecimal calcTotal(Reservation reservation){
        LocalDate i;
        BigDecimal sum = new BigDecimal(0);
        BigDecimal baseRate = DailyRates.getRoomRate(
            reservation.getRoom().getRoomType());

        for (i = reservation.getStartDate();
        i.isBefore(reservation.getEndDate()); i = i.plusDays(1)){
            sum = sum.add(calcDailyTotal(baseRate, i));
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
    public static BigDecimal calcDailyTotal(BigDecimal roomRate, LocalDate date){
        BigDecimal dailyRate = roomRate;

        if (date.getDayOfWeek().getValue() == 1 ||
            date.getDayOfWeek().getValue() == 7){
            dailyRate = dailyRate.multiply(Constants.WEEKEND_MULTIPLIER);
        }
        return dailyRate;
    }

    /**
     * Calculates the total price of a reservation.
     * @param reservation the reservation to be considered
     * @return the total price of the supplied reservation
     */

    public static BigDecimal calcTotal(LocalDate startDate, LocalDate endDate,
    RoomType roomtype){
        LocalDate i;
        BigDecimal sum = new BigDecimal(0);
        BigDecimal baseRate = DailyRates.getRoomRate(roomtype);

        for (i = startDate; i.isBefore(endDate); i = i.plusDays(1)){
            sum = sum.add(calcDailyTotal(baseRate, i));
        }
        sum = sum.multiply(TaxRate.getTaxMultiplier());
        sum = sum.setScale(2, RoundingMode.CEILING);
        return sum;
    }
    
}
