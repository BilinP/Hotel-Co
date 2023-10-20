package com.hotelco.entities;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import javafx.util.Pair;
/**
 * Reservation class requests changes or views of the current users reservation and 
 * holds all data related to the reservation.
 * @author Daniel Schwartz
 * @version %I%, %G%
 */
public class Reservation {
    /**
     * Represents a room.
     */
    private Room room;
    /**
     * Represents the starting date of a reservation.
     */
    private LocalDate startDate;
    /**
     * Represents the end date of a reservation.
     */
    private LocalDate endDate;
    /**
     * Represents the user associated with this reservation.
     */
    private User user;
    /**
     * Represents the data inside reservation for the user to view.
     */
    private InvoiceDetails invoiceDetails;
    /**
     * Represents a comment section for additional information outside of set data.
     */
    private String comments;
    /**
     * Represents the size of the group.
     */
    private Integer groupSize;
    /**
     * Represents the identification of user.
     */
    private Integer reservationId;
    /**
     * Represents a mark if a user has canceled a reservation.
     */
    private boolean isCancelled;
    /**
     * Creates an empty Reservation object.
     */
    public Reservation(){}
    /**
     * fetches user data using reservationIdNum from database.
     * @param reservationIdNum
     */
    public Reservation(Integer reservationIdNum)
    {
        fetch(reservationIdNum);
    }

    /**
     * Constructor for creating a new reservation that will be added to database.
     */
    public Reservation(
        Room newRoom, LocalDate newStartDate, LocalDate newEndDate, User newUser, Integer newGroupSize) {
            room = newRoom;
            startDate = newStartDate;
            endDate = newEndDate;
            user = newUser;
            groupSize = newGroupSize;
    }
    /**
     * 
     * @param newRoom
     * @param newStartDate
     * @param newEndDate
     * @param newUser
     * @param newInvoiceDetails
     * @param newComments
     * @param newGroupSize
     * @param newReservationId
     * @param newIsCancelled
     */
    public Reservation(
        Room newRoom, LocalDate newStartDate, LocalDate newEndDate, User newUser,
        InvoiceDetails newInvoiceDetails, String newComments, Integer newGroupSize,
        Integer newReservationId, boolean newIsCancelled) {
            room = newRoom;
            startDate = newStartDate;
            endDate = newEndDate;
            user = newUser;
            invoiceDetails = newInvoiceDetails;
            comments = newComments;
            groupSize = newGroupSize;
            reservationId = newReservationId;
            isCancelled = newIsCancelled;
    }

    public Room getRoom(){return room;}

    public LocalDate getStartDate(){return startDate;}

    public LocalDate getEndDate(){return endDate;}

    public User getUser(){return user;}

    public InvoiceDetails getInvoiceDetails(){return invoiceDetails;}

    public String getComments(){return comments;}

    public Integer getGroupSize(){return groupSize;}

    public Integer getReservationId(){return reservationId;}

    public boolean getIsCancelled(){return isCancelled;}

    public void setRoom(Room newRoom){room = newRoom;}

    public void setStartDate(LocalDate newStartDate){startDate = newStartDate;}

    public void setEndDate(LocalDate newEndDate){endDate = newEndDate;}

    public void setUser(User newUser){user = newUser;}

    public void setInvoiceDetails(InvoiceDetails newInvoiceDetails){invoiceDetails = newInvoiceDetails;}

    public void setComments(String newComments){comments = newComments;}

    public void setGroupSize(Integer newGroupSize){groupSize = newGroupSize;}

    public void setReservationId(Integer newReservationId){reservationId = newReservationId;}

    public void setIsCancelled(boolean newIsCancelled){isCancelled = newIsCancelled;}

    public void addComment(String newComment){
        comments += newComment;
    }

    public void fetch(Integer reservationIdToFetch){
        PreparedStatement ps = null;
        Connection con = null;
        String sqlQuery = null;
        ResultSet rs = null;
        try {
            sqlQuery = "SELECT reservation_id FROM reservations WHERE reservation_id = " + reservationIdToFetch;
            con = ReservationSystem.getDatabaseConnection();
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                room = new Room(rs.getInt("room_num"));
                startDate = rs.getDate("start_date").toLocalDate();
                endDate = rs.getDate("end_date").toLocalDate();
                user = new User(rs.getInt("user_id"));
                isCancelled = rs.getBoolean("is_cancelled");
                invoiceDetails.rateDiscount = rs.getBigDecimal("rate_discount");
                comments = rs.getString("comments");
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    public void create(){
        PreparedStatement ps = null;
        Connection con = null;
        String sqlQuery = null;
        try {
            sqlQuery = "INSERT INTO reservations" +
            "(room_num, start_date, end_date, user_id, group_size) " + 
            "Values (" +
            room.getRoomNum() + ", '" +
            Date.valueOf(startDate) + "', '" +
            Date.valueOf(endDate) + "', " +
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
    public void push(){
        PreparedStatement ps = null;
        Connection con = null;
        String sqlQuery = null;
        try {
            sqlQuery = "UPDATE reservations " +
            "SET room_num = " + room.getRoomNum() +
            ", start_date = '" + Date.valueOf(startDate) + 
            "', end_date = '" + Date.valueOf(endDate) +
            "',  group_size = " + groupSize +
            ", is_cancelled = " + isCancelled +
            ", comments = " + comments +
            ", rateDiscount = " + invoiceDetails.rateDiscount +
            " WHERE reservation_id = " + reservationId;
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
        //FIXME:write adjustment class

        public InvoiceDetails(){}

        public BigDecimal getRateDiscount(){return rateDiscount;}

        // public InvoiceDetails(Integer reservationId){
        //     PreparedStatement ps = null;
        //     Connection con = null;
        //     String sqlQuery = null;
        //     ResultSet rs = null;
        //     Integer adjustmentsLength = 0;
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