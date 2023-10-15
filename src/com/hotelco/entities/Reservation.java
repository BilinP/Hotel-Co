package com.hotelco.entities;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import javafx.util.Pair;

public class Reservation {
    
    private Room room;

    private LocalDate startDate;

    private LocalDate endDate;
    
    private User user;

    private InvoiceDetails invoiceDetails;

    private String[] comments;

    private int groupSize;

    private int reservationId;

    private boolean isCancelled;
    
    public Reservation(int reservationIdNum)
    {
        fetch(reservationIdNum);
    }

    public Room getRoom(){return room;}

    public LocalDate getStartDate(){return startDate;}

    public LocalDate getEndDate(){return endDate;}

    public User getUser(){return user;}

    public InvoiceDetails getInvoiceDetails(){return invoiceDetails;}

    public String[] getComments(){return comments;}

    public int getGroupSize(){return groupSize;}

    public int getReservationId(){return reservationId;}

    public boolean getIsCancelled(){return isCancelled;}


    public void setRoom(Room newRoom){room = newRoom;}

    public void setStartDate(LocalDate newStartDate){startDate = newStartDate;}

    public void setEndDate(LocalDate newEndDate){endDate = newEndDate;}

    public void setUser(User newUser){user = newUser;}

    public void setInvoiceDetails(InvoiceDetails newInvoiceDetails){invoiceDetails = newInvoiceDetails;}

    public void setComments(String newComments[]){comments = newComments;}

    public void setGroupSize(int newGroupSize){groupSize = newGroupSize;}

    public void setReservationId(int newReservationId){reservationId = newReservationId;}

    public void setIsCancelled(boolean newIsCancelled){isCancelled = newIsCancelled;}

    public void addComment(String newComment){
        int origSize = comments.length;
        String newComments[] = new String[origSize + 1];
        System.arraycopy(comments, 0, newComments, 0, origSize);
        comments = newComments;
        newComments[origSize] = newComment;
    }

    public void fetch(int reservationIdToFetch){
        PreparedStatement ps = null;
        Connection con = null;
        String sqlQuery = null;
        ResultSet rs = null;
        try {
            sqlQuery = "SELECT * FROM reservations WHERE reservation_id = " + reservationIdToFetch;
            con = ReservationSystem.getDatabaseConnection();
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            rs.next();
            room = new Room(rs.getInt("room_num"));
            startDate = rs.getDate("startDate").toLocalDate();
            endDate = rs.getDate("endDate").toLocalDate();
            user = new User(rs.getInt("user_id"));
            //invoiceDetails = new InvoiceDetails(reservationId);
            invoiceDetails.rateDiscount = rs.getBigDecimal("rate_discount");
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    public void push(){
        PreparedStatement ps = null;
        Connection con = null;
        String sqlQuery = null;
        try {
            sqlQuery = "INSERT INTO reservations" +
            "(room_num, start_date, end_date, user_id, group_size) " + 
            "Values (" +
            room.getRoomNum() + ", " +
            Date.valueOf(startDate) + ", " +
            Date.valueOf(endDate) + ", " +
            user.getUserId() + ", " +
            groupSize + ")";
            con = ReservationSystem.getDatabaseConnection();
            ps = con.prepareStatement(sqlQuery);
            ps.execute();
        }
        catch (SQLException e){
            System.out.println(e);
        }

    }


    public class InvoiceDetails{
        private BigDecimal rateDiscount;

        private Pair<String, Integer>[] adjustments;

        // public InvoiceDetails(int reservationId){
        //     PreparedStatement ps = null;
        //     Connection con = null;
        //     String sqlQuery = null;
        //     ResultSet rs = null;
        //     int adjustmentsLength = 0;
        //     try {
        //         sqlQuery = "SELECT * FROM adjustments WHERE reservation_id = " + reservationId;
        //         con = ReservationSystem.getDatabaseConnection();
        //         ps = con.prepareStatement(sqlQuery);
        //         rs = ps.executeQuery();
        //         rs.last();
        //         adjustmentsLength = rs.getRow();
        //         adjustments = new Pair<String,Integer>[adjustmentsLength];
        //         rs.beforeFirst();
        //         while (rs.next()){
        //             adjustments
        //             list.add(temp);
        //         }
        //     }
        //     catch (SQLException e){
        //         System.out.println(e);
        //     }
        // }
    }
    
}
/*
 * -room : Room
-startDate : Date
-endDate : Date
-user : User
-invoiceDetails : InvoiceDetails
-comments : string[]
-groupSize : int
-reservationId : int
+GetRoom() : Room
+GetStartDate() : Date
+GetEndDate() : Date
+getInvoiceDetails() : InvoiceDetails
+GetComments() : string[]
+GetGroupSize() : int
+GetID() : int
+SetRoom(Room) : void
+SetStartDate(startDate) : void
+SetEndDate(endDate) : void
+SetComments(comment) : void
+SetGroupSize(groupSize) : void
+AddComment(comment) : void
+Fetch() : void
+Push() : void
+CalcTotal() : Currency

 */