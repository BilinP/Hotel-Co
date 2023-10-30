package com.hotelco.entities;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.hotelco.utilities.ReservationCalculator;

public class Payment {

    private BigDecimal amount;
    
    private Integer paymentId;

    private LocalDateTime timeOfPayment;
    
    public BigDecimal getAmount(){return amount;}

    public Integer getPaymentId(){return paymentId;}
    
    public LocalDateTime getTimeOfPayment(){return timeOfPayment;}

    public void setAmount(BigDecimal newAmount){amount = newAmount;}

    public void setPaymentId(Integer newPaymentId){paymentId = newPaymentId;}

    public void setTimeOfPayment(LocalDateTime newTimeOfPayment){
        timeOfPayment = newTimeOfPayment;
    }

    public Payment(Reservation reservation){
        amount = ReservationCalculator.calcTotal(reservation);
        timeOfPayment = LocalDateTime.now();
        if(ReservationSystem.requestCreditCardPayment(amount)){
            push();
        }
    }
    
    /**
     * Inserts a new paid payment into database. ReservationSystem's
     * currentReservation will be associated with this payment in the database.
     * Note that there is no push function to update a payment in the database,
     * only to insert a new payment.
     */
    public void push(){
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String sqlQuery = "INSERT INTO payments " +
            "SET amount = " + amount +
            ", SET time = '" + formatter.format(timeOfPayment) +
            "', SET reservation_id = " +
            ReservationSystem.getCurrentReservation().getReservationId();
        try {
            con = ReservationSystem.getDatabaseConnection();
            ps = con.prepareStatement(sqlQuery);
            ps.execute();
            ps = con.prepareStatement("SELECT LAST_INSERT_ID() as id");
            rs = ps.executeQuery();
            if(rs.next()){
                paymentId = rs.getInt("id");
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }
}
