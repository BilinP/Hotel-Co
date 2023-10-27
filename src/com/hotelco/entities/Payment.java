package com.hotelco.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Payment {

    private Integer amount;
    
    private Integer paymentId;

    private LocalDateTime timeOfPayment;

    //members: timeOfPayment? paymentId
    
    public Integer getAmount(){return amount;}

    public void setAmount(Integer newAmount){amount = newAmount;}

    public void processPayment(Reservation reservation){
        /*
        iterate through the dates, calculating each day's total into an ArrayList
        Add any adjustments
        call dummy credit card function
        */
    }
    
}
