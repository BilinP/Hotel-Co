package com.hotelco.entities;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.hotelco.utilities.ReservationCalculator;
/**
 * Maintains the payment associated with a reservation.
 * @author Daniel Schwartz
 */
public class Payment {
    /**
     * Total amount after calculation.
     */
    private BigDecimal amount;
    /**
     * Unique ID for payment.
     */
    private Integer paymentId;
    /**
     * Time payment is created.
     */
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
        Reservation temp = ReservationSystem.getCurrentReservation();
        ReservationSystem.setCurrentReservation(reservation);
        amount = ReservationCalculator.calcTotal(reservation);
        timeOfPayment = LocalDateTime.now();
        if(ReservationSystem.requestCreditCardPayment(amount)){
            push();
        }
        ReservationSystem.setCurrentReservation(temp);
    }
    
    /**
     * Inserts a new paid payment into database. ReservationSystem's
     * currentReservation will be associated with this payment in the database.
     * Note that there is no push function to update a payment in the database,
     * only to insert a new payment.
     */
    public void push(){
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String sqlQuery = "INSERT INTO payments " +
            "SET amount = " + amount +
            ", time = '" + formatter.format(timeOfPayment) +
            "', reservation_id = " +
            ReservationSystem.getCurrentReservation().getReservationId();
        Connection con = ReservationSystem.getDatabaseConnection();

        try {
            ps1 = con.prepareStatement(sqlQuery);
            ps1.execute();
            ps2 = con.prepareStatement("SELECT LAST_INSERT_ID() as id");
            rs = ps2.executeQuery();
            if(rs.next()){
                paymentId = rs.getInt("id");
            }
        }
        catch (SQLException e){ 
            System.out.println("Payment.push()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ReservationSystem.ready();
    }
}
