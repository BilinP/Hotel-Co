package com.hotelco.utilities;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.hotelco.entities.Reservation;
/**
 * Calculates the amount a reservation would cost.
 * @author Daniel Schwartz
 */
public class ReservationCalculator {

    private Reservation reservation;

    private Integer total;
    
    public BigDecimal calcTotal(){
        LocalDate i;
        BigDecimal sum = new BigDecimal(0);

        for (i = reservation.getStartDate();
        i.isBefore(reservation.getEndDate()); i.plusDays(1)){
            sum = sum.add(calcDailyTotal(i));
        }
        return sum;
    }
    /**
     * Calculates the cost of a reserved room within a certain day.
     * @param date
     * @return the cost of a single room.
     */
    public BigDecimal calcDailyTotal(LocalDate date){
        BigDecimal dailyRate =
            DailyRates.getRoomRate(reservation.getRoom().getRoomType());
        if (date.getDayOfWeek().getValue() == 1 ||
            date.getDayOfWeek().getValue() == 7){
            dailyRate = dailyRate.multiply(new BigDecimal(1.2));
        }
        return dailyRate;
    }
    
}
