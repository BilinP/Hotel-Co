package com.hotelco.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 * Maintains the payment associated with a reservation.
 * @author Daniel Schwartz
 */
public class Payment {
    /**
     * Total amount after calculation.
     */
    private Integer amount;
    /**
     * Unique ID for payment.
     */
    private Integer paymentId;
    /**
     * Time payment is created.
     */
    private LocalDateTime timeOfPayment;

    //members: timeOfPayment? paymentId
    /**
     * Getter for an amount.
     * @return the amount
     */
    public Integer getAmount(){return amount;}
    /**
     * Setter for an amount.
     * @param newAmount
     */
    public void setAmount(Integer newAmount){amount = newAmount;}
    /**
     * Processes and calls a reservation calculator method.
     * @param reservation
     */
    public void processPayment(Reservation reservation){
        /*
        iterate through the dates, calculating each day's total into an ArrayList
        Add any adjustments
        call dummy credit card function
        */
    }
    
}
